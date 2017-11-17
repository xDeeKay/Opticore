package net.opticraft.opticore.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;
import net.opticraft.opticore.warp.WarpInfo;
import net.opticraft.opticore.world.WorldInfo;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EventListener implements Listener {

	public Main plugin;

	public Config config;

	public BungeecordMethods bungeecordMethods;

	public EventListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
	}

	public String chatSymbol(String color, String symbol) {
		return ChatColor.WHITE + "[" + ChatColor.valueOf(color.toUpperCase()) + symbol + ChatColor.WHITE + "] ";
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

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Action action = event.getAction();
		String world = player.getWorld().getName();

		if (plugin.worlds.containsKey(world)) {
			if (action == Action.LEFT_CLICK_BLOCK ||
					action == Action.RIGHT_CLICK_BLOCK ||
					action == Action.LEFT_CLICK_AIR ||
					action == Action.RIGHT_CLICK_AIR) {
				if (!player.hasPermission("opticore.world.build." + plugin.worlds.get(world).getPermission())) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "You do not have permission to build in the '" + world + "' world.");
				}
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

		if (plugin.playerPostChangeServer.contains(playerName)) {

			// Player has changed server

			// Remove player from playerPostChangeServer
			plugin.playerPostChangeServer.remove(playerName);

		} else {

			// Player is connecting to the network

			// Send connect message to network
			new BukkitRunnable() {
				public void run() {
					bungeecordMethods.sendConnectMessage(player, "Creative", "connect");
					System.out.println("[Opticore" + config.getServerName() + "] Sent connect message for " + player.getName() + " from " + config.getServerName());
				}
			}.runTaskLater(plugin, 5 * 20);

			// Send connect message to the server
			plugin.getServer().broadcastMessage(chatSymbol("GREEN", "+") + ChatColor.GOLD + player.getName() + " has connected via " + config.getServerName() + ".");

		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();

		if (plugin.playerPreChangeServer.containsKey(playerName)) {

			// Player is changing server

			String targetServer = plugin.playerPreChangeServer.get(playerName);

			/*
			// Send change message to network (moved to BungeeCordMehtodsd.java > sendPlayerToServer)
			bungeecordMethods.connectModule(player, targetServer, "change");
			System.out.println("[OpticoreCreative] Sent change message for " + player.getName() + " from Creative");
			 */

			// Send change message to server
			plugin.getServer().broadcastMessage(chatSymbol("YELLOW", ">") + ChatColor.GOLD + player.getName() + " has changed to " + targetServer + ".");

			// Remove player from playerChangeServer
			plugin.playerPreChangeServer.remove(playerName);

		} else {

			// Player is disconnecting

			// Send disconnect message to network
			bungeecordMethods.sendConnectMessage(player, "Creative", "disconnect");
			System.out.println("[Opticore" + config.getServerName() + "] Sent disconnect message for " + player.getName() + " from " + config.getServerName());

			// Send disconnect message to server
			plugin.getServer().broadcastMessage(chatSymbol("RED", "-") + ChatColor.GOLD + player.getName() + " has disconnected.");
		}
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
		String playerName = player.getName();

		bungeecordMethods.sendChatMessage(player, serverShort, playerGroup, playerGroupColor, playerName, message);

		for (Player online : plugin.getServer().getOnlinePlayers()) {
			event.getRecipients().remove(online);
			String uuid = online.getUniqueId().toString();
			if (plugin.players.get(uuid).getSettingsPlayerChat() == 1) {
				online.spigot().sendMessage(bungeecordMethods.message(serverShort, playerGroupColor, playerGroup, playerName, message));
			}
		}

		//event.setFormat(ChatColor.translateAlternateColorCodes('&', config.getChatFormat().replace("%server_short%", serverShort).replace("%group_color%", playerGroupColor).replace("%group%", playerGroup).replace("%player%", playerName).replace("%message%", "%2$s")));
	}
}
