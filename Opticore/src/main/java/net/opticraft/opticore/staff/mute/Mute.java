package net.opticraft.opticore.staff.mute;

public class Mute {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private long muteTimestamp;
	private long muteLength;
	private String muteReason;
	
	private String unmuteUUID;
	private String unmuteName;
	private long unmuteTimestamp;
	private String unmuteReason;
	
	public Mute(int id, String targetUUID, String targetName, String senderUUID, String senderName, long muteTimestamp, long muteLength, String muteReason, String unmuteUUID, String unmuteName, long unmuteTimestamp, String unmuteReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.muteTimestamp = muteTimestamp;
		this.muteLength = muteLength;
		this.muteReason = muteReason;
		
		this.unmuteUUID = unmuteUUID;
		this.unmuteName = unmuteName;
		this.unmuteTimestamp = unmuteTimestamp;
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
	
	public long getMuteTimestamp() {
		return muteTimestamp;
	}
	public void setMuteTimestamp(long muteTimestamp) {
		this.muteTimestamp = muteTimestamp;
	}
	
	public long getMuteLength() {
		return muteLength;
	}
	public void setMuteLength(long muteLength) {
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
	
	public long getUnmuteTimestamp() {
		return unmuteTimestamp;
	}
	public void setUnmuteTimestamp(long unmuteTimestamp) {
		this.unmuteTimestamp = unmuteTimestamp;
	}
	
	public String getUnmuteReason() {
		return unmuteReason;
	}
	public void setUnmuteReason(String unmuteReason) {
		this.unmuteReason = unmuteReason;
	}
}
