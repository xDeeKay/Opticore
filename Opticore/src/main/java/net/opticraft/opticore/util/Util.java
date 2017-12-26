package net.opticraft.opticore.util;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Util {

	public Main plugin;

	public Config config;

	public Util(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public void log(Level level, String message) {
		plugin.getLogger().log(level, message);
	}

	public void debug(String message) {
		if (config.getLoggingDebug()) {
			log(Level.INFO, message);
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
	
	public String getServerShort(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0);
		return rank;
	}

	public String getPlayerGroup(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0);
		return rank;
	}

	public String getPlayerGroupPrefix(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		String color = user.getPrefix();
		return color;
	}
}
