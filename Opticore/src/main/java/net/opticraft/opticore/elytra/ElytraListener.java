package net.opticraft.opticore.elytra;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.opticraft.opticore.Main;

public class ElytraListener implements Listener {

	public Main plugin;

	public ElytraListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();

		String command = event.getMessage();

		if (command.equalsIgnoreCase("/clearinventory") || command.equalsIgnoreCase("/ci") || command.equalsIgnoreCase("/clean") || command.equalsIgnoreCase("/clearinvent")) {
			
			if (plugin.elytra.contains(player.getName())) {

				plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) plugin, new Runnable() {
					public void run() {
						ItemStack elytra = new ItemStack(Material.ELYTRA, 1);
						player.getInventory().setChestplate(elytra);
					}
				}, 1 * 20);
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {

		Player player = event.getPlayer();

		if (plugin.elytra.contains(player.getName())) {

			ItemStack elytra = new ItemStack(Material.ELYTRA, 1);
			player.getInventory().setChestplate(elytra);
		}
	}
}
