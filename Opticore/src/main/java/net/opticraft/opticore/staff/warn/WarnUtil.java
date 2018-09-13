package net.opticraft.opticore.staff.warn;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.Util;

public class WarnUtil {

	public Main plugin;

	public Util util;

	public MySQL mysql;

	public WarnUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.mysql = this.plugin.mysql;
	}
	
	@SuppressWarnings("deprecation")
	public void warnPlayer(String target, String sender, String reason) {

		long timestamp = System.currentTimeMillis() / 1000;
		
		if (plugin.getServer().getPlayer(target) != null) {
			
			Player targetPlayer = plugin.getServer().getPlayer(target);
			target = targetPlayer.getName();
			
			targetPlayer.sendMessage("You have been warned by " + sender + " for: " + reason);
		}

		String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();

		plugin.mysql.insert("oc_warn", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "warn_timestamp", "warn_reason"), 
				Arrays.asList(targetUUID, target, senderUUID, sender, timestamp, reason));
	}
}
