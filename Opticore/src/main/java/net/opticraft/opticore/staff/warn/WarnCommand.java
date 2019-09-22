package net.opticraft.opticore.staff.warn;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class WarnCommand implements CommandExecutor {

	public Main plugin;

	public WarnUtil warnUtil;

	public BungeecordUtil bungeecordUtil;

	public Util util;

	public ServerUtil serverUtil;

	public WarnCommand(Main plugin) {
		this.plugin = plugin;
		this.warnUtil = this.plugin.warnUtil;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.util = this.plugin.util;
		this.serverUtil = this.plugin.serverUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warn")) {

			if (args.length >= 2) {
				
				String target = args[0];
				String reason = StringUtils.join(args, ' ', 1, args.length);

				if (plugin.getServer().getPlayer(target) != null) {
					// Target is online
					
					Player targetPlayer = plugin.getServer().getPlayer(target);
					
					warnUtil.warnPlayer(targetPlayer.getName(), sender.getName(), reason);
					util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Warned player '" + targetPlayer.getName() + "'.");

				} else {

					String server = serverUtil.getPlayerServerName(target);

					if (server != null) {
						// Target is on another server
						
						if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
							// BungeeCord limitation handler: sending server is not empty and can send bungeecord message to target server
							bungeecordUtil.sendWarnCommand(server, target, sender.getName(), reason);
							util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Warned player '" + target + "'.");
							
						} else {
							// BungeeCord limitation handler: sending server is empty and cannot send bungeecord message to target server
							util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "BungeeCord Protocol: Unable to warn player '" + target + "'. Please join the " + server + " server and run the command again.");
						}
					} else {
						// Target is offline
						
						warnUtil.warnPlayer(target, sender.getName(), reason);
						util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Warned player '" + target + "'.");
					}
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /warn <player> <reason>");
			}
		}
		return true;
	}
}
