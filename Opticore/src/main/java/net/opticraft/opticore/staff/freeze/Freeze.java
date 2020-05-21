package net.opticraft.opticore.staff.freeze;

public class Freeze {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private long freezeTimestamp;
	private long freezeLength;
	private String freezeReason;
	
	private String unfreezeUUID;
	private String unfreezeName;
	private long unfreezeTimestamp;
	private String unfreezeReason;
	
	public Freeze(int id, String targetUUID, String targetName, String senderUUID, String senderName, long freezeTimestamp, long freezeLength, String freezeReason, String unfreezeUUID, String unfreezeName, long unfreezeTimestamp, String unfreezeReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.freezeTimestamp = freezeTimestamp;
		this.freezeLength = freezeLength;
		this.freezeReason = freezeReason;
		
		this.unfreezeUUID = unfreezeUUID;
		this.unfreezeName = unfreezeName;
		this.unfreezeTimestamp = unfreezeTimestamp;
		this.unfreezeReason = unfreezeReason;
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
	
	public long getFreezeTimestamp() {
		return freezeTimestamp;
	}
	
	public void setFreezeTimestamp(long freezeTimestamp) {
		this.freezeTimestamp = freezeTimestamp;
	}
	
	public long getFreezeLength() {
		return freezeLength;
	}
	
	public void setFreezeLength(long freezeLength) {
		this.freezeLength = freezeLength;
	}
	
	public String getFreezeReason() {
		return freezeReason;
	}
	
	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}
	
	public String getUnfreezeUUID() {
		return unfreezeUUID;
	}
	
	public void setUnfreezeUUID(String unfreezeUUID) {
		this.unfreezeUUID = unfreezeUUID;
	}
	
	public String getUnfreezeName() {
		return unfreezeName;
	}
	
	public void getUnfreezeName(String unfreezeName) {
		this.unfreezeName = unfreezeName;
	}
	
	public long getUnfreezeTimestamp() {
		return unfreezeTimestamp;
	}
	
	public void setUnfreezeTimestamp(long unfreezeTimestamp) {
		this.unfreezeTimestamp = unfreezeTimestamp;
	}
	
	public String getUnfreezeReason() {
		return unfreezeReason;
	}
	
	public void setUnfreezeReason(String unfreezeReason) {
		this.unfreezeReason = unfreezeReason;
	}
}
