package net.opticraft.opticore.staff.freeze;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.opticraft.opticore.Main;

public class FreezeListener implements Listener {

	public Main plugin;

	public FreezeListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freezes.containsKey(playerName)) {
			player.teleport(player);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freezes.containsKey(playerName)) {
			player.sendMessage(ChatColor.RED + "You are frozen and cannot place blocks.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freezes.containsKey(playerName)) {
			player.sendMessage(ChatColor.RED + "You are frozen and cannot destroy blocks.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freezes.containsKey(playerName)) {
			player.sendMessage(ChatColor.RED + "You are frozen and cannot run commands.");
			event.setCancelled(true);
		}
	}
}
