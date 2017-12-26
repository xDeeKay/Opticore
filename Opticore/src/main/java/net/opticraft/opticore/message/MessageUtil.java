package net.opticraft.opticore.message;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;

public class MessageUtil {
	
	public Main plugin;

	public Config config;

	public MessageUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}
	
	public void message(String target, String message) {
		
	}
}
