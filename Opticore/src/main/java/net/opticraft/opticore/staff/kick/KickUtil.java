package net.opticraft.opticore.staff.kick;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.MySQL;

public class KickUtil {
	
	public Main plugin;
	
	public MySQL mysql;

	public KickUtil(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}
	
	@SuppressWarnings("deprecation")
	public void kickPlayer(Player target, String senderName, String kickReason) {
		
		target.kickPlayer("You have been kicked from the network by " + senderName + ".\nReason: " + kickReason);
		
		String targetUUID = target.getUniqueId().toString();
		String targetName = target.getName();
		
		String senderUUID = plugin.getServer().getOfflinePlayer(senderName).getUniqueId().toString();
		
		long timestamp = System.currentTimeMillis() / 1000;
		
		plugin.mysql.insert("oc_kick", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "kick_timestamp", "kick_reason"), 
				Arrays.asList(targetUUID, targetName, senderUUID, senderName, timestamp, kickReason));
	}
	
	public void kickAllPlayers(String senderName, String kickReason) {
		for (Player target : plugin.getServer().getOnlinePlayers()) {
			kickPlayer(target, senderName, kickReason);
		}
	}
}
