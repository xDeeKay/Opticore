package net.opticraft.opticore.staff.ban;

public class Ban {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private long banTimestamp;
	private long banLength;
	private String banReason;
	
	private String unbanUUID;
	private String unbanName;
	private long unbanTimestamp;
	private String unbanReason;
	
	public Ban(int id, String targetUUID, String targetName, String senderUUID, String senderName, long banTimestamp, long banLength, String banReason, String unbanUUID, String unbanName, long unbanTimestamp, String unbanReason) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.banTimestamp = banTimestamp;
		this.banLength = banLength;
		this.banReason = banReason;
		
		this.unbanUUID = unbanUUID;
		this.unbanName = unbanName;
		this.unbanTimestamp = unbanTimestamp;
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
	
	public long getBanTimestamp() {
		return banTimestamp;
	}
	
	public void setBanTimestamp(long banTimestamp) {
		this.banTimestamp = banTimestamp;
	}
	
	public long getBanLength() {
		return banLength;
	}
	
	public void setBanLength(long banLength) {
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
	
	public long getUnbanTimestamp() {
		return unbanTimestamp;
	}
	
	public void setUnbanTimestamp(long unbanTimestamp) {
		this.unbanTimestamp = unbanTimestamp;
	}
	
	public String getUnbanReason() {
		return unbanReason;
	}
	
	public void setUnbanReason(String unbanReason) {
		this.unbanReason = unbanReason;
	}
}
