package net.opticraft.opticore.util.bungeecord;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

	public void sendBungeecordMessage(Player player, String channel, String[] args, String[] msgoutArgs) {

		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF(channel);

		for (String arg : args) {
			out.writeUTF(arg);
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
	
	public void sendPlayerToServer(Player player, String server) {

		// Tell target server player is changing server
		sendBungeecordMessage(player, "Forward", new String[]{server, "OpticoreConnectInfo"}, new String[]{player.getName()});

		// Add player to playerIsChangingServer
		plugin.playerIsChangingServer.put(player.getName(), server);

		// Send player to target server
		sendBungeecordMessage(player, "Connect", new String[]{server}, null);
	}

	public void sendConnectMessage(Player player, String type) {

		for (String server : plugin.servers.keySet()) {
			if (!server.equals(config.getServerName().toLowerCase())) {
				if (plugin.servers.get(server).getPlayers().size() >= 1) {

					sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreConnect"}, new String[]{player.getName(), config.getServerName(), type});
				}
			}
		}
	}

	public void sendChatMessage(Player player, String serverShort, String group, String groupColor, String playerName, String message) {

		for (String server : plugin.servers.keySet()) {
			if (!server.equals(config.getServerName().toLowerCase())) {
				if (plugin.servers.get(server).getPlayers().size() >= 1) {

					sendBungeecordMessage(player, "Forward", new String[]{server, "OpticoreChat"}, new String[]{serverShort, group, groupColor, playerName, message});
				}
			}
		}
	}

	public void sendStaffchatMessage(Player player, String group, String groupColor, String playerName, String message) {

		for (String server : plugin.servers.keySet()) {
			if (!server.equals(config.getServerName().toLowerCase())) {
				if (plugin.servers.get(server).getPlayers().size() >= 1) {

					sendBungeecordMessage(player, "Forward", new String[]{server, "OpticoreStaffchat"}, new String[]{group, groupColor, playerName, message});
				}
			}
		}
	}
	
	public void sendBanCommand(String server, String target, String sender, String length, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreBan"}, new String[]{target, sender, length, reason});
	}

	public void sendKickCommand(String server, String target, String sender, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreKick"}, new String[]{target, sender, reason});
	}
	
	public void sendFreezeCommand(String server, String target, String sender, String length, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreFreeze"}, new String[]{target, sender, length, reason});
	}
	
	public void sendMuteCommand(String server, String target, String sender, String length, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreMute"}, new String[]{target, sender, length, reason});
	}

	public void sendWarnCommand(String server, String target, String sender, String reason) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreWarn"}, new String[]{target, sender, reason});
	}

	public void sendTeleportInfo(String player, String target, String server, String type, String playerServer) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreTeleportInfo"}, new String[]{player, target, type, playerServer});
	}

	public void sendMessageToPlayer(String player, String target, String server, String message) {
		sendBungeecordMessage(null, "Forward", new String[]{server, "OpticoreMessage"}, new String[]{player, target, message});
	}

	public void requestServerPlayerList(String server) {
		sendBungeecordMessage(null, "PlayerList", new String[]{server}, null);
	}

	public void runServerPlayerListTimer() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
					for (String server : plugin.servers.keySet()) {
						requestServerPlayerList(server);
					}
				}
			}

		}.runTaskTimer(plugin, 0, 2 * 20);
	}

	public TextComponent chatMessage(String symbol, String symbolColor, String symbolHover, String symbolCommand, String group, String groupColor, String playerName, String playerNameHover, String playerNameCommand, String message) {

		TextComponent tc = new TextComponent("");

		TextComponent bracketOpenTC = new TextComponent("[");
		bracketOpenTC.setColor(ChatColor.WHITE);

		TextComponent bracketCloseTC = new TextComponent("] ");
		bracketCloseTC.setColor(ChatColor.WHITE);

		if (groupColor.endsWith("0")) {
			groupColor = "BLACK";
		} else if (groupColor.endsWith("1")) {
			groupColor = "DARK_BLUE";
		} else if (groupColor.endsWith("2")) {
			groupColor = "DARK_GREEN";
		} else if (groupColor.endsWith("3")) {
			groupColor = "DARK_AQUA";
		} else if (groupColor.endsWith("4")) {
			groupColor = "DARK_RED";
		} else if (groupColor.endsWith("5")) {
			groupColor = "DARK_PURPLE";
		} else if (groupColor.endsWith("6")) {
			groupColor = "GOLD";
		} else if (groupColor.endsWith("7")) {
			groupColor = "GRAY";
		} else if (groupColor.endsWith("8")) {
			groupColor = "DARK_GRAY";
		} else if (groupColor.endsWith("9")) {
			groupColor = "BLUE";
		} else if (groupColor.endsWith("a")) {
			groupColor = "GREEN";
		} else if (groupColor.endsWith("b")) {
			groupColor = "AQUA";
		} else if (groupColor.endsWith("c")) {
			groupColor = "RED";
		} else if (groupColor.endsWith("d")) {
			groupColor = "LIGHT_PURPLE";
		} else if (groupColor.endsWith("e")) {
			groupColor = "YELLOW";
		} else if (groupColor.endsWith("f")) {
			groupColor = "WHITE";
		} else {
			groupColor = "WHITE";
		}

		ChatColor groupChatColor = ChatColor.valueOf(groupColor);

		TextComponent symbolTC = new TextComponent(symbol);
		symbolTC.setColor(ChatColor.valueOf(symbolColor.toUpperCase()));
		BaseComponent[] symbolHoverBC = new ComponentBuilder(symbolHover).create();
		symbolTC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, symbolHoverBC));
		symbolTC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, symbolCommand));

		TextComponent groupTC = new TextComponent(group);
		groupTC.setColor(groupChatColor);

		TextComponent playerNameTC = new TextComponent(playerName);
		playerNameTC.setColor(groupChatColor);
		BaseComponent[] playerNameHoverBC = new ComponentBuilder(playerNameHover).create();
		playerNameTC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, playerNameHoverBC));
		playerNameTC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, playerNameCommand));

		TextComponent colonTC = new TextComponent(": ");
		colonTC.setColor(ChatColor.WHITE);

		tc.addExtra(bracketOpenTC);
		tc.addExtra(symbolTC);
		tc.addExtra(bracketCloseTC);

		tc.addExtra(bracketOpenTC);
		tc.addExtra(groupTC);
		tc.addExtra(bracketCloseTC);

		tc.addExtra(playerNameTC);
		tc.addExtra(colonTC);

		String messageColored = ChatColor.translateAlternateColorCodes('&', message);
		BaseComponent[] messageBC = TextComponent.fromLegacyText(messageColored);
		TextComponent messageTC = new TextComponent(messageBC);
		tc.addExtra(messageTC);

		return tc;
	}
}
