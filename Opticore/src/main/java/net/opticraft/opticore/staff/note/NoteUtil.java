package net.opticraft.opticore.staff.note;

import java.util.Arrays;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.MySQL;

public class NoteUtil {
	
	public Main plugin;
	
	public MySQL mysql;

	public NoteUtil(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
	}
	
	@SuppressWarnings("deprecation")
	public void addNote(String target, String sender, String message) {
		
		String targetUUID =  plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
		String senderUUID = plugin.getServer().getOfflinePlayer(sender).getUniqueId().toString();
		
		long timestamp = System.currentTimeMillis();
		
		plugin.mysql.insert("oc_note", 
				Arrays.asList("target_uuid", "target_name", "sender_uuid", "sender_name", "note_datetime", "note_message"), 
				Arrays.asList(targetUUID, target, senderUUID, sender, timestamp, message));
	}
	
	public void noteList(String target) {
		
		//String targetUUID =  plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
	}
}
