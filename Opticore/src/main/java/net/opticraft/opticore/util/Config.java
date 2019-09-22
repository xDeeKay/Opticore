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
		this.setLoggingDebugEnabled(plugin.getConfig().getBoolean("logging.debug"));
		this.setLoggingAccessKey(plugin.getConfig().getString("logging.access-key"));

		// Server
		this.setServerName(plugin.getConfig().getString("server.name"));
		this.setServerShort(plugin.getConfig().getString("server.short"));

		// Announcement
		this.setAnnouncementInterval(plugin.getConfig().getInt("announcement.interval"));
		this.setAnnouncementMessages(plugin.getConfig().getStringList("announcement.messages"));

		// Teleport
		this.setTeleportRandomtpRadius(plugin.getConfig().getInt("teleport.randomtp-radius"));
		this.setTeleportRequestTimeout(plugin.getConfig().getInt("teleport.request-timeout"));

		// Points
		this.setPointsJoin(plugin.getConfig().getInt("points.join"));
		this.setPointsVote(plugin.getConfig().getInt("points.vote"));
		this.setPointsDaily(plugin.getConfig().getInt("points.daily"));

		// Market
		this.setMarketMaxTime(plugin.getConfig().getLong("market.max-time"));
		this.setMarketMaxTrades(plugin.getConfig().getInt("market.max-trades"));

		// Reminder
		this.setReminderVoteInterval(plugin.getConfig().getInt("reminder.vote.interval"));
		this.setReminderVoteMessages(plugin.getConfig().getStringList("reminder.vote.messages"));
		this.setReminderDailyInterval(plugin.getConfig().getInt("reminder.daily.interval"));
		this.setReminderDailyMessages(plugin.getConfig().getStringList("reminder.daily.messages"));

		// Modded
		this.setModded(plugin.getConfig().getStringList("modded"));

		// Rules
		this.setRules(plugin.getConfig().getStringList("rules"));

		// Ranks
		this.setRanks(plugin.getConfig().getStringList("ranks"));

		// Information
		this.setInformation(plugin.getConfig().getStringList("information"));

		// Livemap
		this.setLivemap(plugin.getConfig().getStringList("livemap"));

		// Vote
		this.setVote(plugin.getConfig().getStringList("vote"));

		// Donate
		this.setDonate(plugin.getConfig().getStringList("donate"));
		
		// Challenges
		this.setChallengesAmount(plugin.getConfig().getInt("challenges.amount"));
		this.setChallengesInterval(plugin.getConfig().getLong("challenges.interval"));
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
	private String loggingAccessKey;

	public void setLoggingDebugEnabled(boolean loggingDebug) {
		this.loggingDebug = loggingDebug;
	}

	public boolean getLoggingDebugEnabled() {
		return this.loggingDebug;
	}

	public String getLoggingAccessKey() {
		return loggingAccessKey;
	}

	public void setLoggingAccessKey(String loggingAccessKey) {
		this.loggingAccessKey = loggingAccessKey;
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

	// Market

	private long marketMaxTime;
	private int marketMaxTrades;

	public void setMarketMaxTime(long marketMaxTime) {
		this.marketMaxTime = marketMaxTime;
	}

	public long getMarketMaxTime() {
		return marketMaxTime;
	}

	public void setMarketMaxTrades(int marketMaxTrades) {
		this.marketMaxTrades = marketMaxTrades;
	}

	public int getMarketMaxTrades() {
		return marketMaxTrades;
	}

	// Reminder

	private int reminderVoteInterval;
	private List<String> reminderVoteMessages;
	private int reminderDailyInterval;
	private List<String> reminderDailyMessages;


	public void setReminderVoteInterval(int reminderVoteInterval) {
		this.reminderVoteInterval = reminderVoteInterval;
	}

	public int getReminderVoteInterval() {
		return this.reminderVoteInterval;
	}

	public void setReminderVoteMessages(List<String> reminderVoteMessages) {
		this.reminderVoteMessages = reminderVoteMessages;
	}

	public List<String> getReminderVoteMessages() {
		return this.reminderVoteMessages;
	}

	public void setReminderDailyInterval(int reminderDailyInterval) {
		this.reminderDailyInterval = reminderDailyInterval;
	}

	public int getReminderDailyInterval() {
		return this.reminderDailyInterval;
	}

	public void setReminderDailyMessages(List<String> reminderDailyMessages) {
		this.reminderDailyMessages = reminderDailyMessages;
	}

	public List<String> getReminderDailyMessages() {
		return this.reminderDailyMessages;
	}

	// Rules

	private List<String> modded;

	public void setModded(List<String> modded) {
		this.modded = modded;
	}

	public List<String> getModded() {
		return this.modded;
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

	// Information

	private List<String> information;

	public void setInformation(List<String> information) {
		this.information = information;
	}

	public List<String> getInformation() {
		return this.information;
	}

	// Livemap

	private List<String> livemap;

	public void setLivemap(List<String> livemap) {
		this.livemap = livemap;
	}

	public List<String> getLivemap() {
		return this.livemap;
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
	
	// Challenges

	private int challengesAmount;
	private long challengesInterval;

	public void setChallengesAmount(int challengesAmount) {
		this.challengesAmount = challengesAmount;
	}

	public int getChallengesAmount() {
		return this.challengesAmount;
	}
	
	public void setChallengesInterval(long challengesInterval) {
		this.challengesInterval = challengesInterval;
	}

	public long getChallengesInterval() {
		return this.challengesInterval;
	}
}
