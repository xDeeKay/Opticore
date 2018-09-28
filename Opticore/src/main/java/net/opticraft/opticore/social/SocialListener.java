package net.opticraft.opticore.social;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.home.HomeUtil;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.util.MySQL;

public class SocialListener implements Listener {

	public Main plugin;

	public MySQL mysql;

	public TeleportUtil teleportUtil;

	public HomeUtil homeUtil;

	public SocialListener(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.teleportUtil = this.plugin.teleportUtil;
		this.homeUtil = this.plugin.homeUtil;
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
	}
}
