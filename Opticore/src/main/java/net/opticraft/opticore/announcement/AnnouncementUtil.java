package net.opticraft.opticore.announcement;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;

public class AnnouncementUtil {

	public Main plugin;
	
	public Config config;

	public BukkitTask task;
	
	public AnnouncementUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public void startAnnouncement() {
		this.task = new BukkitRunnable() {
			public void run() {
				announceMessage();
			}
		}.runTaskTimer(plugin, 20 * config.getAnnouncementInterval(), 20 * config.getAnnouncementInterval());
	}

	public void stopAnnouncement() {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}

	public void announceMessage() {

		String message = config.getAnnouncementMessages().get(new Random().nextInt(config.getAnnouncementMessages().size()));

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (plugin.players.get(player.getName()).getSettings().get("server_announcement").getValue() == 1) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
		}
	}
}
