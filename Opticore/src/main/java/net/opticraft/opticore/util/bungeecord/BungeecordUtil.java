package net.opticraft.opticore.util.bungeecord;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class BungeecordUtil {

	public Main plugin;

	public Config config;

	public Util util;

	public BungeecordUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public void sendBungeecordMessage(Player player, String channel, String[] outArgs, String[] msgoutArgs) {

		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF(channel);
		for (String outArg : outArgs) {
			out.writeUTF(outArg);
		}

		if (channel.equals("Forward") && msgoutArgs != null) {

			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytes);

			try {
				for (String msgoutArg : msgoutArgs) {
					msgout.writeUTF(msgoutArg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
		}

		if (player == null) {
			player = Iterables.getFirst(plugin.getServer().getOnlinePlayers(), null);
		}

		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}

	public void sendConnectMessage(Player player, String type) {

		List<String> servers = new ArrayList<String>();
		servers.add("hub");
		servers.add("survival");
		servers.add("creative");
		servers.add("quest");
		//list.add("legacy");

		for (String server : servers) {
			if (!server.equals(config.getServerName().toLowerCase())) {

				if (!plugin.playerCount.containsKey(server)) {
					requestServerPlayerCount(server);
				}

				if (plugin.playerCount.containsKey(server) && getServerPlayerCount(server) >= 1) {
					sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreConnect"}, new String[]{player.getName(), config.getServerName(), type});
				}
			}
		}
	}

	public void sendPlayerToServer(Player player, String server) {

		// Add player to playerIsChangingServer
		plugin.playerIsChangingServer.put(player.getName(), server);

		// Tell target server player is changing server
		sendBungeecordMessage(player, "Forward", new String[]{server, "OpticoreConnectInfo"}, new String[]{player.getName()});

		// Send player to target server
		sendBungeecordMessage(player, "Connect", new String[]{server}, null);
	}

	public void sendChatMessage(Player player, String serverShort, String playerGroup, String playerGroupColor, String playerName, String message) {

		List<String> servers = new ArrayList<String>();
		servers.add("hub");
		servers.add("survival");
		servers.add("creative");
		servers.add("quest");
		//list.add("legacy");

		for (String server : servers) {
			//util.debug("[" + config.getServerName() + "] server:[" + server + "]");
			//util.debug("[" + config.getServerName() + "] server-name:[" + config.getServerName().toLowerCase() + "]");
			if (!server.equals(config.getServerName().toLowerCase())) {
				//util.debug("[" + config.getServerName() + "] server and server-name do not match, continuing...");
				if (!plugin.playerCount.containsKey(server)) {
					//util.debug("[" + config.getServerName() + "] playerCount does not contain server, requesting player count...");
					requestServerPlayerCount(server);
				}
				//util.debug("[" + config.getServerName() + "] about to check server player coun... this could go bad");
				if (getServerPlayerCount(server) >= 1) {
					//util.debug("[" + config.getServerName() + "] sending chat message to " + server);
					sendBungeecordMessage(player, "Forward", new String[]{server, "OpticoreChat"}, new String[]{serverShort, playerGroup, playerGroupColor, playerName, message});
				}
			} else {
				//util.debug("[" + config.getServerName() + "] server and server-name match, skipping...");
			}
		}
	}
	
	public void sendKickCommand(String server, String target, String sender, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreKick"}, new String[]{target, sender, reason});
	}
	
	public void sendBanCommand(String server, String target, String sender, String length, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreKick"}, new String[]{target, sender, length, reason});
	}
	
	public void sendStaffCommand(String player, String target, String server, String type) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreStaffCommand"}, new String[]{player, target, server, type});
	}

	public void sendTeleportInfo(String player, String target, String server, String type, String playerServer) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreTeleportInfo"}, new String[]{player, target, type, playerServer});
	}

	public void sendMessageToPlayer(String player, String target, String server, String message) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreMessage"}, new String[]{player, target, message});
	}

	public void requestServerPlayerCount(String server) {
		sendBungeecordMessage(null, "PlayerCount", new String[]{server}, null);
	}

	public void runServerPlayerCountTimer() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
					requestServerPlayerCount("hub");
					requestServerPlayerCount("survival");
					requestServerPlayerCount("creative");
					requestServerPlayerCount("quest");
					//requestPlayerCount("legacy");
				}
			}

		}.runTaskTimer(plugin, 0, 5 * 20);
	}

	public int getServerPlayerCount(String server) {
		return plugin.playerCount.get(server);
	}

	public void requestServerPlayerList(String server) {
		sendBungeecordMessage(null, "PlayerList", new String[]{server}, null);
	}

	public void runServerPlayerListTimer() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
					requestServerPlayerList("hub");
					requestServerPlayerList("survival");
					requestServerPlayerList("creative");
					requestServerPlayerList("quest");
					//requestPlayerCount("legacy");
				}
			}

		}.runTaskTimer(plugin, 0, 5 * 20);
	}

	public String getServerPlayerList(String server) {
		return plugin.playerList.get(server);
	}

	public String getPlayerServer(String target) {

		String server = null;

		target = target.toLowerCase();

		String hubPlayerList = getServerPlayerList("hub").toLowerCase();
		if (hubPlayerList.contains(target)) {
			server = "hub";
		}

		String survivalPlayerList = getServerPlayerList("survival").toLowerCase();
		if (survivalPlayerList.contains(target)) {
			server = "survival";
		}

		String creativePlayerList = getServerPlayerList("creative").toLowerCase();
		if (creativePlayerList.contains(target)) {
			server = "creative";
		}

		String questPlayerList = getServerPlayerList("quest").toLowerCase();
		if (questPlayerList.contains(target)) {
			server = "quest";
		}

		/*
		String legacyPlayerList = getServerPlayerList("legacy").toLowerCase();
		if (legacyPlayerList.contains(target)) {
			server = "legacy";
		}
		 */

		return server;
	}

	public TextComponent message(String serverShort, String playerGroupColor, String playerGroup, String playerName, String message) {

		TextComponent tc = new TextComponent("");

		TextComponent bracketOpenTC = new TextComponent("[");
		bracketOpenTC.setColor(ChatColor.WHITE);

		TextComponent bracketCloseTC = new TextComponent("] ");
		bracketCloseTC.setColor(ChatColor.WHITE);

		TextComponent serverShortTC = new TextComponent(serverShort);
		serverShortTC.setColor(ChatColor.GOLD);
		BaseComponent[] serverShortHoverText = new ComponentBuilder(ChatColor.GOLD + "Click to show server selection").create();
		serverShortTC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, serverShortHoverText));
		serverShortTC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/servers"));

		if (playerGroupColor.endsWith("0")) {
			playerGroupColor = "BLACK";
		} else if (playerGroupColor.endsWith("1")) {
			playerGroupColor = "DARK_BLUE";
		} else if (playerGroupColor.endsWith("2")) {
			playerGroupColor = "DARK_GREEN";
		} else if (playerGroupColor.endsWith("3")) {
			playerGroupColor = "DARK_AQUA";
		} else if (playerGroupColor.endsWith("4")) {
			playerGroupColor = "DARK_RED";
		} else if (playerGroupColor.endsWith("5")) {
			playerGroupColor = "DARK_PURPLE";
		} else if (playerGroupColor.endsWith("6")) {
			playerGroupColor = "GOLD";
		} else if (playerGroupColor.endsWith("7")) {
			playerGroupColor = "GRAY";
		} else if (playerGroupColor.endsWith("8")) {
			playerGroupColor = "DARK_GRAY";
		} else if (playerGroupColor.endsWith("9")) {
			playerGroupColor = "BLUE";
		} else if (playerGroupColor.endsWith("a")) {
			playerGroupColor = "GREEN";
		} else if (playerGroupColor.endsWith("b")) {
			playerGroupColor = "AQUA";
		} else if (playerGroupColor.endsWith("c")) {
			playerGroupColor = "RED";
		} else if (playerGroupColor.endsWith("d")) {
			playerGroupColor = "LIGHT_PURPLE";
		} else if (playerGroupColor.endsWith("e")) {
			playerGroupColor = "YELLOW";
		} else if (playerGroupColor.endsWith("f")) {
			playerGroupColor = "WHITE";
		} else {
			playerGroupColor = "WHITE";
		}
		ChatColor playerGroupColor1 = ChatColor.valueOf(playerGroupColor);

		TextComponent playerGroupTC = new TextComponent(playerGroup);
		playerGroupTC.setColor(playerGroupColor1);

		TextComponent playerNameTC = new TextComponent(playerName);
		playerNameTC.setColor(playerGroupColor1);
		BaseComponent[] playerNameHoverText = new ComponentBuilder(ChatColor.GOLD + "Click to view player profile").create();
		playerNameTC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, playerNameHoverText));
		playerNameTC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/opticraft player " + playerName));

		TextComponent colonTC = new TextComponent(": ");
		colonTC.setColor(ChatColor.WHITE);

		tc.addExtra(bracketOpenTC);
		tc.addExtra(serverShortTC);
		tc.addExtra(bracketCloseTC);

		tc.addExtra(bracketOpenTC);
		tc.addExtra(playerGroupTC);
		tc.addExtra(bracketCloseTC);

		tc.addExtra(playerNameTC);
		tc.addExtra(colonTC);

		String messageColored = ChatColor.translateAlternateColorCodes('&', message);
		BaseComponent[] messageBC = TextComponent.fromLegacyText(messageColored);
		TextComponent messageTC = new TextComponent(messageBC);
		tc.addExtra(messageTC);

		//tc.addExtra(ChatColor.translateAlternateColorCodes('&', message));

		return tc;
	}
}