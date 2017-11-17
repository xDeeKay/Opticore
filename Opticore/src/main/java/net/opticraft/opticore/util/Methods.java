package net.opticraft.opticore.util;

import java.util.logging.Level;

import net.opticraft.opticore.Main;

public class Methods {

	public Main plugin;

	public Config config;

	public Methods(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public void log(String message, Level level) {
		plugin.getLogger().log(level, message);
	}

	public void debug(String message) {
		if (config.getLoggingDebug()) {
			log(message, Level.INFO);
		}
	}
}