package net.opticraft.opticore.player;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.opticraft.opticore.home.HomeInfo;

public class PlayerInfo {
	
	private Map<String, HomeInfo> homes = new TreeMap<String, HomeInfo>(String.CASE_INSENSITIVE_ORDER);
	
	private Set<String> teleportRequests = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	private String teleportTo = null;
	
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
	
	public Map<String, HomeInfo> getHomes() {
		return homes;
	}
	public void setHomes(Map<String, HomeInfo> homes) {
		this.homes = homes;
	}
	
	public Set<String> getTeleportRequests() {
		return teleportRequests;
	}
	public void setTeleportingRequests(Set<String> teleportRequests) {
		this.teleportRequests = teleportRequests;
	}
	
	public String getTeleportTo() {
		return teleportTo;
	}
	public void setTeleportTo(String teleportTo) {
		this.teleportTo = teleportTo;
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
