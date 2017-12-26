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
					if (!mysql.tableRowContainsUUID("oc_users", "uuid", uuid)) {
						mysql.insertValuesIntoTable("oc_users", Arrays.asList("uuid"), Arrays.asList(uuid));
					}

					mysql.loadUsersRow(player);

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

			if (!teleportUtil.getTeleportRequests(player).isEmpty()) {
				for (String targetName : teleportUtil.getTeleportRequests(player)) {
					teleportUtil.teleportDeny(playerName, targetName);
				}
			}

			plugin.players.remove(player.getName());
		}
	}
}
