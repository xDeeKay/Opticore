package net.opticraft.opticore.staff.note;

import java.sql.Timestamp;

public class Note {

	private int id;
	
	private String targetUUID;
	private String targetName;
	
	private String senderUUID;
	private String senderName;
	
	private Timestamp noteDatetime;
	private String noteMessage;
	
	public Note(int id, String targetUUID, String targetName, String senderUUID, String senderName, Timestamp noteDatetime, String noteMessage) {
		this.id = id;
		
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		
		this.noteDatetime = noteDatetime;
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
	
	public Timestamp getNoteDatetime() {
		return noteDatetime;
	}
	public void setNoteDatetime(Timestamp noteDatetime) {
		this.noteDatetime = noteDatetime;
	}
	public String getNoteMessage() {
		return noteMessage;
	}
	public void setNoteMessage(String noteMessage) {
		this.noteMessage = noteMessage;
	}
}
