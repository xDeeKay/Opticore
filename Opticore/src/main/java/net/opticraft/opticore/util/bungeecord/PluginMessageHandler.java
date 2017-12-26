package net.opticraft.opticore.util.bungeecord;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

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

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		if (plugin.getServer().getOnlinePlayers().size() != 0) {

			ByteArrayDataInput in = ByteStreams.newDataInput(message);
			String subChannel = in.readUTF();

			if (subChannel.equals("PlayerCount")) {
				String server = in.readUTF();
				int playerCount = in.readInt();
				plugin.playerCount.put(server, playerCount);

			}

			if (subChannel.equals("PlayerList")) {
				String server = in.readUTF();
				String playerList = in.readUTF();
				//String[] playerList = in.readUTF().split(", ");
				plugin.playerList.put(server, playerList);
			}

			if (subChannel.equals("OpticoreChat")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					String serverShort = msgin.readUTF();
					String playerGroup = msgin.readUTF();
					String playerGroupColor = msgin.readUTF();
					String playerName = msgin.readUTF();
					String message1 = msgin.readUTF();
					for (Player online : plugin.getServer().getOnlinePlayers()) {
						if (plugin.players.get(player.getName()).getSettingsPlayerChat() == 1) {
							online.spigot().sendMessage(bungeecordUtil.message(serverShort, playerGroupColor, playerGroup, playerName, message1));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (subChannel.equals("OpticoreConnectInfo")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					String playerName = msgin.readUTF();
					plugin.playerHasChangedServer.add(playerName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (subChannel.equals("OpticoreConnect")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					String playerName = msgin.readUTF();
					String serverName = msgin.readUTF();
					String type = msgin.readUTF();

					if (type.equals("connect")) {
						util.debug("Received connect message from " + serverName + " for " + playerName);
						util.sendStyledMessage(null, null, "GREEN", "+", "GOLD", playerName + " has connected via " + serverName + ".");

					} else if (type.equals("change")) {
						util.debug("Received change message from " + serverName + " for " + playerName);
						util.sendStyledMessage(null, null, "YELLOW", ">", "GOLD", playerName + " has changed to " + serverName + ".");

					} else if (type.equals("disconnect")) {
						util.debug("Received disconnect message from " + serverName + " for " + playerName);
						util.sendStyledMessage(null, null, "RED", "-", "GOLD", playerName + " has disconnected.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (subChannel.equals("OpticoreTeleportInfo")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {

					String playerName = msgin.readUTF();//xdeekay
					String targetName = msgin.readUTF();//slenderman
					
					String type = msgin.readUTF(); //tpa
					
					String playerServer = msgin.readUTF(); //hub
					
					if (type.equals("tp")) {
						plugin.teleport.put(playerName, targetName);
						
					} else if (type.equals("tphere")) {
						Player targetPlayer = plugin.getServer().getPlayer(targetName);
						bungeecordUtil.sendTeleportInfo(targetPlayer.getName(), playerName, playerServer, "tp", "");
						bungeecordUtil.sendPlayerToServer(targetPlayer, playerServer);
					} else if (type.equals("tpr")) {
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
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
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
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					String playerName = msgin.readUTF();
					String targetName = msgin.readUTF();
					String message1 = msgin.readUTF();
					
					Player targetPlayer = plugin.getServer().getPlayer(targetName);

					if (targetPlayer != null) {
						util.sendStyledMessage(null, targetPlayer, "LIGHT_PURPLE", "M", "WHITE", playerName + " > You: " + ChatColor.GRAY + message1);
						plugin.players.get(targetPlayer.getName()).setLastMessageFrom(playerName);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (subChannel.equals("OpticoreKick")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
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
			
			if (subChannel.equals("OpticoreBan")) {
				short len = in.readShort();
				byte[] msgbytes = new byte[len];
				in.readFully(msgbytes);
				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					String targetName = msgin.readUTF();
					String senderName = msgin.readUTF();
					String length = msgin.readUTF();
					String reason = msgin.readUTF();
					
					Player targetPlayer = plugin.getServer().getPlayer(targetName);

					if (targetPlayer != null) {
						banUtil.banPlayer(targetPlayer, senderName, Integer.parseInt(length), reason);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
