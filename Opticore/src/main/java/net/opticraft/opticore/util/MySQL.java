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
import net.opticraft.opticore.challenge.Challenge;
import net.opticraft.opticore.ignore.Ignore;
import net.opticraft.opticore.settings.Setting;

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
		plugin.ds.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
		plugin.ds.setUsername(user);
		plugin.ds.setPassword(password);

		/*
		plugin.ds = new HikariDataSource();
		plugin.ds.setMaximumPoolSize(10);
		plugin.ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		plugin.ds.addDataSourceProperty("serverName", host);
		plugin.ds.addDataSourceProperty("port", port);
		plugin.ds.addDataSourceProperty("databaseName", database);
		plugin.ds.addDataSourceProperty("user", user);
		plugin.ds.addDataSourceProperty("password", password);
		 */
	}

	public void createTables() {

		String settingsTable = "oc_settings";
		List<String> settingsRows = Arrays.asList("uuid VARCHAR(36) PRIMARY KEY", 
				"connect_disconnect INT(11)", 
				"server_change INT(11)", 
				"player_chat INT(11)", 
				"server_announcement INT(11)", 
				"friend_request INT(11)", 
				"direct_message INT(11)", 
				"direct_message_color INT(11)", 
				"teleport_request INT(11)", 
				"home_privacy INT(11)", 
				"reward_reminder INT(11)");
		createTable(settingsTable, settingsRows);

		String friendTable = "oc_friend";
		List<String> friendRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"friend_timestamp BIGINT(10)", 
				"friend_status INT(11)");
		createTable(friendTable, friendRows);

		String banTable = "oc_ban";
		List<String> banRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"ban_timestamp BIGINT(10)", 
				"ban_length BIGINT(10)", 
				"ban_reason VARCHAR(255)", 
				"unban_uuid VARCHAR(36)", 
				"unban_name VARCHAR(16)", 
				"unban_timestamp BIGINT(10)", 
				"unban_reason VARCHAR(255)");
		createTable(banTable, banRows);

		String freezeTable = "oc_freeze";
		List<String> freezeRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"freeze_timestamp BIGINT(10)", 
				"freeze_length BIGINT(10)", 
				"freeze_reason VARCHAR(255)", 
				"unfreeze_uuid VARCHAR(36)", 
				"unfreeze_name VARCHAR(16)", 
				"unfreeze_timestamp BIGINT(10)", 
				"unfreeze_reason VARCHAR(255)");
		createTable(freezeTable, freezeRows);

		String muteTable = "oc_mute";
		List<String> muteRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"mute_timestamp BIGINT(10)", 
				"mute_length BIGINT(10)", 
				"mute_reason VARCHAR(255)", 
				"unmute_uuid VARCHAR(36)", 
				"unmute_name VARCHAR(16)", 
				"unmute_timestamp BIGINT(10)", 
				"unmute_reason VARCHAR(255)");
		createTable(muteTable, muteRows);

		String kickTable = "oc_kick";
		List<String> kickRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"kick_timestamp BIGINT(10)", 
				"kick_reason VARCHAR(255)");
		createTable(kickTable, kickRows);

		String warnTable = "oc_warn";
		List<String> warnRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"warn_timestamp BIGINT(10)", 
				"warn_reason VARCHAR(255)");
		createTable(warnTable, warnRows);

		String noteTable = "oc_note";
		List<String> noteRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"note_timestamp BIGINT(10)", 
				"note_message VARCHAR(255)");
		createTable(noteTable, noteRows);

		String ticketTable = "oc_ticket";
		List<String> ticketRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"ticket_timestamp BIGINT(10)", 
				"ticket_message VARCHAR(255)", 
				"claim_uuid VARCHAR(36)", 
				"claim_name VARCHAR(16)", 
				"claim_timestamp BIGINT(10)", 
				"close_uuid VARCHAR(36)", 
				"close_name VARCHAR(16)", 
				"close_timestamp BIGINT(10)");
		createTable(ticketTable, ticketRows);

		String commandTable = "oc_command";
		List<String> commandRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"name VARCHAR(16)", 
				"server TEXT", 
				"world TEXT", 
				"location TEXT", 
				"timestamp BIGINT(10)", 
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
				"timestamp BIGINT(10)", 
				"server TEXT");
		createTable(loginTable, loginRows);

		String messageTable = "oc_message";
		List<String> messageRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"message_timestamp BIGINT(10)", 
				"message_message VARCHAR(255)");
		createTable(messageTable, messageRows);

		String socialTable = "oc_social";
		List<String> socialRows = Arrays.asList("uuid VARCHAR(36) PRIMARY KEY", 
				"youtube VARCHAR(36)", 
				"twitch VARCHAR(36)", 
				"twitter VARCHAR(36)", 
				"instagram VARCHAR(36)", 
				"discord VARCHAR(36)");
		createTable(socialTable, socialRows);

		String rewardsTable = "oc_rewards";
		List<String> rewardsRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"reward TEXT", 
				"cost BIGINT(10)", 
				"timestamp BIGINT(10)", 
				"server TEXT");
		createTable(rewardsTable, rewardsRows);

		String pointsTable = "oc_points";
		List<String> pointsRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"points BIGINT(10)", 
				"last_daily BIGINT(10)", 
				"last_vote BIGINT(10)");
		createTable(pointsTable, pointsRows);

		String votesTable = "oc_votes";
		List<String> votesRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"username VARCHAR(16)", 
				"ip TEXT", 
				"timestamp BIGINT(10)", 
				"service TEXT");
		createTable(votesTable, votesRows);

		String ignoreTable = "oc_ignore";
		List<String> ignoreRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"target_uuid VARCHAR(36)", 
				"target_name VARCHAR(16)", 
				"sender_uuid VARCHAR(36)", 
				"sender_name VARCHAR(16)", 
				"ignore_timestamp BIGINT(10)");
		createTable(ignoreTable, ignoreRows);

		String challengesTable = "oc_challenges";
		List<String> challengesRows = Arrays.asList("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY", 
				"uuid VARCHAR(36)", 
				"task TEXT", 
				"target TEXT", 
				"amount BIGINT(10)", 
				"reward BIGINT(10)", 
				"timestamp BIGINT(10)", 
				"server TEXT");
		createTable(challengesTable, challengesRows);
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

	public void insert(String table, List<Object> rows, List<Object> values) {

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

	public void update(String table, List<Object> rows, List<Object> values, Object whereRow, Object whereValue) {

		Connection connection = null;

		PreparedStatement statement = null;

		String queryRows = StringUtils.join(rows, " = ?, ") + " = ?";
		String query = "UPDATE " + table + " SET " + queryRows + " WHERE " + whereRow + " = ?";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			int i = 1;
			for (Object value : values) {
				statement.setObject(i, value);
				i++;
			}
			statement.setObject(i, whereValue);
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

	public void delete(String table, List<Object> whereRows, List<Object> whereValues) {

		Connection connection = null;

		PreparedStatement statement = null;

		String queryWhereRows = StringUtils.join(whereRows, " = ? AND ");
		String query = "DELETE FROM " + table + " WHERE " + queryWhereRows;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			int i = 1;
			for (Object whereRow : whereRows) {
				statement.setObject(i, whereRow);
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

		String query = "SELECT * FROM " + table + " WHERE " + row + " = ?;";

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

	@SuppressWarnings("deprecation")
	public int getUUIDColumnValue(String player, String table, String column) {

		Connection connection = null;
		String query = "SELECT " + column + " FROM " + table + " WHERE uuid=?";
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

	// Load the settings from the database to the player object
	public void loadSettingsTable(Player player) {

		Connection connection = null;
		String query = "SELECT * FROM oc_settings WHERE uuid = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, player.getUniqueId().toString());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int connectDisconnect = resultSet.getInt(2);
				int serverChange = resultSet.getInt(3);
				int playerChat = resultSet.getInt(4);
				int serverAnnouncement = resultSet.getInt(5);
				int friendRequest = resultSet.getInt(6);
				int directMessage = resultSet.getInt(7);
				int directMessageColor = resultSet.getInt(8);
				int teleportRequest = resultSet.getInt(9);
				int homePrivacy = resultSet.getInt(10);
				int rewardReminder = resultSet.getInt(11);

				if (!plugin.players.containsKey(player.getName())) {
					plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
				}

				plugin.players.get(player.getName()).getSettings().put("connect_disconnect", new Setting(connectDisconnect, 1));
				plugin.players.get(player.getName()).getSettings().put("server_change", new Setting(serverChange, 1));
				plugin.players.get(player.getName()).getSettings().put("player_chat", new Setting(playerChat, 1));
				plugin.players.get(player.getName()).getSettings().put("server_announcement", new Setting(serverAnnouncement, 1));
				plugin.players.get(player.getName()).getSettings().put("friend_request", new Setting(friendRequest, 1));
				plugin.players.get(player.getName()).getSettings().put("direct_message", new Setting(directMessage, 2));
				plugin.players.get(player.getName()).getSettings().put("direct_message_color", new Setting(directMessageColor, 15));
				plugin.players.get(player.getName()).getSettings().put("teleport_request", new Setting(teleportRequest, 2));
				plugin.players.get(player.getName()).getSettings().put("home_privacy", new Setting(homePrivacy, 2));
				plugin.players.get(player.getName()).getSettings().put("reward_reminder", new Setting(rewardReminder, 1));
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

	// Load the settings from the database to the player class
	public void loadPointsTable(Player player) {

		Connection connection = null;
		String query = "SELECT * FROM oc_points WHERE uuid = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, player.getUniqueId().toString());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int points = resultSet.getInt(3);
				long lastDaily = resultSet.getLong(4);
				long lastVote = resultSet.getLong(5);

				if (!plugin.players.containsKey(player.getName())) {
					plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
				}

				plugin.players.get(player.getName()).setPoints(points);
				plugin.players.get(player.getName()).setLastDaily(lastDaily);
				plugin.players.get(player.getName()).setLastVote(lastVote);
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

	public void loadIgnoreTable(Player player) {

		Connection connection = null;
		String query = "SELECT * FROM oc_ignore WHERE sender_uuid = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, player.getUniqueId().toString());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				String targetUUID = resultSet.getString(2);
				String targetName = resultSet.getString(3);
				long ignoreTimestamp = resultSet.getLong(6);

				if (!plugin.players.containsKey(player.getName())) {
					plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
				}

				plugin.players.get(player.getName()).getIgnored().put(targetUUID, new Ignore(targetName, ignoreTimestamp));
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

	public void loadChallengesCompleted(Player player) {

		Connection connection = null;
		String query = "SELECT * FROM oc_challenges WHERE uuid = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, player.getUniqueId().toString());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt(1);
				String task = resultSet.getString(3);
				String target = resultSet.getString(4);
				int amount = resultSet.getInt(5);
				int reward = resultSet.getInt(6);
				long timestamp = resultSet.getLong(7);

				if (!plugin.players.containsKey(player.getName())) {
					plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
				}

				plugin.players.get(player.getName()).getChallengesCompleted().put(String.valueOf(id), new Challenge(String.valueOf(id), timestamp, false, task, target, amount, reward, null));
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
}
