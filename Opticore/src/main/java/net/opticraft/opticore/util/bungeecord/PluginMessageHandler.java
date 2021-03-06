package net.opticraft.opticore.util.bungeecord;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.staff.ban.BanUtil;
import net.opticraft.opticore.staff.kick.KickUtil;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class PluginMessageHandler implements PluginMessageListener {

	public Main plugin;

	public Config config;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public BanUtil banUtil;
	public KickUtil kickUtil;
	public TeleportUtil teleportUtil;

	public PluginMessageHandler(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.banUtil = this.plugin.banUtil;
		this.kickUtil = this.plugin.kickUtil;
		this.teleportUtil = this.plugin.teleportUtil;
	}

	public String chatSymbol(String color, String symbol) {
		return ChatColor.WHITE + "[" + ChatColor.valueOf(color.toUpperCase()) + symbol + ChatColor.WHITE + "] ";
	}

	public DataInputStream msgIn(ByteArrayDataInput in) {
		short len = in.readShort();
		byte[] msgbytes = new byte[len];
		in.readFully(msgbytes);
		DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
		return msgin;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subChannel = in.readUTF();
		
		if (subChannel.equals("PlayerList")) {
			
			String server = in.readUTF();
			String playerList = in.readUTF();
			
			if (plugin.servers.containsKey(server)) {
				List<String> players = new ArrayList<String>();
				plugin.servers.get(server).setPlayers(players);
			}

			if (playerList.length() > 0) {

				//List<String> players = Arrays.asList(playerList.split(", "));
				
				List<String> players = new ArrayList<String>(Arrays.asList(playerList.split(", ")));
				
				//System.out.println(server + ":" + players);

				if (plugin.servers.containsKey(server)) {
					plugin.servers.get(server).setPlayers(players);
				}
			}
		}

		if (subChannel.equals("OpticoreChat")) {
			DataInputStream msgin = msgIn(in);
			try {

				String serverShort = msgin.readUTF();
				String group = msgin.readUTF();
				String groupColor = msgin.readUTF();
				String playerName = msgin.readUTF();
				String playerMessage = msgin.readUTF();

				for (Player online : plugin.getServer().getOnlinePlayers()) {
					if (plugin.players.get(online.getName()).getSettings().get("player_chat").getValue() == 1) {
						online.spigot().sendMessage(bungeecordUtil.chatMessage(serverShort, "GOLD", ChatColor.GOLD + "Click to show server selection", "/servers", group, groupColor, playerName, ChatColor.GOLD + "Click to view player profile", "/player " + playerName, playerMessage));
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreStaffchat")) {
			DataInputStream msgin = msgIn(in);
			try {

				String group = msgin.readUTF();
				String groupColor = msgin.readUTF();
				String playerName = msgin.readUTF();
				String playerMessage = msgin.readUTF();

				for (Player online : plugin.getServer().getOnlinePlayers()) {
					if (online.hasPermission("opticore.staffchat")) {
						online.spigot().sendMessage(bungeecordUtil.chatMessage("S", "BLACK", ChatColor.GOLD + "Click to toggle speaking in staff chat", "/staffchat", 
								group, groupColor, 
								playerName, ChatColor.GOLD + "Click to view player profile", "/player " + playerName, playerMessage));
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreConnectInfo")) {
			DataInputStream msgin = msgIn(in);
			try {

				String playerName = msgin.readUTF();
				plugin.playerHasChangedServer.add(playerName);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreConnect")) {
			DataInputStream msgin = msgIn(in);
			try {

				String playerName = msgin.readUTF();
				String playerServerName = msgin.readUTF();
				String type = msgin.readUTF();

				if (!playerServerName.toLowerCase().equals(config.getServerName().toLowerCase())) {

					//util.debug("Received " + type + " message from " + playerServerName + " for " + playerName);

					for (Player online : plugin.getServer().getOnlinePlayers()) {

						if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
							if (type.equals("connect")) {
								util.sendStyledMessage(online, null, "GREEN", "+", "GOLD", playerName + " has connected via " + playerServerName + ".");
							}
							if (type.equals("disconnect")) {
								util.sendStyledMessage(online, null, "RED", "-", "GOLD", playerName + " has disconnected.");
							}
						}

						if (!plugin.players.containsKey(online.getName()) || plugin.players.get(online.getName()).getSettings().get("server_change").getValue() == 1) {
							if (type.equals("change")) {
								util.sendStyledMessage(online, null, "YELLOW", ">", "GOLD", playerName + " has changed to " + playerServerName + ".");
							}
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreTeleportInfo")) {
			DataInputStream msgin = msgIn(in);
			try {

				String playerName = msgin.readUTF();
				String targetName = msgin.readUTF();
				String type = msgin.readUTF();
				String playerServer = msgin.readUTF();

				if (type.equals("tp")) {
					plugin.teleport.put(playerName, targetName);
				} else if (type.equals("tphere")) {
					Player targetPlayer = plugin.getServer().getPlayer(targetName);
					bungeecordUtil.sendTeleportInfo(targetPlayer.getName(), playerName, playerServer, "tp", "");
					bungeecordUtil.sendPlayerToServer(targetPlayer, playerServer);
				} else if (type.equals("tpr")) {
					if (plugin.players.get(targetName).getSettings().get("teleport_request").getValue() == 1) {
						teleportUtil.teleportRequest(player.getName(), targetName);
					}
					teleportUtil.teleportRequest(playerName, targetName);
				} else if (type.equals("tpd")) {
					teleportUtil.teleportDeny(playerName, targetName);
				} else if (type.equals("tpa")) {
					teleportUtil.teleportAccept(playerName, targetName);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreTeleport")) {
			DataInputStream msgin = msgIn(in);
			try {

				String playerName = msgin.readUTF();
				String targetName = msgin.readUTF();
				Player player1 = plugin.getServer().getPlayer(playerName);
				Player target1 = plugin.getServer().getPlayer(targetName);

				if (player1 != null && target1 != null) {
					Location location = target1.getLocation();
					player1.teleport(location);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreMessage")) {
			DataInputStream msgin = msgIn(in);
			try {

				String playerName = msgin.readUTF();
				String targetName = msgin.readUTF();
				String message1 = msgin.readUTF();
				Player targetPlayer = plugin.getServer().getPlayer(targetName);

				if (targetPlayer != null) {
					if (plugin.players.get(targetPlayer.getName()).getSettings().get("direct_message").getValue() == 1) {
						util.sendStyledMessage(null, targetPlayer, util.parseColor(targetPlayer.getName()), "MSG", "WHITE", playerName + " > You: " + ChatColor.valueOf(util.parseColor(targetPlayer.getName())) + message1);
						plugin.players.get(targetPlayer.getName()).setLastMessageFrom(playerName);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreBan")) {
			DataInputStream msgin = msgIn(in);
			try {

				String target = msgin.readUTF();
				String sender = msgin.readUTF();
				String length = msgin.readUTF();
				String reason = msgin.readUTF();

				if (plugin.getServer().getPlayer(target) != null) {
					Player targetPlayer = plugin.getServer().getPlayer(target);
					banUtil.banPlayer(targetPlayer.getName(), sender, Integer.parseInt(length), reason);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreFreeze")) {
			DataInputStream msgin = msgIn(in);
			try {

				String targetName = msgin.readUTF();
				String senderName = msgin.readUTF();
				String length = msgin.readUTF();
				String reason = msgin.readUTF();
				Player targetPlayer = plugin.getServer().getPlayer(targetName);

				if (targetPlayer != null) {
					banUtil.banPlayer(targetPlayer.getName(), senderName, Integer.parseInt(length), reason);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreKick")) {
			DataInputStream msgin = msgIn(in);
			try {

				String targetName = msgin.readUTF();
				String senderName = msgin.readUTF();
				String reason = msgin.readUTF();
				Player targetPlayer = plugin.getServer().getPlayer(targetName);

				if (targetPlayer != null) {
					kickUtil.kickPlayer(targetPlayer, senderName, reason);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreMute")) {
			DataInputStream msgin = msgIn(in);
			try {

				String targetName = msgin.readUTF();
				String senderName = msgin.readUTF();
				String length = msgin.readUTF();
				String reason = msgin.readUTF();
				Player targetPlayer = plugin.getServer().getPlayer(targetName);

				if (targetPlayer != null) {
					banUtil.banPlayer(targetPlayer.getName(), senderName, Integer.parseInt(length), reason);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (subChannel.equals("OpticoreWarn")) {
			DataInputStream msgin = msgIn(in);
			try {

				String targetName = msgin.readUTF();
				String senderName = msgin.readUTF();
				String length = msgin.readUTF();
				String reason = msgin.readUTF();
				Player targetPlayer = plugin.getServer().getPlayer(targetName);

				if (targetPlayer != null) {
					banUtil.banPlayer(targetPlayer.getName(), senderName, Integer.parseInt(length), reason);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
