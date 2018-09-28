package net.opticraft.opticore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;
import net.opticraft.opticore.world.WorldUtil;

public class EventListener implements Listener {

	public Main plugin;

	public Config config;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public WorldUtil worldUtil;

	public TeleportUtil teleportUtil;

	public MySQL mysql;

	public EventListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.worldUtil = this.plugin.worldUtil;
		this.teleportUtil = this.plugin.teleportUtil;
		this.mysql = this.plugin.mysql;
	}

	public String chatSymbol(String color, String symbol) {
		return ChatColor.WHITE + "[" + ChatColor.valueOf(color.toUpperCase()) + symbol + ChatColor.WHITE + "] ";
	}

	// Player is logging into the server
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

	}

	// Player has successfully logged into the server
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws JsonIOException, JsonSyntaxException, IOException {

		final Player player = event.getPlayer();
		String playerName = player.getName();

		String uuid = player.getUniqueId().toString();

		String ip = event.getPlayer().getAddress().toString().replaceAll("/", "").split(":")[0];

		long timestamp = System.currentTimeMillis() / 1000;

		String server = config.getServerName();

		// Login stuff
		String sURL = "http://geoip.nekudo.com/api/" + ip;

		// Connect to the URL using java's native library
		URL url = new URL(sURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
		JsonObject jo = je.getAsJsonObject();
		JsonObject country = (JsonObject) jo.get("country");
		String countryName = country.get("name").getAsString();
		String city = jo.get("city").getAsString();

		// Log login to the database
		mysql.insert("oc_login", 
				Arrays.asList("uuid", "name", "ip", "country", "city", "timestamp", "server"), 
				Arrays.asList(uuid, playerName, ip, countryName, city, timestamp, server));

		// Remove default join message
		event.setJoinMessage(null);

		int delay = 0;
		if (plugin.getServer().getOnlinePlayers().size() == 1) {
			delay = 1;
			util.debug("[" + config.getServerName() + "] " + player.getName() + " is the only player, delay = " + delay);
		}

		new BukkitRunnable() {
			public void run() {

				if (plugin.playerHasChangedServer.contains(playerName)) {

					// Player has changed server

					// Send change messsge to server
					util.debug("[" + config.getServerName() + "] Sent change message to server for " + player.getName());
					for (Player online : plugin.getServer().getOnlinePlayers()) {
						if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("server_change") == null || plugin.players.get(online.getName()).getSettings().get("server_change").getValue() == 1) {
							util.sendStyledMessage(online, null, "YELLOW", ">", "GOLD", player.getName() + " has changed to " + config.getServerName() + ".");
						}
					}

					// Send change messsge to network
					util.debug("[" + config.getServerName() + "] Sent change message to network for " + player.getName());
					bungeecordUtil.sendConnectMessage(player, "change");

					// Remove player from playerHasChangedServer
					plugin.playerHasChangedServer.remove(playerName);

				} else {

					// Player is connecting to network

					// Send connect message to server
					util.debug("[" + config.getServerName() + "] Sent connect message to server for " + player.getName());
					for (Player online : plugin.getServer().getOnlinePlayers()) {
						if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("connect_disconnect") == null || plugin.players.get(online.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
							util.sendStyledMessage(online, null, "GREEN", "+", "GOLD", player.getName() + " has connected via " + config.getServerName() + ".");
						}
					}

					// Send connect message to network
					util.debug("[" + config.getServerName() + "] Sent connect message to network for " + player.getName());
					bungeecordUtil.sendConnectMessage(player, "connect");
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

				util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported to player '" + targetPlayer.getName() + "'.");

			} else {

				util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
			}

			plugin.teleport.remove(playerName);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

		// Remove default quit message
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
			util.debug("[" + config.getServerName() + "] Sent disconnect message to server for " + player.getName());
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
					util.sendStyledMessage(online, null, "RED", "-", "GOLD", player.getName() + " has disconnected.");
				}
			}

			// Send disconnect message to network
			util.debug("[" + config.getServerName() + "] Sent disconnect message to network for " + player.getName());
			bungeecordUtil.sendConnectMessage(player, "disconnect");
		}
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
		String playerGroup = util.getPlayerGroup(player);
		String playerGroupColor = util.getPlayerGroupPrefix(player);
		String playerName = ChatColor.stripColor(player.getDisplayName());

		if (plugin.players.get(player.getName()).getSettings().get("player_chat").getValue() == 1) {
			
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				event.getRecipients().remove(online);
				if (plugin.players.get(online.getName()).getSettings().get("player_chat").getValue() == 1) {
					online.spigot().sendMessage(bungeecordUtil.message(serverShort, playerGroupColor, playerGroup, playerName, message));
				}
			}
			
			bungeecordUtil.sendChatMessage(player, serverShort, playerGroup, playerGroupColor, playerName, message);
			
		} else {
			event.setCancelled(true);
		}

		//event.setFormat(ChatColor.translateAlternateColorCodes('&', config.getChatFormat().replace("%server_short%", serverShort).replace("%group_color%", playerGroupColor).replace("%group%", playerGroup).replace("%player%", playerName).replace("%message%", "%2$s")));
	}

	public void tabComplete(String[] args, List<String> completions, Collection<String> contents) {
		if (args.length == 1) {
			completions.addAll(contents);
		} else if (args.length == 2) {
			for (String string : contents) {
				if (!args[1].equalsIgnoreCase(string)) {
					if (string.toLowerCase().startsWith(args[1].toLowerCase())) {
						completions.add(string);
					}
				}
			}
		}
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent event) {

		CommandSender sender = event.getSender();

		String buffer = event.getBuffer();

		String[] args = buffer.split("\\s+");

		List<String> completions = event.getCompletions();

		//sender.sendMessage("buffer:[" + buffer + "]");
		//sender.sendMessage("args:[" + Arrays.toString(args) + "]");
		//sender.sendMessage("completions-old:" + completions);
		//sender.sendMessage("---------------------");

		if (buffer.toLowerCase().startsWith("/dragon ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/opticraft ") || buffer.toLowerCase().startsWith("/opti ") || buffer.toLowerCase().startsWith("/oc ")) {
			completions.clear();
			Set<String> guis = plugin.gui.keySet();
			tabComplete(args, completions, guis);
		}

		if (buffer.toLowerCase().startsWith("/ranks ")) {
			completions.clear();
			List<String> list = new ArrayList<String>();
			list.add("list");
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/rules ")) {
			completions.clear();
			List<String> list = new ArrayList<String>();
			list.add("list");
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/elytra ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/friend ") || buffer.toLowerCase().startsWith("/friends ") || buffer.toLowerCase().startsWith("/f ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/delhome ")) {
			completions.clear();
			Set<String> homes = plugin.players.get(sender.getName()).getHomes().keySet();
			tabComplete(args, completions, homes);
		}

		if (buffer.toLowerCase().startsWith("/givehome ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				list.add(player.getName());
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/home ") || buffer.toLowerCase().startsWith("/hoem ")) {
			completions.clear();
			Set<String> homes = plugin.players.get(sender.getName()).getHomes().keySet();
			tabComplete(args, completions, homes);
		}

		if (buffer.toLowerCase().startsWith("/homes ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				list.add(player.getName());
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/lockhome ")) {
			completions.clear();
			Set<String> homes = plugin.players.get(sender.getName()).getHomes().keySet();
			tabComplete(args, completions, homes);
		}

		if (buffer.toLowerCase().startsWith("/movehome ")) {
			completions.clear();
			Set<String> homes = plugin.players.get(sender.getName()).getHomes().keySet();
			tabComplete(args, completions, homes);
		}

		if (buffer.toLowerCase().startsWith("/sethome ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/takehome ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				list.add(player.getName());
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/message ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/reply ")) {
			completions.clear();
			List<String> list = new ArrayList<String>();
			if (plugin.players.get(sender.getName()).getLastMessageFrom() != null) {
				list.add(plugin.players.get(sender.getName()).getLastMessageFrom());
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/socialspy ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/player ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/players ") || buffer.toLowerCase().startsWith("/list ") || buffer.toLowerCase().startsWith("/playerlist ") || buffer.toLowerCase().startsWith("/online ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/donate ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/reward ") || buffer.toLowerCase().startsWith("/rewards ")) {
			completions.clear();
			List<String> commands = new ArrayList<String>();
			commands.add("buy");
			commands.add("daily");
			commands.add("points");
			commands.add("store");
			tabComplete(args, completions, commands);
		}

		if (buffer.toLowerCase().startsWith("/reward buy ") || buffer.toLowerCase().startsWith("/rewards buy ")) {
			completions.clear();

			Set<String> rewards = plugin.rewards.keySet();

			if (args.length == 2) {
				completions.addAll(rewards);
			} else if (args.length == 3) {
				for (String reward : rewards) {
					if (!args[2].equalsIgnoreCase(reward)) {
						if (reward.toLowerCase().startsWith(args[2].toLowerCase())) {
							completions.add(reward);
						}
					}
				}
			}
		}

		if (buffer.toLowerCase().startsWith("/vote ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/creative ") || buffer.toLowerCase().startsWith("/c ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/hub ") || buffer.toLowerCase().startsWith("/h ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/server ") || buffer.toLowerCase().startsWith("/servers ")) {
			completions.clear();
			Set<String> servers = plugin.servers.keySet();
			tabComplete(args, completions, servers);
		}

		if (buffer.toLowerCase().startsWith("/survival ") || buffer.toLowerCase().startsWith("/s ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/settings ") || buffer.toLowerCase().startsWith("/setting ")) {
			completions.clear();
			List<String> settings = new ArrayList<String>();
			settings.add("connect_disconnect");
			settings.add("server_change");
			settings.add("player_chat");
			settings.add("server_announcement");
			settings.add("friend_request");
			settings.add("direct_message");
			settings.add("direct_message_color");
			settings.add("teleport_request");
			settings.add("home_privacy");
			tabComplete(args, completions, settings);
		}

		if (buffer.toLowerCase().startsWith("/staff ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/ban ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/unban ") || buffer.toLowerCase().startsWith("/pardon ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/staffchat ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/freeze ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/unfreeze ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/kick ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/mute ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/unmute ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/note ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/ticket ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/warn ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/team ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/randomtp ") || buffer.toLowerCase().startsWith("/rtp ") || buffer.toLowerCase().startsWith("/wilderness ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/tpaccept ") || buffer.toLowerCase().startsWith("/tpa ") || buffer.toLowerCase().startsWith("/tpyes ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String player : plugin.players.get(sender.getName()).getTprIncoming()) {
				list.add(player);
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/tpcancel ") || buffer.toLowerCase().startsWith("/tpc ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/tp ") || buffer.toLowerCase().startsWith("/teleport ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/tpdeny ") || buffer.toLowerCase().startsWith("/tpd ") || buffer.toLowerCase().startsWith("/tpno ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String player : plugin.players.get(sender.getName()).getTprIncoming()) {
				list.add(player);
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/tphere ") || buffer.toLowerCase().startsWith("/tph ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/tprequest ") || buffer.toLowerCase().startsWith("/tpr ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/tprequesthere ") || buffer.toLowerCase().startsWith("/tprhere ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/delwarp ")) {
			completions.clear();
			Set<String> warps = plugin.warps.keySet();
			tabComplete(args, completions, warps);
		}


		if (buffer.toLowerCase().startsWith("/setwarp ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/warp ") || buffer.toLowerCase().startsWith("/warps ")) {
			completions.clear();
			Set<String> warps = plugin.warps.keySet();
			tabComplete(args, completions, warps);
		}

		if (buffer.toLowerCase().startsWith("/wither ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/j ") || buffer.toLowerCase().startsWith("/join ") || buffer.toLowerCase().startsWith("/world ") || buffer.toLowerCase().startsWith("/worlds ")) {
			completions.clear();
			Set<String> worlds = plugin.worlds.keySet();
			tabComplete(args, completions, worlds);
		}

		if (buffer.toLowerCase().startsWith("/setspawn ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/spawn ")) {
			completions.clear();
		}
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {

		if (event.getEntity() instanceof EnderDragon) {

			// after 15 seconds, drop 2920 exp over a 4 second interval
			// drop 36.5 exp per tick, or 730 exp per second
			new BukkitRunnable() {
				public void run() {

					new BukkitRunnable() {

						int count = 0;

						@Override
						public void run() {
							if (count >= 4 * 20) {
								cancel();
							}

							Location location = event.getEntity().getLocation();
							ExperienceOrb exp = location.getWorld().spawn(location, ExperienceOrb.class);
							exp.setExperience((int) 36.5);

							count++;
						}

					}.runTaskTimer(plugin, 0L, 1L);

					// set egg atop the exit portal
					Block block = event.getEntity().getWorld().getBlockAt(0, 65, 0);
					block.setType(Material.DRAGON_EGG);
					//event.getDrops().add(new ItemStack(Material.DRAGON_EGG));

				}
			}.runTaskLater(plugin, 15 * 20);
		}
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();

		String uuid = player.getUniqueId().toString();

		String name = event.getPlayer().getName();

		String server = config.getServerName();

		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.FLOOR);

		Location loc = player.getLocation();
		String world = loc.getWorld().getName();
		String x = df.format(loc.getX());
		String y = df.format(loc.getY());
		String z = df.format(loc.getZ());
		String yaw = df.format(loc.getYaw());
		String pitch = df.format(loc.getPitch());
		String location = String.join(" ", x, y, z, yaw, pitch);

		long timestamp = System.currentTimeMillis() / 1000;

		String command = event.getMessage();

		// Log command to the database
		mysql.insert("oc_command", 
				Arrays.asList("uuid", "name", "server", "world", "location", "timestamp", "command"), 
				Arrays.asList(uuid, name, server, world, location, timestamp, command));
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {

		Entity entity = event.getEntity();
		List<Block> blocks = event.blockList();

		if (entity.getType().equals(EntityType.ENDER_CRYSTAL)) {

			if (!entity.getWorld().getEnvironment().equals(Environment.THE_END)) {
				blocks.clear();

			} else {
				int i = 0;
				for (Block block : blocks) {
					if (!block.getType().equals(Material.IRON_BARS)) {
						event.blockList().remove(i);
					}
					i++;
				}
			}
		}
	}
}
