package net.opticraft.opticore.staff.note;

public class Note {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private long noteTimestamp;
	private String noteMessage;
	
	public Note(int id, String targetUUID, String targetName, String senderUUID, String senderName, long noteTimestamp, String noteMessage) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.noteTimestamp = noteTimestamp;
		this.noteMessage = noteMessage;
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
	
	public long getNoteTimestamp() {
		return noteTimestamp;
	}
	
	public void setNoteTimestamp(long noteTimestamp) {
		this.noteTimestamp = noteTimestamp;
	}
	
	public String getNoteMessage() {
		return noteMessage;
	}
	
	public void setNoteMessage(String noteMessage) {
		this.noteMessage = noteMessage;
	}
}
