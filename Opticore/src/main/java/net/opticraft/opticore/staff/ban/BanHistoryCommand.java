package net.opticraft.opticore.staff.ban;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class BanHistoryCommand implements CommandExecutor {

	public Main plugin;
	
	public BanUtil banUtil;
	
	public Util util;

	public BanHistoryCommand(Main plugin) {
		this.plugin = plugin;
		this.banUtil = this.plugin.banUtil;
		this.util = this.plugin.util;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("banhistory")) {
			
			if (args.length == 1) {
				
				String target = args[0];
				String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
				
				banUtil.loadPlayerBans(target);
				
				int banAmount = plugin.bans.get(uuid).size();
				
				util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Showing ban history for player '" + target + "'.");
				
				String banRecordString = " bans on record.";
				if (banAmount == 1) {
					banRecordString = " ban on record.";
				}
				sender.sendMessage(ChatColor.GRAY + Integer.toString(banAmount) + banRecordString);
				
				for (Ban ban : plugin.bans.get(uuid)) {
					
					int banID = ban.getId();
					
					String senderName = ban.getSenderName();
					long banTimestamp = ban.getBanTimestamp();
					long banLength = ban.getBanLength();
					String banReason = ban.getBanReason();
					
					String unbanName = ban.getUnbanName();
					long unbanTimestamp = ban.getUnbanTimestamp();
					String unbanReason = ban.getUnbanReason();
					
					sender.sendMessage(ChatColor.RED + " Ban ID: " + ChatColor.WHITE + "#" + banID);
					sender.sendMessage(ChatColor.RED + " Ban By: " + ChatColor.WHITE + senderName);
					sender.sendMessage(ChatColor.RED + " Ban Date: " + ChatColor.WHITE + util.timestampDateFormat(banTimestamp) + " (UTC)");

					String banLengthString;
					String banExpiresString;
					if (banLength == 0) {
						banLengthString = "Permanent";
						banExpiresString = "Never";
					} else {
						banLengthString = util.getDurationString(banLength);
						banExpiresString = util.timestampDateFormat(banTimestamp + banLength) + " (UTC)";
					}
					
					sender.sendMessage(ChatColor.RED + " Ban Length: " + ChatColor.WHITE + banLengthString);
					sender.sendMessage(ChatColor.RED + " Ban Expires: " + ChatColor.WHITE + banExpiresString);
					sender.sendMessage(ChatColor.RED + " Ban Reason: " + ChatColor.WHITE + banReason);

					if (unbanName != null && unbanTimestamp != 0 && unbanReason != null) {
						sender.sendMessage(ChatColor.GREEN + " Unban By: " + ChatColor.WHITE + unbanName);
						sender.sendMessage(ChatColor.GREEN + " Unban Date: " + ChatColor.WHITE + util.timestampDateFormat(unbanTimestamp) + " (UTC)");
						sender.sendMessage(ChatColor.GREEN + " Unban Reason: " + ChatColor.WHITE + unbanReason);
					}

					sender.sendMessage(ChatColor.GRAY + "----------------------------------------");
				}

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /banhistory <player>");
			}
		}
		return true;
	}
}
