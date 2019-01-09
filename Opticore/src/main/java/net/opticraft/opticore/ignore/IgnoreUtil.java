package net.opticraft.opticore.ignore;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.MySQL;

public class IgnoreUtil {

	public Main plugin;

	public MySQL mysql;

	public IgnoreUtil(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}

	public boolean isIgnored(Player player, String target) {



		return false;

	}

	@SuppressWarnings("deprecation")
	public void ignore(Player player, String target) {
		
		long timestamp = System.currentTimeMillis() / 1000;
		
		String playerUUID = player.getUniqueId().toString();
		String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
		mysql.insert("oc_ignore", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "ignore_timestamp"), 
				Arrays.asList(targetUUID, target, playerUUID, player.getName(), timestamp));

	}

	@SuppressWarnings("deprecation")
	public void unignore(Player player, String target) {
		
		String playerUUID = player.getUniqueId().toString();
		String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

		mysql.delete("oc_ignore", 
				Arrays.asList("target_uuid", "sender_uuid"), 
				Arrays.asList(targetUUID, playerUUID));
	}
}
