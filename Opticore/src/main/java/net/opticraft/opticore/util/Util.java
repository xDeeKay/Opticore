package net.opticraft.opticore.util;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	public ChatColor c(String color) {
		return ChatColor.valueOf(color.toUpperCase());
	}

	public void sendStyledMessage(Player player, CommandSender sender, String symbolColor, String symbol, String messageColor, String message) {

		String string = c("WHITE") + "[" + c(symbolColor) + symbol + c("WHITE") + "] " + c(messageColor) + message;

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

	public long parse(String input) {
		long result = 0;
		String number = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c)) {
				number += c;
			} else if (Character.isLetter(c) && !number.isEmpty()) {
				result += convert(Integer.parseInt(number), c);
				number = "";
			}
		}
		return result;
	}

	public long convert(int value, char unit) {
		switch (unit) {
		case 'w':
			return value * 604800;
		case 'd':
			return value * 86400;
		case 'h':
			return value * 3600;
		case 'm':
			return value * 60;
		case 's':
			return value * 1;
		}
		return 0;
	}

	public boolean isValidTimeString(String lengthString) {
		if (lengthString.contains("s") || 
				lengthString.contains("m") || 
				lengthString.contains("h") || 
				lengthString.contains("d")|| 
				lengthString.contains("w")) {
			return true;
		}
		return false;
	}

	public boolean isPermanent(String lengthString) {
		if ("permanent".startsWith(lengthString.toLowerCase()) || lengthString.equals("0")) {
			return true;
		}
		return false;
	}

	public String timestampDateFormat(long timestamp) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date(timestamp * 1000);
		String dateFormat = simpleDateFormat.format(date);
		return dateFormat;
	}
	
	public String timeConversion(long totalSeconds) {

	    int seconds = (int) (totalSeconds % 60);
	    int totalMinutes = (int) (totalSeconds / 60);
	    int minutes = totalMinutes % 60;
	    int hours = totalMinutes / 60;

	    return hours + "h " + minutes + "m " + seconds + "s";
	}

	public String parseColor(String target) {

		String playerMessageColorString = "WHITE";

		int playerMessageColor = plugin.players.get(target).getSettings().get("direct_message_color").getValue();

		if (playerMessageColor == 0) {
			playerMessageColorString = "BLACK";
		} else if (playerMessageColor == 1) {
			playerMessageColorString = "DARK_BLUE";
		} else if (playerMessageColor == 2) {
			playerMessageColorString = "DARK_GREEN";
		} else if (playerMessageColor == 3) {
			playerMessageColorString = "DARK_AQUA";
		} else if (playerMessageColor == 4) {
			playerMessageColorString = "DARK_RED";
		} else if (playerMessageColor == 5) {
			playerMessageColorString = "DARK_PURPLE";
		} else if (playerMessageColor == 6) {
			playerMessageColorString = "GOLD";
		} else if (playerMessageColor == 7) {
			playerMessageColorString = "GRAY";
		} else if (playerMessageColor == 8) {
			playerMessageColorString = "DARK_GRAY";
		} else if (playerMessageColor == 9) {
			playerMessageColorString = "BLUE";
		} else if (playerMessageColor == 10) {
			playerMessageColorString = "GREEN";
		} else if (playerMessageColor == 11) {
			playerMessageColorString = "AQUA";
		} else if (playerMessageColor == 12) {
			playerMessageColorString = "RED";
		} else if (playerMessageColor == 13) {
			playerMessageColorString = "LIGHT_PURPLE";
		} else if (playerMessageColor == 14) {
			playerMessageColorString = "YELLOW";
		} else if (playerMessageColor == 15) {
			playerMessageColorString = "WHITE";
		}

		return playerMessageColorString;
	}
}
