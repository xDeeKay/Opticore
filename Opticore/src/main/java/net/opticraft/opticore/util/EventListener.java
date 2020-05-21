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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class EventListener implements Listener {

	public Main plugin;

	public Config config;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public MySQL mysql;

	public EventListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
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

		Player player = event.getPlayer();
		String playerName = player.getName();
		String uuid = player.getUniqueId().toString();
		String ip = event.getPlayer().getAddress().toString().replaceAll("/", "").split(":")[0];

		long timestamp = System.currentTimeMillis() / 1000;

		String server = config.getServerName();

		String accessKey = config.getLoggingAccessKey();

		// Login stuff
		String sURL = "http://api.ipapi.com/" + ip + "?access_key=" + accessKey;

		// Connect to the URL using java's native library
		URL url = new URL(sURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
		JsonObject jo = je.getAsJsonObject();

		if (!ip.equals("127.0.0.1")) {

			String country = jo.get("country_name").toString();
			String region = jo.get("region_name").toString();
			String city = jo.get("city").toString();

			// Log login to the database
			mysql.insert("oc_login", 
					Arrays.asList("uuid", "name", "ip", "country", "region", "city", "timestamp", "server"), 
					Arrays.asList(uuid, playerName, ip, country, region, city, timestamp, server));
		}

		// Remove default join message
		event.setJoinMessage(null);

		int delay = 0;
		if (plugin.getServer().getOnlinePlayers().size() == 1) {
			delay = 1;
			util.debug(player.getName() + " is the only player, delay = " + delay);
		}

		new BukkitRunnable() {
			public void run() {

				if (plugin.playerHasChangedServer.contains(playerName)) {
					// Player has changed server

					// Send change messsge to server
					util.debug("Sent change message to server for " + player.getName());
					for (Player online : plugin.getServer().getOnlinePlayers()) {
						if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("server_change") == null || plugin.players.get(online.getName()).getSettings().get("server_change").getValue() == 1) {
							util.sendStyledMessage(online, null, "YELLOW", ">", "GOLD", player.getName() + " has changed to " + config.getServerName() + ".");
						}
					}

					// Send change messsge to network
					util.debug("Sent change message to network for " + player.getName());
					bungeecordUtil.sendConnectMessage(player, "change");

					// Remove player from playerHasChangedServer
					plugin.playerHasChangedServer.remove(playerName);

				} else {
					// Player is connecting to network

					// Send connect message to server
					util.debug("Sent connect message to server for " + player.getName());
					for (Player online : plugin.getServer().getOnlinePlayers()) {
						if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("connect_disconnect") == null || plugin.players.get(online.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
							util.sendStyledMessage(online, null, "GREEN", "+", "GOLD", player.getName() + " has connected via " + config.getServerName() + ".");
						}
					}

					// Send connect message to network
					util.debug("Sent connect message to network for " + player.getName());
					bungeecordUtil.sendConnectMessage(player, "connect");
				}
			}
		}.runTaskLater(plugin, delay * 20);

		// Remind player to vote
		if (plugin.rewardUtil.canVote(player, true)) {
			if (mysql.getUUIDColumnValue(player.getName(), "oc_settings", "reward_reminder") == 1) {
				for (String voteMessage : config.getReminderVoteMessages()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes ('&', voteMessage));
				}
			}
		}

		// Remind player to claim their daily reward
		if (plugin.rewardUtil.getTimeSinceLastDaily(player, timestamp, true) >= 86400) {
			if (mysql.getUUIDColumnValue(player.getName(), "oc_settings", "reward_reminder") == 1) {
				for (String dailyMessage : config.getReminderDailyMessages()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes ('&', dailyMessage));
				}
			}
		}

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
			util.debug("Sent disconnect message to server for " + player.getName());
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
					util.sendStyledMessage(online, null, "RED", "-", "GOLD", player.getName() + " has disconnected.");
				}
			}

			// Send disconnect message to network
			util.debug("Sent disconnect message to network for " + player.getName());
			bungeecordUtil.sendConnectMessage(player, "disconnect");
		}
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		String playerName = ChatColor.stripColor(player.getDisplayName());

		String message = event.getMessage();
		String messageCodes = ChatColor.translateAlternateColorCodes('&', message);

		// Can player use color codes
		if (messageCodes.contains(String.valueOf(ChatColor.COLOR_CHAR))) {
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
		for (String part : messageParts) {
			try {
				new URL(part);
				if (!player.hasPermission("opticore.chat.url")) {
					player.sendMessage(ChatColor.RED + "Your message can not contain a url.");
					event.setCancelled(true);
					return;
				}
			} catch (MalformedURLException e) {
				// no url
			}
		}

		String serverShort = config.getServerShort();
		String group = util.getPlayerGroup(player);
		String groupColor = util.getPlayerGroupColor(player);

		if (plugin.staffchat.contains(player.getName())) {

			event.setCancelled(true);

			for (Player online : plugin.getServer().getOnlinePlayers()) {
				event.getRecipients().remove(online);
				if (online.hasPermission("opticore.staffchat")) {
					//online.spigot().sendMessage(bungeecordUtil.staffchatMessage(groupColor, group, playerName, message));
					online.spigot().sendMessage(bungeecordUtil.chatMessage("S", "BLACK", ChatColor.GOLD + "Click to toggle speaking in staff chat", "/staffchat", group, groupColor, playerName, ChatColor.GOLD + "Click to view player profile", "/player " + playerName, message));
				}
			}

			bungeecordUtil.sendStaffchatMessage(player, group, groupColor, playerName, message);

		} else if (plugin.players.get(player.getName()).getSettings().get("player_chat").getValue() == 1) {

			for (Player online : plugin.getServer().getOnlinePlayers()) {
				event.getRecipients().remove(online);
				if (plugin.players.get(online.getName()).getSettings().get("player_chat").getValue() == 1) {
					//online.spigot().sendMessage(bungeecordUtil.message(serverShort, groupColor, group, playerName, message));
					online.spigot().sendMessage(bungeecordUtil.chatMessage(serverShort, "GOLD", ChatColor.GOLD + "Click to show server selection", "/servers", group, groupColor, playerName, ChatColor.GOLD + "Click to view player profile", "/player " + playerName, message));
				}
			}

			bungeecordUtil.sendChatMessage(player, serverShort, group, groupColor, playerName, message);

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

		if (buffer.toLowerCase().startsWith("/addchallenge ")) {
			completions.clear();
		}
		
		if (buffer.toLowerCase().startsWith("/challenges ")) {
			completions.clear();
		}
		
		if (buffer.toLowerCase().startsWith("/delchallenge ")) {
			completions.clear();
			Set<String> challenges = plugin.challenges.keySet();
			tabComplete(args, completions, challenges);
		}

		if (buffer.toLowerCase().startsWith("/information ") || buffer.toLowerCase().startsWith("/info ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/livemap ") || buffer.toLowerCase().startsWith("/map ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/ranks ") || buffer.toLowerCase().startsWith("/rank ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/rules ") || buffer.toLowerCase().startsWith("/rule ")) {
			completions.clear();
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

		if (buffer.toLowerCase().startsWith("/exit ")) {
			completions.clear();
		}

		if (buffer.toLowerCase().startsWith("/opticraft ") || buffer.toLowerCase().startsWith("/opti ") || buffer.toLowerCase().startsWith("/oc ")) {
			completions.clear();
			Set<String> guis = plugin.gui.keySet();
			tabComplete(args, completions, guis);
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

		if (buffer.toLowerCase().startsWith("/givepoints ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
		}

		if (buffer.toLowerCase().startsWith("/reward ") || buffer.toLowerCase().startsWith("/rewards ")) {
			completions.clear();
			List<String> commands = new ArrayList<String>();
			commands.add("buy");
			commands.add("daily");
			commands.add("donate");
			commands.add("points");
			commands.add("store");
			commands.add("vote");
			commands.add("challenges");
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

		if (buffer.toLowerCase().startsWith("/takepoints ")) {
			completions.clear();

			List<String> list = new ArrayList<String>();
			for (String server : plugin.servers.keySet()) {
				for (String online : plugin.servers.get(server).getPlayers()) {
					list.add(online);
				}
			}
			tabComplete(args, completions, list);
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
			settings.add("reward_reminder");
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

		if (buffer.toLowerCase().startsWith("/tpcancel ") || buffer.toLowerCase().startsWith("/tpc ") || buffer.toLowerCase().startsWith("/tpnvm ")) {
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

					// set egg on top the exit portal
					Block block = event.getEntity().getWorld().getBlockAt(0, 65, 0);
					block.setType(Material.DRAGON_EGG);

				}
			}.runTaskLater(plugin, 15 * 20);
		}
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		String playerName = event.getPlayer().getName();
		String uuid = player.getUniqueId().toString();

		String command = event.getMessage();

		String server = config.getServerName();

		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.FLOOR);

		Location location = player.getLocation();
		String world = location.getWorld().getName();
		String x = df.format(location.getX());
		String y = df.format(location.getY());
		String z = df.format(location.getZ());
		String yaw = df.format(location.getYaw());
		String pitch = df.format(location.getPitch());
		String locationString = String.join(" ", x, y, z, yaw, pitch);

		long timestamp = System.currentTimeMillis() / 1000;

		// Log command to the database
		mysql.insert("oc_command", 
				Arrays.asList("uuid", "name", "server", "world", "location", "timestamp", "command"), 
				Arrays.asList(uuid, playerName, server, world, locationString, timestamp, command));
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {

		Entity entity = event.getEntity();
		Block block = event.getBlock();

		// Handle wither and wither skull change block within the arena, and cancel change block anywhere else
		if (entity.getType().equals(EntityType.WITHER) || entity.getType().equals(EntityType.WITHER_SKULL)) {
			if (util.getRegionName(block.getLocation()) != null && util.getRegionName(block.getLocation()).equals("witherarena")) {
				block.setType(Material.AIR);
			} else {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {

		Entity entity = event.getEntity();
		List<Block> blocks = event.blockList();

		// Handle ender crystal iron bar damage within the end world, and cancel block damage anywhere else
		if (entity.getType().equals(EntityType.ENDER_CRYSTAL)) {

			if (!entity.getWorld().getEnvironment().equals(Environment.THE_END)) {
				blocks.clear();

			} else {
				Iterator<Block> it = blocks.iterator();
				while (it.hasNext()) {
					Block block = it.next();
					if (!block.getType().equals(Material.IRON_BARS)) {
						it.remove();
						event.blockList().remove(it);
					}
				}
			}
		}

		// Handle wither and wither skull block damage within the arena, and cancel block damage anywhere else
		if (entity.getType().equals(EntityType.WITHER) || entity.getType().equals(EntityType.WITHER_SKULL)) {
			for (Block block : blocks) {
				if (util.getRegionName(block.getLocation()) != null && util.getRegionName(block.getLocation()).equals("witherarena")) {
					block.setType(Material.AIR);
				} else {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		World world = player.getLocation().getWorld();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		// Cancel bed explosions within the nether and the end world
		if (world.getEnvironment().equals(Environment.NETHER) || world.getEnvironment().equals(Environment.THE_END)) {
			if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
				if (block.getType().toString().endsWith("BED")) {
					event.setCancelled(true);
				}
			}
		}

		// Cancel spawner mob changes by using spawn eggs
		if (util.getItemInAnyHand(player) != null && util.getItemInAnyHand(player).getType().toString().endsWith("SPAWN_EGG")) {
			if (block.getType().equals(Material.SPAWNER) && action.equals(Action.RIGHT_CLICK_BLOCK)) {
				if (!player.hasPermission("opticore.spawner")) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {

		Player player = event.getPlayer();
		World world = player.getLocation().getWorld();

		if (event.getBedEnterResult().equals(BedEnterResult.OK)) {

			int playersSleeping = 1;

			for (Player online : world.getPlayers()) {
				if (online.isSleeping()) {
					playersSleeping++;
				}
			}

			int playersInWorld = world.getPlayers().size();

			int playersNeeded = playersInWorld / 2 - playersSleeping + 1;

			for (Player online : world.getPlayers()) {
				String type = "players";
				if (playersNeeded == 1) {
					type = "player";
				}
				online.sendMessage(ChatColor.YELLOW + player.getName() + " is attempting to sleep. " + playersNeeded + " more " + type + " needed to skip the night.");
			}

			if (playersSleeping > playersInWorld / 2) {
				
				world.setTime(0);
				
				if (world.hasStorm() || world.isThundering()) {
					world.setStorm(false);
					world.setThundering(false);
				}
			}
		}
	}
}
