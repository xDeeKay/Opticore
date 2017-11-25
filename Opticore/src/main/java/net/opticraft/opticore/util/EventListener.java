package net.opticraft.opticore.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.player.PlayerInfo;
import net.opticraft.opticore.teleport.TeleportMethods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;
import net.opticraft.opticore.warp.WarpInfo;
import net.opticraft.opticore.world.WorldInfo;
import net.opticraft.opticore.world.WorldMethods;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EventListener implements Listener {

	public Main plugin;

	public Config config;
	public Methods methods;
	public BungeecordMethods bungeecordMethods;

	public WorldMethods worldMethods;
	
	public TeleportMethods teleportMethods;

	public EventListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.methods = this.plugin.methods;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.worldMethods = this.plugin.worldMethods;
		this.teleportMethods = this.plugin.teleportMethods;
	}

	public String chatSymbol(String color, String symbol) {
		return ChatColor.WHITE + "[" + ChatColor.valueOf(color.toUpperCase()) + symbol + ChatColor.WHITE + "] ";
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		Action action = event.getAction();
		
		String world = plugin.players.get(player.getName()).getWorld();
		
		if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
			if (!player.hasPermission("opticore.world.build." + plugin.worlds.get(world).getPermission())) {
				event.setCancelled(true);
				methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to build in the '" + world + "' world.");
			}
		}
	}

	// Player is logging into the server
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
	}

	// Player has successfully logged into the server
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		event.setJoinMessage(null);

		final Player player = event.getPlayer();
		String playerName = player.getName();

		String worldName = player.getLocation().getWorld().getName();
		String world = worldMethods.resolveWorld(worldName);
		
		if (!plugin.players.containsKey(player.getName())) {
			System.out.println("players does not contain uuid.. creating");
			plugin.players.put(player.getName(), new PlayerInfo());
		}

		plugin.players.get(player.getName()).setWorld(world);
		
		int delay = 0;
		if (plugin.getServer().getOnlinePlayers().size() == 1) {
			delay = 1;
			methods.debug("[" + config.getServerName() + "] " + player.getName() + " is the only player, delay = " + delay);
		}

		new BukkitRunnable() {
			public void run() {

				if (plugin.playerHasChangedServer.contains(playerName)) {

					// Player has changed server //

					// Send change messsge to server
					methods.debug("[" + config.getServerName() + "] Sent change message to server for " + player.getName());
					methods.sendStyledMessage(null, null, "YELLOW", ">", "GOLD", player.getName() + " has moved to " + config.getServerName() + ".");

					// Send change messsge to network
					methods.debug("[" + config.getServerName() + "] Sent change message to network for " + player.getName());
					bungeecordMethods.sendConnectMessage(player, "change");

					// Remove player from playerHasChangedServer
					plugin.playerHasChangedServer.remove(playerName);

				} else {

					// Player is connecting to network //

					// Send connect message to server
					methods.debug("[" + config.getServerName() + "] Sent connect message to server for " + player.getName());
					methods.sendStyledMessage(null, null, "GREEN", "+", "GOLD", player.getName() + " has connected via " + config.getServerName() + ".");

					// Send connect message to network
					methods.debug("[" + config.getServerName() + "] Sent connect message to network for " + player.getName());
					bungeecordMethods.sendConnectMessage(player, "connect");
				}
			}
		}.runTaskLater(plugin, delay * 20);
		
		// Teleport player from other server
		if (plugin.teleport.containsKey(playerName)) {
			
			System.out.print("teleport contains " + playerName);
			
			String target = plugin.teleport.get(playerName);
			
			if (plugin.getServer().getPlayer(target) != null) {
				
				Player targetPlayer = plugin.getServer().getPlayer(target);
				
				Location location = targetPlayer.getLocation();
				
				player.teleport(location);
				
				methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported to player '" + targetPlayer.getName() + "'.");
				
			} else {
				
				methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
			}
			
			plugin.teleport.remove(playerName);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		event.setQuitMessage(null);
		
		Player player = event.getPlayer();
		String playerName = player.getName();

		if (plugin.playerIsChangingServer.containsKey(playerName)) {

			// Player is changing server

			// Remove player from playerIsChangingServer
			plugin.playerIsChangingServer.remove(playerName);

		} else {

			// Player is disconnecting from network

			// Send disconnect message to server
			plugin.getServer().broadcastMessage(chatSymbol("RED", "-") + ChatColor.GOLD + player.getName() + " has disconnected.");
			methods.debug("[" + config.getServerName() + "] Sent disconnect message to server for " + player.getName());

			// Send disconnect message to network
			bungeecordMethods.sendConnectMessage(player, "disconnect");
			methods.debug("[" + config.getServerName() + "] Sent disconnect message to network for " + player.getName());
		}
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

		Player player = event.getPlayer();

		String worldName = player.getLocation().getWorld().getName();
		String world = worldMethods.resolveWorld(worldName);
		
		plugin.players.get(player.getName()).setWorld(world);
	}

	public String getServerShort(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0);
		return rank;
	}

	public String getPlayerGroup(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0);
		return rank;
	}

	public String getPlayerGroupPrefix(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		String color = user.getPrefix();
		return color;
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();

		String message = event.getMessage();

		String messageCodes = ChatColor.translateAlternateColorCodes('&', message);

		// Can player use color codes
		if (messageCodes.contains(String.valueOf(ChatColor.BLACK)) || 
				messageCodes.contains(String.valueOf(ChatColor.DARK_BLUE)) || 
				messageCodes.contains(String.valueOf(ChatColor.DARK_GREEN)) || 
				messageCodes.contains(String.valueOf(ChatColor.DARK_AQUA)) || 
				messageCodes.contains(String.valueOf(ChatColor.DARK_RED)) || 
				messageCodes.contains(String.valueOf(ChatColor.DARK_PURPLE)) || 
				messageCodes.contains(String.valueOf(ChatColor.GOLD)) || 
				messageCodes.contains(String.valueOf(ChatColor.GRAY)) || 
				messageCodes.contains(String.valueOf(ChatColor.DARK_GRAY)) || 
				messageCodes.contains(String.valueOf(ChatColor.BLUE)) || 
				messageCodes.contains(String.valueOf(ChatColor.GREEN)) || 
				messageCodes.contains(String.valueOf(ChatColor.AQUA)) || 
				messageCodes.contains(String.valueOf(ChatColor.RED)) || 
				messageCodes.contains(String.valueOf(ChatColor.LIGHT_PURPLE)) || 
				messageCodes.contains(String.valueOf(ChatColor.YELLOW))) {
			if (!player.hasPermission("opticore.chat.color")) {
				message = ChatColor.stripColor(messageCodes);
			}
		}

		// Can player use formatting codes
		if (messageCodes.contains(String.valueOf(ChatColor.BOLD)) || 
				messageCodes.contains(String.valueOf(ChatColor.STRIKETHROUGH)) || 
				messageCodes.contains(String.valueOf(ChatColor.UNDERLINE)) || 
				messageCodes.contains(String.valueOf(ChatColor.ITALIC)) || 
				messageCodes.contains(String.valueOf(ChatColor.RESET))) {
			if (!player.hasPermission("opticore.chat.format")) {
				message = ChatColor.stripColor(messageCodes);
			}
		}

		// Can player use magic code
		if (messageCodes.contains(String.valueOf(ChatColor.MAGIC))) {
			if (!player.hasPermission("opticore.chat.magic")) {
				message = ChatColor.stripColor(messageCodes);
			}
		}

		// Can player send url's
		String[] messageParts = message.split("\\s");
		for (String string : messageParts) {
			try {
				new URL(string);
				if (!player.hasPermission("opticore.chat.url")) {
					player.sendMessage(ChatColor.RED + "Your message can not contain a url.");
					event.setCancelled(true);
					return;
				}
			} catch (MalformedURLException e) {
				//no url
			}
		}

		String serverShort = config.getServerShort();
		String playerGroup = getPlayerGroup(player);
		String playerGroupColor = getPlayerGroupPrefix(player);
		String playerName = ChatColor.stripColor(player.getDisplayName());

		bungeecordMethods.sendChatMessage(player, serverShort, playerGroup, playerGroupColor, playerName, message);

		for (Player online : plugin.getServer().getOnlinePlayers()) {
			event.getRecipients().remove(online);
			if (plugin.players.get(player.getName()).getSettingsPlayerChat() == 1) {
				online.spigot().sendMessage(bungeecordMethods.message(serverShort, playerGroupColor, playerGroup, playerName, message));
			}
		}

		//event.setFormat(ChatColor.translateAlternateColorCodes('&', config.getChatFormat().replace("%server_short%", serverShort).replace("%group_color%", playerGroupColor).replace("%group%", playerGroup).replace("%player%", playerName).replace("%message%", "%2$s")));
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent event) {

		CommandSender sender = event.getSender();

		String buffer = event.getBuffer();
		String[] args = buffer.split("\\s+");
		//String command = args[0];

		List<String> completions = event.getCompletions();

		//sender.sendMessage("buffer:[" + buffer + "]");
		//sender.sendMessage("completions-old:" + completions);

		if (buffer.toLowerCase().startsWith("/ranks ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/rules ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/server ") || buffer.toLowerCase().startsWith("/servers ")) {
			completions.clear();
			completions.add("hub");
			completions.add("survival");
			completions.add("creative");
			completions.add("quest");
			completions.add("legacy");
		}

		if (buffer.toLowerCase().startsWith("/settings ") || buffer.toLowerCase().startsWith("/setting ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/friend ") || buffer.toLowerCase().startsWith("/friends ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/gui ") || buffer.toLowerCase().startsWith("/menu ") || buffer.toLowerCase().startsWith("/opticraft ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/donate ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/delwarp ")) {
			completions.clear();
			if (args.length == 1) {
				for (Map.Entry<String, WarpInfo> warps : plugin.warps.entrySet()) {
					String warp = warps.getKey();
					if (sender.hasPermission("opticore.warp." + warp.toLowerCase())) {
						completions.add(warp);
					}
				}
				//sender.sendMessage("completions-new:" + completions);
			}
		}

		if (buffer.toLowerCase().startsWith("/setwarp ")) {
			completions.clear();
			if (args.length == 1) {
				completions.add("<warp-name>");
				//sender.sendMessage("completions-new:" + completions);
			}
		}

		if (buffer.toLowerCase().startsWith("/warp ") || buffer.toLowerCase().startsWith("/warps ")) {
			completions.clear();
			if (args.length == 1) {
				for (Map.Entry<String, WarpInfo> warps : plugin.warps.entrySet()) {
					String warp = warps.getKey();
					if (sender.hasPermission("opticore.warp." + warp.toLowerCase())) {
						completions.add(warp);
					}
				}
				//sender.sendMessage("completions-new:" + completions);
			}
		}

		if (buffer.toLowerCase().startsWith("/rewards ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/vote ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/j ") || buffer.toLowerCase().startsWith("/worlds ") || buffer.toLowerCase().startsWith("/join ") || buffer.toLowerCase().startsWith("/world ")) {
			completions.clear();
			if (args.length == 1) {
				for (Map.Entry<String, WorldInfo> worlds : plugin.worlds.entrySet()) {
					String world = worlds.getKey();
					if (sender.hasPermission("opticore.world.join." + plugin.worlds.get(world).getPermission())) {
						completions.add(world);
					}
				}
				//sender.sendMessage("completions-new:" + completions);
			}
		}
	}

	/*
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (!event.isCancelled()) {
			String message = event.getMessage().toLowerCase();
			if (message.startsWith("/j")) {
				event.setCancelled(true);
				plugin.getServer().dispatchCommand(event.getPlayer(), "world");
			}
		}
	}
	 */
}
