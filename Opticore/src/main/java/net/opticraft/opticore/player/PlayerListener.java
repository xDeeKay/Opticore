package net.opticraft.opticore.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class PlayerListener implements Listener {
	
	public Main plugin;

	public Config config;
	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;
	public MySQL mysql;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
		this.mysql = this.plugin.mysql;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		Player player = event.getPlayer();
		final String uuid = player.getUniqueId().toString();
		
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				try {
					if (!mysql.usersRowExist(uuid)) {
						mysql.createUsersRow(uuid);
					}
					
					mysql.loadUsersRow(uuid);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		event.setQuitMessage(null);

		Player player = event.getPlayer();
		final String uuid = player.getUniqueId().toString();
		
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				try {
					if (mysql.usersRowExist(uuid)) {
						mysql.saveUsersRow(uuid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
