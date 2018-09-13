package net.opticraft.opticore.staff.mute;

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

public class MuteUtil {

	public Main plugin;

	public Util util;

	public MySQL mysql;

	public MuteUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.mysql = this.plugin.mysql;
	}
	
	@SuppressWarnings("deprecation")
	public void loadPlayerMutes(String player) {

		String uuid = plugin.getServer().getOfflinePlayer(player).getUniqueId().toString();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM oc_mute WHERE target_uuid = ? ORDER BY mute_timestamp DESC";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);

			resultSet = statement.executeQuery();

			ArrayList<Mute> mutes = new ArrayList<Mute>();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				String targetUUID = resultSet.getString(2);
				String targetName = resultSet.getString(3);
				String senderUUID = resultSet.getString(4);
				String senderName = resultSet.getString(5);
				long muteTimestamp = resultSet.getLong(6);
				long muteLength = resultSet.getLong(7);
				String muteReason = resultSet.getString(8);
				String unmuteUUID = resultSet.getString(9);
				String unmuteName = resultSet.getString(10);
				long unmuteTimestamp = resultSet.getLong(11);
				String unmuteReason = resultSet.getString(12);

				mutes.add(new Mute(id, targetUUID, targetName, senderUUID, senderName, muteTimestamp, muteLength, muteReason, unmuteUUID, unmuteName, unmuteTimestamp, unmuteReason));
			}

			plugin.mutes.put(uuid, mutes);

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
	public void mutePlayer(String target, String sender, long length, String reason) {

		long timestamp = System.currentTimeMillis() / 1000;

		if (plugin.getServer().getPlayer(target) != null) {
			
			Player targetPlayer = plugin.getServer().getPlayer(target);
			target = targetPlayer.getName();

			long unmuteTimestamp = timestamp + length;
			String unmuteDate = util.timestampDateFormat(unmuteTimestamp) + " (UTC)";
			
			if (length == 0) {
				unmuteDate = "forever";
			}
			
			targetPlayer.sendMessage("You have been muted by " + sender + " until " + unmuteDate + " for: " + reason);
			
			
		}

		String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();

		plugin.mysql.insert("oc_mute", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "mute_timestamp", "mute_length", "mute_reason"), 
				Arrays.asList(targetUUID, target, senderUUID, sender, timestamp, length, reason));
	}
	
	@SuppressWarnings("deprecation")
	public Mute getActiveMute(String player) {

		loadPlayerMutes(player);

		String uuid = plugin.getServer().getOfflinePlayer(player).getUniqueId().toString();

		for (Mute mute : plugin.mutes.get(uuid)) {
			
			if (mute.getUnmuteTimestamp() == 0) {
				
				long timestamp = System.currentTimeMillis() / 1000;

				long muteTimestamp = mute.getMuteTimestamp();
				long muteLength = mute.getMuteLength();
				long unmuteTimestamp = muteTimestamp + muteLength;

				if (muteLength == 0 || unmuteTimestamp >= timestamp) {
					return mute;
				}
			}
		}
		return null;
	}
}
