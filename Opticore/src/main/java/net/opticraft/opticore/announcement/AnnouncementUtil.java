package net.opticraft.opticore.announcement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;

public class AnnouncementUtil {

	public Main plugin;

	public List<String> messages = new ArrayList<String>();
	public int interval;

	public BukkitTask task;
	
	public AnnouncementUtil(Main plugin) {
		this.plugin = plugin;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("announcement")) {

			List<String> announcementMessages = plugin.getConfig().getStringList("announcement.messages");

			if (!announcementMessages.isEmpty()) {
				this.setMessages(plugin.getConfig().getStringList("announcement.messages"));
				this.setInterval(plugin.getConfig().getInt("announcement.interval"));
			}
		}
	}
	
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return this.messages;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return this.interval;
	}

	public void startAnnouncement() {
		this.task = new BukkitRunnable() {
			public void run() {
				announceMessage();
			}
		}.runTaskTimer(plugin, 20 * getInterval(), 20 * getInterval());
	}

	public void stopAnnouncement() {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}

	public void announceMessage() {

		String message = messages.get(new Random().nextInt(messages.size()));

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (plugin.players.get(player.getName()).getSettings().get("server_announcement").getValue() == 1) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
		}
	}
}
