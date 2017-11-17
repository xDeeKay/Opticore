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
import net.opticraft.opticore.util.Config;

public class PluginMessageHandler implements PluginMessageListener {

	public Main plugin;
	
	public BungeecordMethods bungeecordMethods;

	public Config config;

	public PluginMessageHandler(Main plugin) {
		this.plugin = plugin;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.config = this.plugin.config;
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
						String uuid = online.getUniqueId().toString();
						if (plugin.players.get(uuid).getSettingsPlayerChat() == 1) {
							online.spigot().sendMessage(bungeecordMethods.message(serverShort, playerGroupColor, playerGroup, playerName, message1));
						}
					}
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
						if (!serverName.equals(config.getServerName())) {
							plugin.getServer().broadcastMessage(chatSymbol("GREEN", "+") + ChatColor.GOLD + playerName + " has connected via " + serverName + ".");
							System.out.println("[Opticore" + config.getServerName() + "] Received connect message for " + playerName + " from " + serverName);
						}

					} else if (type.equals("disconnect")) {
						plugin.getServer().broadcastMessage(chatSymbol("RED", "-") + ChatColor.GOLD + playerName + " has disconnected.");
						System.out.println("[Opticore" + config.getServerName() + "] Received disconnect message for " + playerName + " from " + serverName);

					} else if (type.equals("change")) {
						plugin.playerPostChangeServer.add(playerName);
						plugin.getServer().broadcastMessage(chatSymbol("YELLOW", ">") + ChatColor.GOLD + playerName + " has changed to " + serverName + ".");
						System.out.println("[Opticore" + config.getServerName() + "] Received change message for " + playerName + " from " + serverName);
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
