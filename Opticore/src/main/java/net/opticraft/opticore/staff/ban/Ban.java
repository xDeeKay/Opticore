package net.opticraft.opticore.staff.ban;

import java.sql.Timestamp;

public class Ban {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private Timestamp banDatetime;
	private int banLength;
	private String banReason;
	
	private String unbanUUID;
	private String unbanName;
	private Timestamp unbanDatetime;
	private String unbanReason;
	
	public Ban(int id, String targetUUID, String targetName, String senderUUID, String senderName, Timestamp banDatetime, int banLength, String banReason, String unbanUUID, String unbanName, Timestamp unbanDatetime, String unbanReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.banDatetime = banDatetime;
		this.banLength = banLength;
		this.banReason = banReason;
		
		this.unbanUUID = unbanUUID;
		this.unbanName = unbanName;
		this.unbanDatetime = unbanDatetime;
		this.unbanReason = unbanReason;
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
	
	public Timestamp getBanDatetime() {
		return banDatetime;
	}
	public void setBanDatetime(Timestamp banDatetime) {
		this.banDatetime = banDatetime;
	}
	
	public int getBanLength() {
		return banLength;
	}
	public void setBanLength(int banLength) {
		this.banLength = banLength;
	}
	
	public String getBanReason() {
		return banReason;
	}
	public void setBanReason(String banReason) {
		this.banReason = banReason;
	}
	
	public String getUnbanUUID() {
		return unbanUUID;
	}
	public void setUnbanUUID(String unbanUUID) {
		this.unbanUUID = unbanUUID;
	}
	
	public String getUnbanName() {
		return unbanName;
	}
	public void getUnbanName(String unbanName) {
		this.unbanName = unbanName;
	}
	
	public Timestamp getUnbanDatetime() {
		return unbanDatetime;
	}
	public void setUnbanDatetime(Timestamp unbanDatetime) {
		this.unbanDatetime = unbanDatetime;
	}
	
	public String getUnbanReason() {
		return unbanReason;
	}
	public void setUnbanReason(String unbanReason) {
		this.unbanReason = unbanReason;
	}
}
