package net.opticraft.opticore.player;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.opticraft.opticore.home.Home;
import net.opticraft.opticore.settings.Setting;

public class Player {
	
	private Map<String, Home> homes = new TreeMap<String, Home>(String.CASE_INSENSITIVE_ORDER);
	
	private Map<String, Setting> settings = new TreeMap<String, Setting>(String.CASE_INSENSITIVE_ORDER);
	
	private String lastMessageFrom = null;
	
	private String tprOutgoing = null;
	private Set<String> tprIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	private String tprhereOutgoing = null;
	private Set<String> tprhereIncoming = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	private int homesRemaining;
	
	private String world;
	
	private int settingsConnectDisconnect;
	private int settingsServerChange;
	private int settingsPlayerChat;
	private int settingsServerAnnouncement;
	private int settingsFriendRequest;
	private int settingsDirectMessage;
	private int settingsTeleportRequest;
	private int settingsSpectateRequest;

	public Map<String, Home> getHomes() {
		return homes;
	}
	public void setHomes(Map<String, Home> homes) {
		this.homes = homes;
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
	
	public int getHomesRemaining() {
		return homesRemaining;
	}
	public void setHomesRemaining(int homesRemaining) {
		this.homesRemaining = homesRemaining;
	}
	
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}

	public int getSettingsConnectDisconnect() {
		return settingsConnectDisconnect;
	}
	public void setSettingsConnectDisconnect(int settingsConnectDisconnect) {
		this.settingsConnectDisconnect = settingsConnectDisconnect;
	}

	public int getSettingsServerChange() {
		return settingsServerChange;
	}
	public void setSettingsServerChange(int settingsServerChange) {
		this.settingsServerChange = settingsServerChange;
	}
	
	public int getSettingsPlayerChat() {
		return settingsPlayerChat;
	}
	public void setSettingsPlayerChat(int settingsPlayerChat) {
		this.settingsPlayerChat = settingsPlayerChat;
	}
	
	public int getSettingsServerAnnouncement() {
		return settingsServerAnnouncement;
	}
	public void setSettingsServerAnnouncement(int settingsServerAnnouncement) {
		this.settingsServerAnnouncement = settingsServerAnnouncement;
	}
	
	public int getSettingsFriendRequest() {
		return settingsFriendRequest;
	}
	public void setSettingsFriendRequest(int settingsFriendRequest) {
		this.settingsFriendRequest = settingsFriendRequest;
	}
	
	public int getSettingsDirectMessage() {
		return settingsDirectMessage;
	}
	public void setSettingsDirectMessage(int settingsDirectMessage) {
		this.settingsDirectMessage = settingsDirectMessage;
	}
	
	public int getSettingsTeleportRequest() {
		return settingsTeleportRequest;
	}
	public void setSettingsTeleportRequest(int settingsTeleportRequest) {
		this.settingsTeleportRequest = settingsTeleportRequest;
	}
	
	public int getSettingsSpectateRequest() {
		return settingsSpectateRequest;
	}
	public void setSettingsSpectateRequest(int settingsSpectateRequest) {
		this.settingsSpectateRequest = settingsSpectateRequest;
	}
}
