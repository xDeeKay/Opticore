package net.opticraft.opticore.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.home.HomeMethods;
import net.opticraft.opticore.teleport.TeleportMethods;
import net.opticraft.opticore.util.MySQL;

public class PlayerListener implements Listener {

	public Main plugin;

	public MySQL mysql;

	public TeleportMethods teleportMethods;

	public HomeMethods homeMethods;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.teleportMethods = this.plugin.teleportMethods;
		this.homeMethods = this.plugin.homeMethods;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		final String uuid = player.getUniqueId().toString();

		plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				try {
					if (!mysql.usersRowExist(uuid)) {
						mysql.createUsersRow(uuid);
					}

					mysql.loadUsersRow(player);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		homeMethods.loadPlayerHomes(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		String playerName = player.getName();

		if (plugin.players.containsKey(playerName)) {

			if (!teleportMethods.getTeleportRequests(player).isEmpty()) {
				for (String targetName : teleportMethods.getTeleportRequests(player)) {
					teleportMethods.teleportDeny(playerName, targetName);
				}
			}

			plugin.players.remove(player.getName());
		}
	}
}
