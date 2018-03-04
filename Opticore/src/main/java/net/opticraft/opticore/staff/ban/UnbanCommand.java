package net.opticraft.opticore.staff.ban;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class UnbanCommand implements CommandExecutor {

	public Main plugin;
	
	public BanUtil banUtil;
	
	public Util util;

	public UnbanCommand(Main plugin) {
		this.plugin = plugin;
		this.banUtil = this.plugin.banUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("unban") || cmd.getName().equalsIgnoreCase("pardon")) {
			
			if (args.length >= 2) {
				
				String target = args[0];
				String reason = StringUtils.join(args, ' ', 1, args.length);

				if (banUtil.getActiveBan(target) != null) {
					
					int id = banUtil.getActiveBan(target).getId();
					
					banUtil.unbanPlayer(id, sender.getName(), reason);
					
					util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Unbanned player '" + target + "'.");
					
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "The player '" + target + "' is not banned.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /unban <player> <reason>");
			}
		}
		return true;
	}
}
