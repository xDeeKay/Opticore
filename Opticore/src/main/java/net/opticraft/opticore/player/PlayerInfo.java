package net.opticraft.opticore.player;

public class PlayerInfo {

	private int settingsConnectDisconnect;
	private int settingsServerChange;
	private int settingsPlayerChat;
	private int settingsServerAnnouncement;
	private int settingsFriendRequest;
	private int settingsDirectMessage;
	private int settingsTeleportRequest;
	private int settingsSpectateRequest;

	public PlayerInfo(int settingsConnectDisconnect, int settingsServerChange, int settingsPlayerChat, int settingsServerAnnouncement, int settingsFriendRequest, int settingsDirectMessage, int settingsTeleportRequest, int settingsSpectateRequest) {
		this.settingsConnectDisconnect = settingsConnectDisconnect;
		this.settingsServerChange = settingsServerChange;
		this.settingsPlayerChat = settingsPlayerChat;
		this.settingsServerAnnouncement = settingsServerAnnouncement;
		this.settingsFriendRequest = settingsFriendRequest;
		this.settingsDirectMessage = settingsDirectMessage;
		this.settingsTeleportRequest = settingsTeleportRequest;
		this.settingsSpectateRequest = settingsSpectateRequest;
	}

	public int getSettingsConnectDisconnect() {
		return settingsConnectDisconnect;
	}
	public void setSettingsConnectDisconnect(int setting) {
		this.settingsConnectDisconnect = setting;
	}

	public int getSettingsServerChange() {
		return settingsServerChange;
	}
	public void setSettingsServerChange(int setting) {
		this.settingsServerChange = setting;
	}
	
	public int getSettingsPlayerChat() {
		return settingsPlayerChat;
	}
	public void setSettingsPlayerChat(int setting) {
		this.settingsPlayerChat = setting;
	}
	
	public int getSettingsServerAnnouncement() {
		return settingsServerAnnouncement;
	}
	public void setSettingsServerAnnouncement(int setting) {
		this.settingsServerAnnouncement = setting;
	}
	
	public int getSettingsFriendRequest() {
		return settingsFriendRequest;
	}
	public void setSettingsFriendRequest(int setting) {
		this.settingsFriendRequest = setting;
	}
	
	public int getSettingsDirectMessage() {
		return settingsDirectMessage;
	}
	public void setSettingsDirectMessage(int setting) {
		this.settingsDirectMessage = setting;
	}
	
	public int getSettingsTeleportRequest() {
		return settingsTeleportRequest;
	}
	public void setSettingsTeleportRequest(int setting) {
		this.settingsTeleportRequest = setting;
	}
	
	public int getSettingsSpectateRequest() {
		return settingsSpectateRequest;
	}
	public void setSettingsSpectateRequest(int setting) {
		this.settingsSpectateRequest = setting;
	}
}
