package net.opticraft.opticore.staff.kick;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class KickCommand implements CommandExecutor {

	public Main plugin;
	
	public KickUtil kickUtil;
	
	public GuiUtil guiUtil;
	
	public BungeecordUtil bungeecordUtil;
	
	public Util util;

	public KickCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.kickUtil = this.plugin.kickUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kick")) {
			
			if (args.length == 0) {
				
				if (sender instanceof Player) {
					guiUtil.openKickGui((Player) sender);
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
				}
				
			} else if (args.length == 1) {
				
				String target = args[0];
				
				if (sender instanceof Player) {
					guiUtil.openKickPlayerGui((Player) sender, target);
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
				}
				
			} else if (args.length >= 2) {
				
				String target = args[0];
				String reason = StringUtils.join(args, ' ', 1, args.length);
				
				if (plugin.getServer().getPlayer(target) != null) {
					// Target is online
					
					Player targetPlayer = plugin.getServer().getPlayer(target);
					
					kickUtil.kickPlayer(targetPlayer, sender.getName(), reason);
					
					util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Kicked player '" + targetPlayer.getName() + "'.");
					
				} else {

					String server = bungeecordUtil.getPlayerServer(target);
					
					if (server != null) {
						// Target is on another server
						
						bungeecordUtil.sendKickCommand(server, target, sender.getName(), reason);
						
						util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Kicked player '" + target + "'.");
						
					} else {
						// Target is offline
						
						util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
					}
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /kick [player] [reason]");
			}
		}
		return true;
	}
}
