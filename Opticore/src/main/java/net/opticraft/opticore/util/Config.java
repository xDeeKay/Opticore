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

		// Server
		this.setServerName(plugin.getConfig().getString("server.name"));
		this.setServerShort(plugin.getConfig().getString("server.short"));

		// Teleport
		this.setTeleportRandomtpRadius(plugin.getConfig().getInt("teleport.randomtp-radius"));
		this.setTeleportRequestTimeout(plugin.getConfig().getInt("teleport.request-timeout"));

		// Announcement
		this.setAnnouncementInterval(plugin.getConfig().getInt("announcement.interval"));
		this.setAnnouncementMessages(plugin.getConfig().getStringList("announcement.messages"));

		// Rules
		this.setRules(plugin.getConfig().getStringList("rules"));

		// Ranks
		this.setRanks(plugin.getConfig().getStringList("ranks"));

		// Vote
		this.setVote(plugin.getConfig().getStringList("vote"));

		// Donate
		this.setDonate(plugin.getConfig().getStringList("donate"));

		// Points
		this.setPointsJoin(plugin.getConfig().getInt("points.join"));
		this.setPointsVote(plugin.getConfig().getInt("points.vote"));
		this.setPointsDaily(plugin.getConfig().getInt("points.daily"));
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

	// Server

	private String serverName;
	private String serverShort;

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

	// Teleport

	private int teleportRandomtpRadius;
	private int teleportRequestTimeout;

	public void setTeleportRandomtpRadius(int teleportRandomtpRadius) {
		this.teleportRandomtpRadius = teleportRandomtpRadius;
	}

	public int getTeleportRandomtpRadius() {
		return teleportRandomtpRadius;
	}

	public void setTeleportRequestTimeout(int teleportRequestTimeout) {
		this.teleportRequestTimeout = teleportRequestTimeout;
	}

	public int getTeleportRequestTimeout() {
		return teleportRequestTimeout;
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
	
	// Vote

	private List<String> vote;

	public void setVote(List<String> vote) {
		this.vote = vote;
	}

	public List<String> getVote() {
		return this.vote;
	}
	
	// Donate

	private List<String> donate;

	public void setDonate(List<String> donate) {
		this.donate = donate;
	}

	public List<String> getDonate() {
		return this.donate;
	}

	// Points

	private int pointsJoin;
	private int pointsVote;
	private int pointsDaily;

	public void setPointsJoin(int pointsJoin) {
		this.pointsJoin = pointsJoin;
	}

	public int getPointsJoin() {
		return pointsJoin;
	}

	public void setPointsVote(int pointsVote) {
		this.pointsVote = pointsVote;
	}

	public int getPointsVote() {
		return pointsVote;
	}

	public void setPointsDaily(int pointsDaily) {
		this.pointsDaily = pointsDaily;
	}

	public int getPointsDaily() {
		return pointsDaily;
	}
}
