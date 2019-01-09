package net.opticraft.opticore.player;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.opticraft.opticore.home.Home;
import net.opticraft.opticore.ignore.Ignore;
import net.opticraft.opticore.settings.Setting;

public class Player {
	
	private Map<String, Home> homes = new TreeMap<String, Home>(String.CASE_INSENSITIVE_ORDER);
	private int homesAmount;
	
	private Map<String, Setting> settings = new TreeMap<String, Setting>(String.CASE_INSENSITIVE_ORDER);
	
	private Map<String, Ignore> ignored = new TreeMap<String, Ignore>(String.CASE_INSENSITIVE_ORDER);
	
	private String lastMessageFrom = null;
	
	private String tprOutgoing = null; // your outgoing tpr request to a player - teleport you to them
	private Set<String> tprIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER); // your incoming tpr requests from players - teleport them to you
	
	private String tprhereOutgoing = null; // your outgoing tprhere request to a player - teleport them to you
	private Set<String> tprhereIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER); // your incoming tprhere requests from players - teleport you to them
	
	private String youtube;
	private String twitch;
	private String twitter;
	private String instagram;
	private String discord;
	
	private int points;
	
	private long lastDaily;
	
	private long lastVote;

	public Map<String, Home> getHomes() {
		return homes;
	}

	public void setHomes(Map<String, Home> homes) {
		this.homes = homes;
	}

	public int getHomesAmount() {
		return homesAmount;
	}

	public void setHomesAmount(int homesAmount) {
		this.homesAmount = homesAmount;
	}

	public Map<String, Setting> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, Setting> settings) {
		this.settings = settings;
	}

	public String getLastMessageFrom() {
		return lastMessageFrom;
	}

	public void setLastMessageFrom(String lastMessageFrom) {
		this.lastMessageFrom = lastMessageFrom;
	}
	
	public String getTprOutgoing() {
		return tprOutgoing;
	}

	public void setTprOutgoing(String tprOutgoing) {
		this.tprOutgoing = tprOutgoing;
	}

	public Set<String> getTprIncoming() {
		return tprIncoming;
	}

	public void setTprIncoming(Set<String> tprIncoming) {
		this.tprIncoming = tprIncoming;
	}
	
	public String getTprhereOutgoing() {
		return tprhereOutgoing;
	}

	public void setTprhereOutgoing(String tprhereOutgoing) {
		this.tprhereOutgoing = tprhereOutgoing;
	}

	public Set<String> getTprhereIncoming() {
		return tprhereIncoming;
	}

	public void setTprhereIncoming(Set<String> tprhereIncoming) {
		this.tprhereIncoming = tprhereIncoming;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getTwitch() {
		return twitch;
	}

	public void setTwitch(String twitch) {
		this.twitch = twitch;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getDiscord() {
		return discord;
	}

	public void setDiscord(String discord) {
		this.discord = discord;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public long getLastDaily() {
		return lastDaily;
	}

	public void setLastDaily(long lastDaily) {
		this.lastDaily = lastDaily;
	}

	public long getLastVote() {
		return lastVote;
	}

	public void setLastVote(long lastVote) {
		this.lastVote = lastVote;
	}

	public Map<String, Ignore> getIgnored() {
		return ignored;
	}

	public void setIgnored(Map<String, Ignore> ignored) {
		this.ignored = ignored;
	}
}
