package net.opticraft.opticore.util;

import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
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

	public ChatColor color(String color) {
		return ChatColor.valueOf(color.toUpperCase());
	}
	
	public void sendStyledMessage(Player player, CommandSender sender, String symbolColor, String symbol, String messageColor, String message) {
		
		String string = color("WHITE") + "[" + color(symbolColor) + symbol + color("WHITE") + "] " + color(messageColor) + message;
		
		if (player != null) {
			player.sendMessage(string);
		} else if (sender != null) {
			sender.sendMessage(string);
		} else {
			plugin.getServer().broadcastMessage(string);
		}
	}
	
	public boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
