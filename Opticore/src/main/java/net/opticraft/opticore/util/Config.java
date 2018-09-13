package net.opticraft.opticore.util;

import java.util.List;

import net.opticraft.opticore.Main;

public class Config {

	public Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
	}

	public void loadConfig() {

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();

		// MySQL
		this.setMysqlUser(plugin.getConfig().getString("mysql.user"));
		this.setMysqlPort(plugin.getConfig().getString("mysql.port"));
		this.setMysqlPassword(plugin.getConfig().getString("mysql.password"));
		this.setMysqlHost(plugin.getConfig().getString("mysql.host"));
		this.setMysqlDatabase(plugin.getConfig().getString("mysql.database"));

		// Logging
		this.setLoggingDebug(plugin.getConfig().getBoolean("logging.debug"));

		// Broadcasts
		this.setBroadcastsNetworkConnect(plugin.getConfig().getString("broadcasts.network-connect"));
		this.setBroadcastsNetworkConnectNew(plugin.getConfig().getString("broadcasts.network-connect-new"));
		this.setBroadcastsNetworkDisconnect(plugin.getConfig().getString("broadcasts.network-disconnect"));
		this.setBroadcastsServerMove(plugin.getConfig().getString("broadcasts.server-move"));

		// Chat
		this.setChatFormat(plugin.getConfig().getString("chat.format"));
		this.setServerName(plugin.getConfig().getString("chat.server-name"));
		this.setServerShort(plugin.getConfig().getString("chat.server-short"));

		// Teleport > Wilderness
		this.setTeleportWildernessRadius(plugin.getConfig().getInt("teleport.wilderness.radius"));
		this.setTeleportWildernessCooldown(plugin.getConfig().getInt("teleport.wilderness.cooldown"));
		this.setTeleportWildernessDelay(plugin.getConfig().getInt("teleport.wilderness.delay"));
		this.setTeleportWildernessInterupt(plugin.getConfig().getBoolean("teleport.wilderness.interupt"));
		// Teleport > Tpr
		this.setTeleportTprCooldown(plugin.getConfig().getInt("teleport.tpr.cooldown"));
		this.setTeleportTprTimeout(plugin.getConfig().getInt("teleport.tpr.timeout"));
		this.setTeleportTprDelay(plugin.getConfig().getInt("teleport.tpr.delay"));
		this.setTeleportTprInterupt(plugin.getConfig().getBoolean("teleport.tpr.interupt"));
		// Teleport > Home
		this.setTeleportHomeCooldown(plugin.getConfig().getInt("teleport.home.cooldown"));
		this.setTeleportHomeDelay(plugin.getConfig().getInt("teleport.home.delay"));
		this.setTeleportHomeInterupt(plugin.getConfig().getBoolean("teleport.home.interupt"));
		// Teleport > Warp
		this.setTeleportWarpCooldown(plugin.getConfig().getInt("teleport.warp.cooldown"));
		this.setTeleportWarpDelay(plugin.getConfig().getInt("teleport.warp.delay"));
		this.setTeleportWarpInterupt(plugin.getConfig().getBoolean("teleport.warp.interupt"));
		// Teleport > World
		this.setTeleportWorldCooldown(plugin.getConfig().getInt("teleport.world.cooldown"));
		this.setTeleportWorldDelay(plugin.getConfig().getInt("teleport.world.delay"));
		this.setTeleportWorldInterupt(plugin.getConfig().getBoolean("teleport.world.interupt"));
		// Teleport > Spawn
		this.setTeleportSpawnCooldown(plugin.getConfig().getInt("teleport.spawn.cooldown"));
		this.setTeleportSpawnDelay(plugin.getConfig().getInt("teleport.spawn.delay"));
		this.setTeleportSpawnInterupt(plugin.getConfig().getBoolean("teleport.spawn.interupt"));

		// Announcements
		this.setAnnouncementInterval(plugin.getConfig().getInt("announcement.interval"));
		this.setAnnouncementMessages(plugin.getConfig().getStringList("announcement.messages"));

		// Rules
		this.setRules(plugin.getConfig().getStringList("rules"));

		// Ranks
		this.setRanks(plugin.getConfig().getStringList("ranks"));
	}

	// MySQL

	private String mysqlUser;
	private String mysqlPort;
	private String mysqlPassword;
	private String mysqlHost;
	private String mysqlDatabase;

	public void setMysqlUser(String mysqlUser) {
		this.mysqlUser = mysqlUser;
	}
	public String getMysqlUser() {
		return this.mysqlUser;
	}

	public void setMysqlPort(String mysqlPort) {
		this.mysqlPort = mysqlPort;
	}
	public String getMysqlPort() {
		return this.mysqlPort;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}
	public String getMysqlPassword() {
		return this.mysqlPassword;
	}

	public void setMysqlHost(String mysqlHost) {
		this.mysqlHost = mysqlHost;
	}
	public String getMysqlHost() {
		return this.mysqlHost;
	}

	public void setMysqlDatabase(String mysqlDatabase) {
		this.mysqlDatabase = mysqlDatabase;
	}
	public String getMysqlDatabase() {
		return this.mysqlDatabase;
	}

	// Logging

	private boolean loggingDebug;

	public void setLoggingDebug(boolean loggingDebug) {
		this.loggingDebug = loggingDebug;
	}
	public boolean getLoggingDebug() {
		return this.loggingDebug;
	}

	// Broadcasts

	private String broadcastsNetworkConnect;
	private String broadcastsNetworkConnectNew;
	private String broadcastsNetworkDisconnect;
	private String broadcastsServerMove;

	public void setBroadcastsNetworkConnect(String broadcastsNetworkConnect) {
		this.broadcastsNetworkConnect = broadcastsNetworkConnect;
	}
	public String getBroadcastsNetworkConnect() {
		return this.broadcastsNetworkConnect;
	}

	public void setBroadcastsNetworkConnectNew(String broadcastsNetworkConnectNew) {
		this.broadcastsNetworkConnectNew = broadcastsNetworkConnectNew;
	}
	public String getBroadcastsNetworkConnectNew() {
		return this.broadcastsNetworkConnectNew;
	}

	public void setBroadcastsNetworkDisconnect(String broadcastsNetworkDisconnect) {
		this.broadcastsNetworkDisconnect = broadcastsNetworkDisconnect;
	}
	public String getBroadcastsNetworkDisconnect() {
		return this.broadcastsNetworkDisconnect;
	}

	public void setBroadcastsServerMove(String broadcastsServerMove) {
		this.broadcastsServerMove = broadcastsServerMove;
	}
	public String getBroadcastsServerMove() {
		return this.broadcastsServerMove;
	}

	// Chat

	private String chatFormat;
	private String serverName;
	private String serverShort;

	public void setChatFormat(String chatFormat) {
		this.chatFormat = chatFormat;
	}
	public String getChatFormat() {
		return this.chatFormat;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerName() {
		return this.serverName;
	}

	public void setServerShort(String serverShort) {
		this.serverShort = serverShort;
	}
	public String getServerShort() {
		return this.serverShort;
	}

	// Teleport > Wilderness

	private int teleportWildernessRadius;
	private int teleportWildernessCooldown;
	private int teleportWildernessDelay;
	private boolean teleportWildernessInterupt;

	public void setTeleportWildernessRadius(int teleportWildernessRadius) {
		this.teleportWildernessRadius = teleportWildernessRadius;
	}
	public int getTeleportWildernessRadius() {
		return this.teleportWildernessRadius;
	}

	public void setTeleportWildernessCooldown(int teleportWildernessCooldown) {
		this.teleportWildernessCooldown = teleportWildernessCooldown;
	}
	public int getTeleportWildernessCooldown() {
		return this.teleportWildernessCooldown;
	}

	public void setTeleportWildernessDelay(int teleportWildernessDelay) {
		this.teleportWildernessDelay = teleportWildernessDelay;
	}
	public int getTeleportWildernessDelay() {
		return this.teleportWildernessDelay;
	}

	public void setTeleportWildernessInterupt(boolean teleportWildernessInterupt) {
		this.teleportWildernessInterupt = teleportWildernessInterupt;
	}
	public boolean getTeleportWildernessInterupt() {
		return this.teleportWildernessInterupt;
	}

	// Teleport > Tpr

	private int teleportTprCooldown;
	private int teleportTprTimeout;
	private int teleportTprDelay;
	private boolean teleportTprInterupt;

	public void setTeleportTprCooldown(int teleportTprCooldown) {
		this.teleportTprCooldown = teleportTprCooldown;
	}
	public int getTeleportTprCooldown() {
		return this.teleportTprCooldown;
	}

	public void setTeleportTprTimeout(int teleportTprTimeout) {
		this.teleportTprTimeout = teleportTprTimeout;
	}
	public int getTeleportTprTimeout() {
		return this.teleportTprTimeout;
	}

	public void setTeleportTprDelay(int teleportTprDelay) {
		this.teleportTprDelay = teleportTprDelay;
	}
	public int getTeleportTprDelay() {
		return this.teleportTprDelay;
	}

	public void setTeleportTprInterupt(boolean teleportTprInterupt) {
		this.teleportTprInterupt = teleportTprInterupt;
	}
	public boolean getTeleportTprInterupt() {
		return this.teleportTprInterupt;
	}

	// Teleport > Home

	private int teleportHomeCooldown;
	private int teleportHomeDelay;
	private boolean teleportHomeInterupt;

	public void setTeleportHomeCooldown(int teleportHomeCooldown) {
		this.teleportHomeCooldown = teleportHomeCooldown;
	}
	public int getTeleportHomeCooldown() {
		return this.teleportHomeCooldown;
	}

	public void setTeleportHomeDelay(int teleportHomeDelay) {
		this.teleportHomeDelay = teleportHomeDelay;
	}
	public int getTeleportHomeDelay() {
		return this.teleportHomeDelay;
	}

	public void setTeleportHomeInterupt(boolean teleportHomeInterupt) {
		this.teleportHomeInterupt = teleportHomeInterupt;
	}
	public boolean getTeleportHomeInterupt() {
		return this.teleportHomeInterupt;
	}

	// Teleport > Warp

	private int teleportWarpCooldown;
	private int teleportWarpDelay;
	private boolean teleportWarpInterupt;

	public void setTeleportWarpCooldown(int teleportWarpCooldown) {
		this.teleportWarpCooldown = teleportWarpCooldown;
	}
	public int getTeleportWarpCooldown() {
		return this.teleportWarpCooldown;
	}

	public void setTeleportWarpDelay(int teleportWarpDelay) {
		this.teleportWarpDelay = teleportWarpDelay;
	}
	public int getTeleportWarpDelay() {
		return this.teleportWarpDelay;
	}

	public void setTeleportWarpInterupt(boolean teleportWarpInterupt) {
		this.teleportWarpInterupt = teleportWarpInterupt;
	}
	public boolean getTeleportWarpInterupt() {
		return this.teleportWarpInterupt;
	}

	// Teleport > World

	private int teleportWorldCooldown;
	private int teleportWorldDelay;
	private boolean teleportWorldInterupt;

	public void setTeleportWorldCooldown(int teleportWorldCooldown) {
		this.teleportWorldCooldown = teleportWorldCooldown;
	}
	public int getTeleportWorldCooldown() {
		return this.teleportWorldCooldown;
	}

	public void setTeleportWorldDelay(int teleportWorldDelay) {
		this.teleportWorldDelay = teleportWorldDelay;
	}
	public int getTeleportWorldDelay() {
		return this.teleportWorldDelay;
	}

	public void setTeleportWorldInterupt(boolean teleportWorldInterupt) {
		this.teleportWorldInterupt = teleportWorldInterupt;
	}
	public boolean getTeleportWorldInterupt() {
		return this.teleportWorldInterupt;
	}

	// Teleport > Spawn

	private int teleportSpawnCooldown;
	private int teleportSpawnDelay;
	private boolean teleportSpawnInterupt;

	public void setTeleportSpawnCooldown(int teleportSpawnCooldown) {
		this.teleportSpawnCooldown = teleportSpawnCooldown;
	}
	public int getTeleportSpawnCooldown() {
		return this.teleportSpawnCooldown;
	}

	public void setTeleportSpawnDelay(int teleportSpawnDelay) {
		this.teleportSpawnDelay = teleportSpawnDelay;
	}
	public int getTeleportSpawnDelay() {
		return this.teleportSpawnDelay;
	}

	public void setTeleportSpawnInterupt(boolean teleportSpawnInterupt) {
		this.teleportSpawnInterupt = teleportSpawnInterupt;
	}
	public boolean getTeleportSpawnInterupt() {
		return this.teleportSpawnInterupt;
	}

	// Announcement

	private int announcementInterval;
	private List<String> announcementMessages;

	public void setAnnouncementInterval(int announcementInterval) {
		this.announcementInterval = announcementInterval;
	}
	public int getAnnouncementInterval() {
		return this.announcementInterval;
	}

	public void setAnnouncementMessages(List<String> announcementMessages) {
		this.announcementMessages = announcementMessages;
	}
	public List<String> getAnnouncementMessages() {
		return this.announcementMessages;
	}

	// Rules

	private List<String> rules;

	public void setRules(List<String> rules) {
		this.rules = rules;
	}
	public List<String> getRules() {
		return this.rules;
	}

	// Ranks

	private List<String> ranks;

	public void setRanks(List<String> ranks) {
		this.ranks = ranks;
	}
	public List<String> getRanks() {
		return this.ranks;
	}
}
