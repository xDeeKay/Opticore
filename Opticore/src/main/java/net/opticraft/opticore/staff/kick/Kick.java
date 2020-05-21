package net.opticraft.opticore.staff.kick;

public class Kick {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private long kickTimestamp;
	private String kickReason;
	
	public Kick(int id, String targetUUID, String targetName, String senderUUID, String senderName, long kickTimestamp, String kickReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.kickTimestamp = kickTimestamp;
		this.kickReason = kickReason;
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
	
	public long getKickTimestamp() {
		return kickTimestamp;
	}
	
	public void setKickTimestamp(long kickTimestamp) {
		this.kickTimestamp = kickTimestamp;
	}
	
	public String getKickReason() {
		return kickReason;
	}
	
	public void setKickReason(String kickReason) {
		this.kickReason = kickReason;
	}
}
