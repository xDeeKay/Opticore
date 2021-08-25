package net.opticraft.opticore.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.opticraft.opticore.challenge.Challenge;
import net.opticraft.opticore.home.Home;
import net.opticraft.opticore.ignore.Ignore;
import net.opticraft.opticore.settings.Setting;

public class Player {
	
	private Map<String, Home> homes = new TreeMap<String, Home>(String.CASE_INSENSITIVE_ORDER);
	private int homesRemaining;
	
	private Map<String, Setting> settings = new TreeMap<String, Setting>(String.CASE_INSENSITIVE_ORDER);
	
	private Map<String, Ignore> ignored = new TreeMap<String, Ignore>(String.CASE_INSENSITIVE_ORDER);
	
	private String lastMessageFrom = null;
	
	private String tprOutgoing = null;
	private Set<String> tprIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	private String tprhereOutgoing = null;
	private Set<String> tprhereIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	private String youtube;
	private String twitch;
	private String twitter;
	private String instagram;
	private String discord;
	
	private int points;
	
	private long lastDaily;
	
	private long lastVote;
	
	private String tradeOutgoing = null;
	private Set<String> tradeIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	public Map<String, Challenge> challengesCompleted = new HashMap<String, Challenge>();

	public Map<String, Home> getHomes() {
		return homes;
	}

	public void setHomes(Map<String, Home> homes) {
		this.homes = homes;
	}

	public int getHomesRemaining() {
		return homesRemaining;
	}

	public void setHomesRemaining(int homesRemaining) {
		this.homesRemaining = homesRemaining;
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

	public String getTradeOutgoing() {
		return tradeOutgoing;
	}

	public void setTradeOutgoing(String tradeOutgoing) {
		this.tradeOutgoing = tradeOutgoing;
	}

	public Set<String> getTradeIncoming() {
		return tradeIncoming;
	}

	public void setTradeIncoming(Set<String> tradeIncoming) {
		this.tradeIncoming = tradeIncoming;
	}
	
	public Map<String, Challenge> getChallengesCompleted() {
		return challengesCompleted;
	}

	public void setChallengesCompleted(Map<String, Challenge> challengesCompleted) {
		this.challengesCompleted = challengesCompleted;
	}
}
