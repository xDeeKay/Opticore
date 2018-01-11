package net.opticraft.opticore.world;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.opticraft.opticore.Main;

public class WorldListener implements Listener {

	public Main plugin;

	public WorldUtil worldUtil;

	public WorldListener(Main plugin) {
		this.plugin = plugin;
		this.worldUtil = this.plugin.worldUtil;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		String world = player.getWorld().getName();
		world = worldUtil.resolveWorld(world);

		if (world != null && plugin.worlds.containsKey(world)) {

			if (plugin.worlds.get(world).getForced()) {

				worldUtil.teleportPlayerToWorld(player, world);
			}
		}
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

		Player player = event.getPlayer();

		String world = player.getWorld().getName();
		world = worldUtil.resolveWorld(world);

		if (world != null && plugin.worlds.containsKey(world)) {

			if (plugin.worlds.get(world).getForced()) {

				worldUtil.teleportPlayerToWorld(player, world);
			}
		}
	}
}
