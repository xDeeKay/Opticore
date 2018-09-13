package net.opticraft.opticore.staff.ban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.Util;

public class BanUtil {

	public Main plugin;

	public Util util;

	public MySQL mysql;

	public BanUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.mysql = this.plugin.mysql;
	}

	@SuppressWarnings("deprecation")
	public void loadPlayerBans(String player) {

		String uuid = plugin.getServer().getOfflinePlayer(player).getUniqueId().toString();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM oc_ban WHERE target_uuid = ? ORDER BY ban_timestamp DESC";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);

			resultSet = statement.executeQuery();

			ArrayList<Ban> bans = new ArrayList<Ban>();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				String targetUUID = resultSet.getString(2);
				String targetName = resultSet.getString(3);
				String senderUUID = resultSet.getString(4);
				String senderName = resultSet.getString(5);
				long banTimestamp = resultSet.getLong(6);
				long banLength = resultSet.getLong(7);
				String banReason = resultSet.getString(8);
				String unbanUUID = resultSet.getString(9);
				String unbanName = resultSet.getString(10);
				long unbanTimestamp = resultSet.getLong(11);
				String unbanReason = resultSet.getString(12);

				bans.add(new Ban(id, targetUUID, targetName, senderUUID, senderName, banTimestamp, banLength, banReason, unbanUUID, unbanName, unbanTimestamp, unbanReason));
			}

			plugin.bans.put(uuid, bans);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public Ban getActiveBan(String player) {

		loadPlayerBans(player);

		String uuid = plugin.getServer().getOfflinePlayer(player).getUniqueId().toString();

		for (Ban ban : plugin.bans.get(uuid)) {
			
			if (ban.getUnbanTimestamp() == 0) {
				
				long timestamp = System.currentTimeMillis() / 1000;

				long banTimestamp = ban.getBanTimestamp();
				long banLength = ban.getBanLength();
				long unbanTimestamp = banTimestamp + banLength;

				if (banLength == 0 || unbanTimestamp >= timestamp) {
					return ban;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void banPlayer(String target, String sender, long length, String reason) {

		long timestamp = System.currentTimeMillis() / 1000;

		if (plugin.getServer().getPlayer(target) != null) {
			Player targetPlayer = plugin.getServer().getPlayer(target);
			target = targetPlayer.getName();

			long unbanTimestamp = timestamp + length;
			String unbanDate = util.timestampDateFormat(unbanTimestamp) + " (UTC)";
			
			if (length == 0) {
				unbanDate = "forever";
			}
			
			targetPlayer.kickPlayer("You have been banned from the network by " + sender + " until " + unbanDate + "\nReason: " + reason + "\nAppeal at www.opticraft.net");
		}

		String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();

		plugin.mysql.insert("oc_ban", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "ban_timestamp", "ban_length", "ban_reason"), 
				Arrays.asList(targetUUID, target, senderUUID, sender, timestamp, length, reason));
	}
	
	@SuppressWarnings("deprecation")
	public void unbanPlayer(int id, String sender, String reason) {
		
		long timestamp = System.currentTimeMillis() / 1000;
		
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();

		plugin.mysql.update("oc_ban", 
				Arrays.asList("unban_uuid", "unban_name", "unban_timestamp", "unban_reason"), 
				Arrays.asList(senderUUID, sender, timestamp, reason), 
				"id", id);
		
	}

	public void banAllPlayers(String sender, long length, String reason) {
		for (Player target : plugin.getServer().getOnlinePlayers()) {
			banPlayer(target.getName(), sender, length, reason);
		}
	}
}
