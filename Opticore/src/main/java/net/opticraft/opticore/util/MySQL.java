package net.opticraft.opticore.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.player.PlayerInfo;

public class MySQL {

	public Main plugin;

	public Config config;

	public MySQL(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	//Open the MySQL connection
	public void openConnection() {

		plugin.log.info("[Opticore" + config.getServerName() + "] Opening MySQL connection...");

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

		createUsersTable();
		//createFriendsTable();
	}

	//Create the stats table
	public synchronized void createUsersTable() {

		Connection connection = null;

		String query = "CREATE TABLE IF NOT EXISTS oc_users (" 
				+ "uuid VARCHAR(36) PRIMARY KEY," 
				+ "setting_connect_disconnect INT(11)," 
				+ "setting_server_change INT(11)," 
				+ "setting_player_chat INT(11)," 
				+ "setting_server_announcement INT(11)," 
				+ "setting_friend_request INT(11)," 
				+ "setting_direct_message INT(11)," 
				+ "setting_teleport_request INT(11)," 
				+ "setting_spectate_request INT(11));";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			DatabaseMetaData metadata = connection.getMetaData();

			resultSet = metadata.getTables(null, null, "oc_users", null);
			if (!resultSet.next()) {
				statement = connection.prepareStatement(query);
				statement.executeUpdate();
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

	//Check if the player stats row exists
	public synchronized boolean usersRowExist(String uuid) {

		Connection connection = null;
		String query = "SELECT * FROM oc_users WHERE uuid=?;";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);

			resultSet = statement.executeQuery();
			boolean containsUUID = resultSet.next();

			return containsUUID;

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

	//Create the player stats row
	public void createUsersRow(String uuid) {

		Connection connection = null;
		String query = "INSERT INTO oc_users VALUES(?,1,1,1,1,1,1,1,1);";
		PreparedStatement statement = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);
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

	//Load the player stats row from the database to the server cache
	public void loadUsersRow(String uuid) {

		Connection connection = null;
		String query = "SELECT * FROM oc_users WHERE uuid=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);

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

				plugin.players.put(uuid, new PlayerInfo(
						settingConnectDisconnect, 
						settingServerChange, 
						settingPlayerChat, 
						settingServerAnnouncement, 
						settingFriendRequest, 
						settingDirectMessage, 
						settingTeleportRequest, 
						settingSpectateRequest));
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

	//Save the cached player stats row to the database
	public void saveUsersRow(String uuid) {

		Connection connection = null;
		String query = "UPDATE oc_users SET setting_connect_disconnect=?, setting_server_change=?, setting_player_chat=?, setting_server_announcement=?, setting_friend_request=?, setting_direct_message=?, setting_teleport_request=?, setting_spectate_request=? WHERE uuid=?";
		PreparedStatement statement = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);

			statement.setInt(1, plugin.players.get(uuid).getSettingsConnectDisconnect());
			statement.setInt(2, plugin.players.get(uuid).getSettingsServerChange());
			statement.setInt(3, plugin.players.get(uuid).getSettingsPlayerChat());
			statement.setInt(4, plugin.players.get(uuid).getSettingsServerAnnouncement());
			statement.setInt(5, plugin.players.get(uuid).getSettingsFriendRequest());
			statement.setInt(6, plugin.players.get(uuid).getSettingsDirectMessage());
			statement.setInt(7, plugin.players.get(uuid).getSettingsTeleportRequest());
			statement.setInt(8, plugin.players.get(uuid).getSettingsSpectateRequest());
			statement.setString(9, uuid);

			statement.executeUpdate();
			
			plugin.players.remove(uuid);

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

	//Create friends table
	public synchronized void createFriendsTable() {

		Connection connection = null;

		String query = "CREATE TABLE IF NOT EXISTS oc_friends (" 
				+ "user_id_1 INT NOT NULL," 
				+ "user_id_2 INT NOT NULL,"
				+ "status TINYINT NOT NULL);";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.ds.getConnection();

			DatabaseMetaData metadata = connection.getMetaData();

			resultSet = metadata.getTables(null, null, "oc_friends", null);
			if (!resultSet.next()) {
				statement = connection.prepareStatement(query);
				statement.executeUpdate();
				plugin.log.info("[OpticoreSurvival] 'oc_friends' table creation was successful.");
			}

		} catch (SQLException e) {
			plugin.log.warning("[OpticoreSurvival] 'oc_friends' table creation was unsuccessful.");
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

	// Add friend
	public void friendAdd(String senderUUID, String targetUUID) {

		Connection connection = null;
		String query = "INSERT INTO oc_friends VALUES(?,?,?);";
		PreparedStatement statement = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, senderUUID);
			statement.setString(2, targetUUID);
			statement.setInt(3, 0);
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

	// Accept friend
	public void friendAccept(String senderUUID, String targetUUID) {

		Connection connection = null;
		String query = "UPDATE oc_friends SET status=? WHERE user_id_1=? AND user_id_2=?";
		PreparedStatement statement = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setInt(1, 1);
			statement.setString(2, targetUUID);
			statement.setString(2, senderUUID);
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

	// Accept friend
	public void friendDecline(String senderUUID, String targetUUID) {

		Connection connection = null;
		String query = "UPDATE oc_friends SET status=? WHERE user_id_1=? AND user_id_2=?";
		PreparedStatement statement = null;

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setInt(1, 2);
			statement.setString(2, targetUUID);
			statement.setString(2, senderUUID);
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
