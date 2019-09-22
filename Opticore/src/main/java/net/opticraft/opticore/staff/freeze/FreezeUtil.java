package net.opticraft.opticore.staff.freeze;

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

public class FreezeUtil {

	public Main plugin;

	public Util util;

	public MySQL mysql;

	public FreezeUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.mysql = this.plugin.mysql;
	}
	
	@SuppressWarnings("deprecation")
	public void loadPlayerFreezes(String player) {

		String uuid = plugin.getServer().getOfflinePlayer(player).getUniqueId().toString();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM oc_freeze WHERE target_uuid = ? ORDER BY freeze_timestamp DESC";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);

			resultSet = statement.executeQuery();

			ArrayList<Freeze> freezes = new ArrayList<Freeze>();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				String targetUUID = resultSet.getString(2);
				String targetName = resultSet.getString(3);
				String senderUUID = resultSet.getString(4);
				String senderName = resultSet.getString(5);
				long freezeTimestamp = resultSet.getLong(6);
				long freezeLength = resultSet.getLong(7);
				String freezeReason = resultSet.getString(8);
				String unfreezeUUID = resultSet.getString(9);
				String unfreezeName = resultSet.getString(10);
				long unfreezeTimestamp = resultSet.getLong(11);
				String unfreezeReason = resultSet.getString(12);

				freezes.add(new Freeze(id, targetUUID, targetName, senderUUID, senderName, freezeTimestamp, freezeLength, freezeReason, unfreezeUUID, unfreezeName, unfreezeTimestamp, unfreezeReason));
			}

			plugin.freezes.put(uuid, freezes);

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
	public Freeze getActiveFreeze(String player) {

		loadPlayerFreezes(player);

		String uuid = plugin.getServer().getOfflinePlayer(player).getUniqueId().toString();

		for (Freeze freeze : plugin.freezes.get(uuid)) {
			
			if (freeze.getUnfreezeTimestamp() == 0) {
				
				long timestamp = System.currentTimeMillis() / 1000;

				long freezeTimestamp = freeze.getFreezeTimestamp();
				long freezeLength = freeze.getFreezeLength();
				long expireTimestamp = freezeTimestamp + freezeLength;

				if (freezeLength == 0 || expireTimestamp >= timestamp) {
					return freeze;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void freezePlayer(String target, String sender, long length, String reason) {

		long timestamp = System.currentTimeMillis() / 1000;

		if (plugin.getServer().getPlayer(target) != null) {
			
			Player targetPlayer = plugin.getServer().getPlayer(target);
			target = targetPlayer.getName();

			long expireTimestamp = timestamp + length;
			String expireDate = util.timestampDateFormat(expireTimestamp) + " (UTC)";
			
			if (length == 0) {
				expireDate = "forever";
			}
			
			util.sendStyledMessage(targetPlayer, null, "RED", "!", "GOLD", "You have been frozen by " + sender + " until " + expireDate + " for: " + reason);
		}

		String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();

		plugin.mysql.insert("oc_freeze", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "freeze_timestamp", "freeze_length", "freeze_reason"), 
				Arrays.asList(targetUUID, target, senderUUID, sender, timestamp, length, reason));
	}
	
	@SuppressWarnings("deprecation")
	public void unfreezePlayer(int id, String sender, String reason) {
		
		long timestamp = System.currentTimeMillis() / 1000;
		
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();

		plugin.mysql.update("oc_freeze", 
				Arrays.asList("unfreeze_uuid", "unfreeze_name", "unfreeze_timestamp", "unfreeze_reason"), 
				Arrays.asList(senderUUID, sender, timestamp, reason), 
				"id", id);
		
	}

	public void freezeAllOnlinePlayers(String sender, long length, String reason) {
		for (Player target : plugin.getServer().getOnlinePlayers()) {
			freezePlayer(target.getName(), sender, length, reason);
		}
	}
}
