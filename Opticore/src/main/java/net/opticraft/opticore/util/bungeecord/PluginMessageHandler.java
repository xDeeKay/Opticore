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
import net.opticraft.opticore.teleport.TeleportMethods;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Methods;

public class PluginMessageHandler implements PluginMessageListener {

	public Main plugin;

	public Config config;
	public Methods methods;
	public BungeecordMethods bungeecordMethods;
	
	public TeleportMethods teleportMethods;

	public PluginMessageHandler(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.methods = this.plugin.methods;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.teleportMethods = this.plugin.teleportMethods;
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
							online.spigot().sendMessage(bungeecordMethods.message(serverShort, playerGroupColor, playerGroup, playerName, message1));
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
						methods.debug("Received connect message from " + serverName + " for " + playerName);
						methods.sendStyledMessage(null, null, "GREEN", "+", "GOLD", playerName + " has connected via " + serverName + ".");

					} else if (type.equals("change")) {
						methods.debug("Received change message from " + serverName + " for " + playerName);
						methods.sendStyledMessage(null, null, "YELLOW", ">", "GOLD", playerName + " has changed to " + serverName + ".");

					} else if (type.equals("disconnect")) {
						methods.debug("Received disconnect message from " + serverName + " for " + playerName);
						methods.sendStyledMessage(null, null, "RED", "-", "GOLD", playerName + " has disconnected.");
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
					
					String server = msgin.readUTF(); //quest
					String type = msgin.readUTF(); //tpa
					
					String playerServer = msgin.readUTF(); //hub
					
					if (type.equals("tp")) {
						plugin.teleport.put(playerName, targetName);
						
					} else if (type.equals("tphere")) {
						Player targetPlayer = plugin.getServer().getPlayer(targetName);
						bungeecordMethods.sendTeleportInfo(targetPlayer.getName(), playerName, playerServer, "tp", "");
						bungeecordMethods.sendPlayerToServer(targetPlayer, playerServer);
					} else if (type.equals("tpr")) {
						teleportMethods.teleportRequest(playerName, targetName);
					} else if (type.equals("tpd")) {
						teleportMethods.teleportDeny(playerName, targetName);
					} else if (type.equals("tpa")) {
						teleportMethods.teleportAccept(playerName, targetName);
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
		}
	}
}
