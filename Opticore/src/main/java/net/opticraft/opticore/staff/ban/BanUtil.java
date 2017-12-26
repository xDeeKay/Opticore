package net.opticraft.opticore.staff.ban;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.MySQL;

public class BanUtil {
	
	public Main plugin;
	
	public MySQL mysql;

	public BanUtil(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}
	
	@SuppressWarnings("deprecation")
	public void banPlayer(Player target, String senderName, long banLength, String banReason) {
		
		target.kickPlayer("You have been banned from the server.\n" + banReason);
		
		String targetUUID = target.getUniqueId().toString();
		String targetName = target.getName();
		
		String senderUUID = plugin.getServer().getOfflinePlayer(senderName).getUniqueId().toString();
		
		long timestamp = System.currentTimeMillis();
		
		plugin.mysql.insertValuesIntoTable("oc_ban", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "ban_datetime", "ban_length", "ban_reason"), 
				Arrays.asList(targetUUID, targetName, senderUUID, senderName, timestamp, banLength, banReason));
	}
	
	public void banAllPlayers(String senderName, long banLength, String banReason) {
		for (Player target : plugin.getServer().getOnlinePlayers()) {
			banPlayer(target, senderName, banLength, banReason);
		}
	}
}
