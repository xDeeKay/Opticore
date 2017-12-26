package net.opticraft.opticore.staff.mute;

import java.sql.Timestamp;

public class Mute {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private Timestamp muteDatetime;
	private int muteLength;
	private String muteReason;
	
	private String unmuteUUID;
	private String unmuteName;
	private Timestamp unmuteDatetime;
	private String unmuteReason;
	
	public Mute(int id, String targetUUID, String targetName, String senderUUID, String senderName, Timestamp muteDatetime, int muteLength, String muteReason, String unmuteUUID, String unmuteName, Timestamp unmuteDatetime, String unmuteReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.muteDatetime = muteDatetime;
		this.muteLength = muteLength;
		this.muteReason = muteReason;
		
		this.unmuteUUID = unmuteUUID;
		this.unmuteName = unmuteName;
		this.unmuteDatetime = unmuteDatetime;
		this.unmuteReason = unmuteReason;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTargetUUID() {
		return targetUUID;
	}
	public void setTargetUUID(String targetUUID) {
		this.targetUUID = targetUUID;
	}
	
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public String getSenderUUID() {
		return senderUUID;
	}
	public void setSenderUUID(String senderUUID) {
		this.senderUUID = senderUUID;
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public Timestamp getMuteDatetime() {
		return muteDatetime;
	}
	public void setMuteDatetime(Timestamp muteDatetime) {
		this.muteDatetime = muteDatetime;
	}
	
	public int getMuteLength() {
		return muteLength;
	}
	public void setMuteLength(int muteLength) {
		this.muteLength = muteLength;
	}
	
	public String getMuteReason() {
		return muteReason;
	}
	public void setMuteReason(String muteReason) {
		this.muteReason = muteReason;
	}
	
	public String getUnmuteUUID() {
		return unmuteUUID;
	}
	public void setUnmuteUUID(String unmuteUUID) {
		this.unmuteUUID = unmuteUUID;
	}
	
	public String getUnmuteName() {
		return unmuteName;
	}
	public void getUnmuteName(String unmuteName) {
		this.unmuteName = unmuteName;
	}
	
	public Timestamp getUnmuteDatetime() {
		return unmuteDatetime;
	}
	public void setUnmuteDatetime(Timestamp unmuteDatetime) {
		this.unmuteDatetime = unmuteDatetime;
	}
	
	public String getUnmuteReason() {
		return unmuteReason;
	}
	public void setUnmuteReason(String unmuteReason) {
		this.unmuteReason = unmuteReason;
	}
}
