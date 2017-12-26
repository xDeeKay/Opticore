package net.opticraft.opticore.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import com.zaxxer.hikari.HikariDataSource;

import net.opticraft.opticore.Main;

public class MySQL {

	public Main plugin;

	public Config config;

	public Util util;

	public MySQL(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	//Open the MySQL connection
	public void openConnection() {

		util.log(Level.INFO, "Opening MySQL connection...");

		String host = config.getMysqlHost();
		String port = config.getMysqlPort();
		String database = config.getMysqlDatabase();
		String user = config.getMysqlUser();
		String password = config.getMysqlPassword();

		plugin.ds = new HikariDataSource();
		plugin.ds.setMaximumPoolSize(10);
		plugin.ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		plugin.ds.addDataSourceProperty("serverName", host);
		plugin.ds.addDataSourceProperty("port", port);
		plugin.ds.addDataSourceProperty("databaseName", database);
		plugin.ds.addDataSourceProperty("user", user);
		plugin.ds.addDataSourceProperty("password", password);
	}

	public void createTables() {

		String usersTable = "oc_users";
		List<String> usersRows = Arrays.asList("uuid VARCHAR(36) PRIMARY KEY", 
				"setting_connect_disconnect INT(11)", 
				"setting_server_change INT(11)", 
				"setting_player_chat INT(11)", 
				"setting_server_announcement INT(11)", 
				"setting_friend_request INT(11)", 
				"setting_direct_message INT(11)", 
				"setting_direct_message_color INT(11)", 
				"setting_teleport_request INT(11)", 
				"setting_spectate_request INT(11)", 
				"homes_remaining INT(11)");
		createTable(usersTable, usersRows);

		String friendTable = "oc_friend";
		List<String> friendRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"friend_timestamp BIGINT(13)", 
				"friend_status INT(11)");
		createTable(friendTable, friendRows);

		String banTable = "oc_ban";
		List<String> banRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"ban_timestamp BIGINT(13)", 
				"ban_length BIGINT(13)", 
				"ban_reason VARCHAR(255)", 
				"unban_uuid VARCHAR(36)", 
				"unban_name VARCHAR(16)", 
				"unban_timestamp BIGINT(13)", 
				"unban_reason VARCHAR(255)");
		createTable(banTable, banRows);

		String freezeTable = "oc_freeze";
		List<String> freezeRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"freeze_timestamp BIGINT(13)", 
				"freeze_length BIGINT(13)", 
				"freeze_reason VARCHAR(255)", 
				"unfreeze_uuid VARCHAR(36)", 
				"unfreeze_name VARCHAR(16)", 
				"unfreeze_timestamp BIGINT(13)", 
				"unfreeze_reason VARCHAR(255)");
		createTable(freezeTable, freezeRows);

		String muteTable = "oc_mute";
		List<String> muteRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"mute_timestamp BIGINT(13)", 
				"mute_length BIGINT(13)", 
				"mute_reason VARCHAR(255)", 
				"unmute_uuid VARCHAR(36)", 
				"unmute_name VARCHAR(16)", 
				"unmute_timestamp BIGINT(13)", 
				"unmute_reason VARCHAR(255)");
		createTable(muteTable, muteRows);

		String kickTable = "oc_kick";
		List<String> kickRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"kick_timestamp BIGINT(13)", 
				"kick_reason VARCHAR(255)");
		createTable(kickTable, kickRows);

		String noteTable = "oc_note";
		List<String> noteRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"note_timestamp BIGINT(13)", 
				"note_message VARCHAR(255)");
		createTable(noteTable, noteRows);

		String ticketTable = "oc_ticket";
		List<String> ticketRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"ticket_timestamp BIGINT(13)", 
				"ticket_message VARCHAR(255)", 
				"claim_uuid VARCHAR(36)", 
				"claim_name VARCHAR(16)", 
				"claim_timestamp BIGINT(13)", 
				"close_uuid VARCHAR(36)", 
				"close_name VARCHAR(16)", 
				"close_timestamp BIGINT(13)");
		createTable(ticketTable, ticketRows);

		String commandTable = "oc_command";
		List<String> commandRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"name VARCHAR(16)", 
				"server TEXT", 
				"world TEXT", 
				"location TEXT", 
				"timestamp BIGINT(13)", 
				"command TEXT");
		createTable(commandTable, commandRows);

		String loginTable = "oc_login";
		List<String> loginRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"name VARCHAR(16)", 
				"ip TEXT", 
				"country TEXT", 
				"region TEXT", 
				"city TEXT", 
				"timestamp BIGINT(13)", 
				"server TEXT");
		createTable(loginTable, loginRows);
	}

	public synchronized void createTable(String table, List<String> rows) {

		Connection connection = null;

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		String queryRows = StringUtils.join(rows, ",");
		String query = "CREATE TABLE IF NOT EXISTS " + table + " (" + queryRows + ");";

		try {
			connection = plugin.ds.getConnection();

			DatabaseMetaData metadata = connection.getMetaData();
			resultSet = metadata.getTables(null, null, table, null);

			if (!resultSet.next()) {
				statement = connection.prepareStatement(query);
				statement.executeUpdate();
				util.log(Level.INFO, "'" + table + "' table creation was successful.");
			}

		} catch (SQLException e) {
			util.log(Level.WARNING, "'" + table + "' table creation was unsuccessful.");
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

	public void insertValuesIntoTable(String table, List<Object> rows, List<Object> values) {

		Connection connection = null;

		PreparedStatement statement = null;

		String queryRows = StringUtils.join(rows, ",");
		String queryValues = StringUtils.join(Collections.nCopies(values.size(), "?").toArray(new String[values.size()]), ",");
		String query = "INSERT INTO " + table + " (" + queryRows + ") VALUES(" + queryValues + ");";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			int i = 1;
			for (Object value : values) {
				statement.setObject(i, value);
				i++;
			}
			statement.executeUpdate();

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
		}
	}

	// Check if a table row contains a uuid
	public synchronized boolean tableRowContainsUUID(String table, String row, String uuid) {

		Connection connection = null;

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		String query = "SELECT * FROM " + table + " WHERE " + row + "=?;";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);

			resultSet = statement.executeQuery();

			return resultSet.next();

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
		return false;
	}

	// Load the user data from the database to the player object
	public void loadUsersRow(Player player) {

		Connection connection = null;
		String query = "SELECT * FROM oc_users WHERE uuid=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, player.getUniqueId().toString());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int settingConnectDisconnect = resultSet.getInt(2);
				int settingServerChange = resultSet.getInt(3);
				int settingPlayerChat = resultSet.getInt(4);
				int settingServerAnnouncement = resultSet.getInt(5);
				int settingFriendRequest = resultSet.getInt(6);
				int settingDirectMessage = resultSet.getInt(7);
				int settingTeleportRequest = resultSet.getInt(8);
				int settingSpectateRequest = resultSet.getInt(9);
				int homesRemaining = resultSet.getInt(10);

				if (!plugin.players.containsKey(player.getName())) {
					plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
				}

				plugin.players.get(player.getName()).setSettingsConnectDisconnect(settingConnectDisconnect);
				plugin.players.get(player.getName()).setSettingsServerChange(settingServerChange);
				plugin.players.get(player.getName()).setSettingsPlayerChat(settingPlayerChat);
				plugin.players.get(player.getName()).setSettingsServerAnnouncement(settingServerAnnouncement);
				plugin.players.get(player.getName()).setSettingsFriendRequest(settingFriendRequest);
				plugin.players.get(player.getName()).setSettingsDirectMessage(settingDirectMessage);
				plugin.players.get(player.getName()).setSettingsTeleportRequest(settingTeleportRequest);
				plugin.players.get(player.getName()).setSettingsSpectateRequest(settingSpectateRequest);
				plugin.players.get(player.getName()).setHomesRemaining(homesRemaining);
			}

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
	public int getUsersColumnValue(String player, String column) {

		Connection connection = null;
		String query = "SELECT " + column + " FROM oc_users WHERE uuid=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		int value = 0;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, plugin.getServer().getOfflinePlayer(player).getUniqueId().toString());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				value = resultSet.getInt(1);
			}

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
		return value;
	}

	@SuppressWarnings("deprecation")
	public void setUsersColumnValue(String player, String column, int value) {

		Connection connection = null;
		String query = "UPDATE oc_users SET " + column + "=? WHERE uuid=?";
		PreparedStatement statement = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);

			statement.setInt(1, value);
			statement.setString(2, plugin.getServer().getOfflinePlayer(player).getUniqueId().toString());

			statement.executeUpdate();

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
		}
	}

	public void logNote(String targetUUID, String targetName, String senderUUID, String senderName, String noteTimestamp, String noteMessage) {

		Connection connection = null;
		PreparedStatement statement = null;

		String query = "INSERT INTO oc_note(target_uuid, target_name, sender_uuid, sender_name, note_timestamp, note_message) VALUES(?,?,?,?,?,?)";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);



			statement.setString(1, targetUUID);
			statement.setString(2, targetName);
			statement.setString(3, senderUUID);
			statement.setString(4, senderName);
			statement.setString(5, noteTimestamp);
			statement.setString(6, noteMessage);
			statement.executeUpdate();

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
		}
	}

	/*
	public boolean tableExists(Connection con, String table) throws SQLException {
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet tables = dbm.getTables(null, null, table, null);
		return tables.next();
	}

	public boolean columnExists(Connection con, String table, String column) throws SQLException {
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet columns = dbm.getColumns(null, null, table, column);
		return columns.next();
	}
	 */
}
