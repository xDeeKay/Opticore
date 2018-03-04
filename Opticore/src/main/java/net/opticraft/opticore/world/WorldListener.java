package net.opticraft.opticore.world;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.NullExtent;
import com.sk89q.worldedit.util.eventbus.Subscribe;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class WorldListener implements Listener {

	public Main plugin;

	public WorldUtil worldUtil;

	public Util util;

	public WorldListener(Main plugin) {
		this.plugin = plugin;
		this.worldUtil = this.plugin.worldUtil;
		this.util = this.plugin.util;
	}

	@Subscribe
	public void onEditSession(EditSessionEvent event) {
		
		System.out.print("some worldedit happened");

		Actor actor = event.getActor();

		if (actor != null && actor.isPlayer()) {

			Player player = (Player) actor;

			String world = plugin.players.get(player.getName()).getWorld();

			if (!worldUtil.isOwner(player, world) && !worldUtil.isMember(player, world)) {

				event.setExtent(new NullExtent());

				util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to build in the world '" + world + "'.");
			}
		}
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

		plugin.players.get(player.getName()).setWorld(world);

		if (world != null && plugin.worlds.containsKey(world)) {

			if (plugin.worlds.get(world).getForced()) {

				if (plugin.players.get(player.getName()).getTprOutgoing() == null) {

					worldUtil.teleportPlayerToWorld(player, world);
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

		Player player = event.getPlayer();

		String world = plugin.players.get(player.getName()).getWorld();

		if (world != null && worldUtil.worldExists(world)) {
			if (!worldUtil.isOwner(player, world) && !worldUtil.isMember(player, world)) {
				event.setCancelled(true);
				util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to build in the world '" + world + "'.");
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();

		String world = plugin.players.get(player.getName()).getWorld();

		if (world != null && worldUtil.worldExists(world)) {
			if (!worldUtil.isOwner(player, world) && !worldUtil.isMember(player, world)) {
				event.setCancelled(true);
				util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to build in the world '" + world + "'.");
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		Action action = event.getAction();

		String world = plugin.players.get(player.getName()).getWorld();

		if (world != null && worldUtil.worldExists(world)) {
			if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR) {
				if (!worldUtil.isOwner(player, world) && !worldUtil.isMember(player, world) && !worldUtil.isGuest(player, world)) {
					event.setCancelled(true);
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to interact in the world '" + world + "'.");
				}
			}
		}
	}
}
