package net.opticraft.opticore.player;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.home.HomeUtil;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.util.MySQL;

public class PlayerListener implements Listener {

	public Main plugin;

	public MySQL mysql;

	public TeleportUtil teleportUtil;

	public HomeUtil homeUtil;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.teleportUtil = this.plugin.teleportUtil;
		this.homeUtil = this.plugin.homeUtil;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		final String uuid = player.getUniqueId().toString();

		plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				try {
					if (!mysql.tableRowContainsUUID("oc_settings", "uuid", uuid)) {
						mysql.insert("oc_settings", 
								Arrays.asList("uuid", "connect_disconnect", "server_change", "player_chat", "server_announcement", "friend_request", "direct_message", "direct_message_color", "teleport_request", "home_privacy", "reward_reminder"), 
								Arrays.asList(uuid, 1, 1, 1, 1, 1, 1, 15, 1, 1, 1));
					}

					if (!mysql.tableRowContainsUUID("oc_social", "uuid", uuid)) {
						mysql.insert("oc_social", 
								Arrays.asList("uuid", "	youtube", "twitch", "twitter", "instagram", "discord"), 
								Arrays.asList(uuid, null, null, null, null, null));
					}

					if (!mysql.tableRowContainsUUID("oc_points", "uuid", uuid)) {
						mysql.insert("oc_points", 
								Arrays.asList("uuid", "points", "last_daily", "last_vote"), 
								Arrays.asList(uuid, plugin.config.getPointsJoin(), null, null));
					}

					mysql.loadSettingsTable(player);

					//mysql.loadSocialTable(player);

					mysql.loadPointsTable(player);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		homeUtil.loadPlayerHomes(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		String playerName = player.getName();

		if (plugin.players.containsKey(playerName)) {

			if (!plugin.players.get(playerName).getTprIncoming().isEmpty()) {
				for (String targetName : plugin.players.get(playerName).getTprIncoming()) {
					teleportUtil.teleportDeny(playerName, targetName);
				}
			}
			
			if (!plugin.players.get(playerName).getTprhereIncoming().isEmpty()) {
				for (String targetName : plugin.players.get(playerName).getTprhereIncoming()) {
					teleportUtil.teleportDeny(playerName, targetName);
				}
			}
			
			if (plugin.players.get(playerName).getTprOutgoing() != null) {
				String targetName = plugin.players.get(playerName).getTprOutgoing();
				teleportUtil.teleportDeny(targetName, playerName);
			}
			
			if (plugin.players.get(playerName).getTprhereOutgoing() != null) {
				String targetName = plugin.players.get(playerName).getTprhereOutgoing();
				teleportUtil.teleportDeny(targetName, playerName);
			}

			plugin.players.remove(player.getName());
		}
		
		String playerServerName = plugin.serverUtil.getPlayerServerName(playerName);
		String serverName = plugin.config.getServerName();
		
		if (playerServerName != null && playerServerName.toLowerCase().equals(serverName.toLowerCase())) {
			plugin.servers.get(playerServerName).getPlayers().remove(playerName);
		}
	}
}
