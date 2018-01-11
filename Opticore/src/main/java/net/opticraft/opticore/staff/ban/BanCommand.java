package net.opticraft.opticore.staff.ban;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class BanCommand implements CommandExecutor {

	public Main plugin;
	
	public BanUtil banUtil;
	
	public GuiUtil guiUtil;
	
	public BungeecordUtil bungeecordUtil;
	
	public Util util;

	public BanCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.banUtil = this.plugin.banUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ban")) {
			
			if (args.length == 0) {
				
				if (sender instanceof Player) {
					guiUtil.openGui((Player) sender, "ban", null);
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
				}
				
			} else if (args.length == 1) {
				
				String target = args[0];
				
				if (sender instanceof Player) {
					guiUtil.openGui((Player) sender, "banplayer", target);
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
				}
				
			} else if (args.length >= 3) {
				
				String target = args[0];
				String reason = StringUtils.join(args, ' ', 2, args.length);
				
				if (util.isInt(args[1])) {
					
					long length = Integer.parseInt(args[1]);
					
					if (plugin.getServer().getPlayer(target) != null) {
						// Target is online
						
						Player targetPlayer = plugin.getServer().getPlayer(target);
						
						banUtil.banPlayer(targetPlayer, sender.getName(), length, reason);
						
						util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Banned player '" + targetPlayer.getName() + "'.");
						
					} else {

						String server = bungeecordUtil.getPlayerServer(target);
						
						if (server != null) {
							// Target is on another server
							
							bungeecordUtil.sendBanCommand(server, target, sender.getName(), String.valueOf(length), reason);
							
							util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Banned player '" + target + "'.");
							
						} else {
							// Target is offline
							
							util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
						}
					}
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /ban <player> <length> <reason>");
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid number for <length>.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /ban [player] [length] [reason]");
			}
		}
		return true;
	}
}
