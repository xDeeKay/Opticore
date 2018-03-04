package net.opticraft.opticore.teleport;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TeleportUtil {

	public Main plugin;

	public Config config;
	
	public Util util;
	
	public BungeecordUtil bungeecordUtil;
	
	public BukkitTask task;

	public TeleportUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}
	
	
	
	public void teleportRequest(String playerName, String targetName) {

		if (plugin.getServer().getPlayer(playerName) != null) {
			// Player is online

			// Create player from playerName
			Player player = plugin.getServer().getPlayer(playerName);
			
			plugin.players.get(player.getName()).setTprOutgoing(targetName);
		}

		if (plugin.getServer().getPlayer(targetName) != null) {
			// Target is online
			
			// Create target from targetName
			Player target = plugin.getServer().getPlayer(targetName);
			
			// Add playerName to target teleport requests
			plugin.players.get(target.getName()).getTprIncoming().add(playerName);
			
			// Send teleport request message to target
			util.sendStyledMessage(target, null, "GREEN", "/", "GOLD", "Received teleport request from player '" + playerName + "'.");
			
			// Send teleport request command usage message to target
			String usage;
			if (plugin.players.get(target.getName()).getTprIncoming().size() > 1) {
				usage = "Type '/tpa <" + playerName + ">' to accept or '/tpd <" + playerName + ">' to deny.";
			} else {
				usage = "Type '/tpa' to accept or '/tpd' to deny.";
			}
			util.sendStyledMessage(target, null, "GREEN", "/", "GOLD", usage);
			
			// Start command timeout timer if present
			if (config.getTeleportTprTimeout() > 0) {
				this.task = new BukkitRunnable() {
					public void run() {
						teleportDeny(playerName, target.getName());
					}
				}.runTaskLater(plugin, config.getTeleportTprTimeout() * 20);
			}
		}
	}
	
	public void teleportDeny(String playerName, String targetName) {
		
		if (plugin.getServer().getPlayer(playerName) != null) {
			// Player is online
			
			// Create player from playerName
			Player player = plugin.getServer().getPlayer(playerName);
			
			// Remove targetName from player teleport requests
			plugin.players.get(player.getName()).getTprIncoming().remove(targetName);
		}
		
		if (plugin.getServer().getPlayer(targetName) != null) {
			// Target is online
			
			// Create target from targetName
			Player target = plugin.getServer().getPlayer(targetName);
			
			// Send teleport deny message to target
			util.sendStyledMessage(target, null, "RED", "/", "GOLD", "Teleport request denied by player '" + playerName + "'.");
			
			// Remove playerName from target teleport to
			plugin.players.get(target.getName()).setTprOutgoing(null);
		}
	}
	
	public void teleportAccept(String playerName, String targetName) {
		
		if (plugin.getServer().getPlayer(playerName) != null) {
			// Player is online
			
			// Create player from playerName
			Player player = plugin.getServer().getPlayer(playerName);
			
			// Remove targetName from player teleport requests
			plugin.players.get(player.getName()).getTprIncoming().remove(targetName);
		}
		
		if (plugin.getServer().getPlayer(targetName) != null) {
			// Target is online
			
			// Create target from targetName
			Player target = plugin.getServer().getPlayer(targetName);
			
			// Send teleport accept message to target
			util.sendStyledMessage(target, null, "GREEN", "/", "GOLD", "Teleport request accepted by player '" + playerName + "'.");
			
			// Remove playerName from target teleport to
			plugin.players.get(target.getName()).setTprOutgoing(null);
			
			if (plugin.getServer().getPlayer(playerName) == null) {
				
				String server = bungeecordUtil.getPlayerServer(playerName);
				
				if (server != null) {
					// Target is on another server
					
					// Send teleport info to other server to get the player ready
					bungeecordUtil.sendTeleportInfo(target.getName(), playerName, server, "tp", "");
					
					// Send player to other server
					bungeecordUtil.sendPlayerToServer(target, server);
					
				} else {
					// Target is offline
					util.sendStyledMessage(target, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
				}
			}
		}
	}
}
