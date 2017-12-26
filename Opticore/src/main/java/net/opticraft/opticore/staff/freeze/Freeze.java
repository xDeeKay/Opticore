package net.opticraft.opticore.staff.freeze;

import java.sql.Timestamp;

public class Freeze {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private Timestamp freezeDatetime;
	private int freezeLength;
	private String freezeReason;
	
	private String unfreezeUUID;
	private String unfreezeName;
	private Timestamp unfreezeDatetime;
	private String unfreezeReason;
	
	public Freeze(int id, String targetUUID, String targetName, String senderUUID, String senderName, Timestamp freezeDatetime, int freezeLength, String freezeReason, String unfreezeUUID, String unfreezeName, Timestamp unfreezeDatetime, String unfreezeReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.freezeDatetime = freezeDatetime;
		this.freezeLength = freezeLength;
		this.freezeReason = freezeReason;
		
		this.unfreezeUUID = unfreezeUUID;
		this.unfreezeName = unfreezeName;
		this.unfreezeDatetime = unfreezeDatetime;
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
	
	public Timestamp getFreezeDatetime() {
		return freezeDatetime;
	}
	public void setFreezeDatetime(Timestamp freezeDatetime) {
		this.freezeDatetime = freezeDatetime;
	}
	
	public int getFreezeLength() {
		return freezeLength;
	}
	public void setFreezeLength(int freezeLength) {
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
	
	public Timestamp getUnfreezeDatetime() {
		return unfreezeDatetime;
	}
	public void setUnfreezeDatetime(Timestamp unfreezeDatetime) {
		this.unfreezeDatetime = unfreezeDatetime;
	}
	
	public String getUnfreezeReason() {
		return unfreezeReason;
	}
	public void setUnfreezeReason(String unfreezeReason) {
		this.unfreezeReason = unfreezeReason;
	}
}
