package net.opticraft.opticore.teleport;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class TeleportMethods {

	public Main plugin;

	public Config config;
	
	public Methods methods;
	
	public BungeecordMethods bungeecordMethods;
	
	public BukkitTask task;

	public TeleportMethods(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.methods = this.plugin.methods;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
	}
	
	public Set<String> getTeleportRequests(Player player) {
		return plugin.players.get(player.getName()).getTeleportRequests();
	}
	
	public String getTeleportTo(Player player) {
		return plugin.players.get(player.getName()).getTeleportTo();
	}

	public void teleportRequest(String playerName, String targetName) {

		if (plugin.getServer().getPlayer(playerName) != null) {
			// Player is online

			// Create player from playerName
			Player player = plugin.getServer().getPlayer(playerName);
			
			plugin.players.get(player.getName()).setTeleportTo(targetName);
		}

		if (plugin.getServer().getPlayer(targetName) != null) {
			// Target is online
			
			// Create target from targetName
			Player target = plugin.getServer().getPlayer(targetName);
			
			// Add playerName to target teleport requests
			getTeleportRequests(target).add(playerName);
			
			// Send teleport request message to target
			methods.sendStyledMessage(target, null, "GREEN", "/", "GOLD", "Received teleport request from player '" + playerName + "'.");
			
			// Send teleport request command usage message to target
			String usage;
			if (getTeleportRequests(target).size() > 1) {
				usage = "Type '/tpa <" + playerName + ">' to accept or '/tpd <" + playerName + ">' to deny.";
			} else {
				usage = "Type '/tpa' to accept or '/tpd' to deny.";
			}
			methods.sendStyledMessage(target, null, "GREEN", "/", "GOLD", usage);
			
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
			getTeleportRequests(player).remove(targetName);
		}
		
		if (plugin.getServer().getPlayer(targetName) != null) {
			// Target is online
			
			// Create target from targetName
			Player target = plugin.getServer().getPlayer(targetName);
			
			// Send teleport deny message to target
			methods.sendStyledMessage(target, null, "RED", "/", "GOLD", "Teleport request denied by player '" + playerName + "'.");
			
			// Remove playerName from target teleport to
			plugin.players.get(target.getName()).setTeleportTo(null);
		}
	}
	
	public void teleportAccept(String playerName, String targetName) {
		
		if (plugin.getServer().getPlayer(playerName) != null) {
			// Player is online
			
			// Create player from playerName
			Player player = plugin.getServer().getPlayer(playerName);
			
			// Remove targetName from player teleport requests
			getTeleportRequests(player).remove(targetName);
		}
		
		if (plugin.getServer().getPlayer(targetName) != null) {
			// Target is online
			
			// Create target from targetName
			Player target = plugin.getServer().getPlayer(targetName);
			
			// Send teleport accept message to target
			methods.sendStyledMessage(target, null, "GREEN", "/", "GOLD", "Teleport request accepted by player '" + playerName + "'.");
			
			if (plugin.getServer().getPlayer(playerName) == null) {
				
				String server = bungeecordMethods.getPlayerServer(playerName);
				
				if (server != null) {
					// Target is on another server
					
					// Send teleport info to other server to get the player ready
					bungeecordMethods.sendTeleportInfo(target.getName(), playerName, server, "tp", "");
					
					// Send player to other server
					bungeecordMethods.sendPlayerToServer(target, server);
					
				} else {
					// Target is offline
					methods.sendStyledMessage(target, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
				}
			}
		}
	}
}
