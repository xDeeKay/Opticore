package net.opticraft.opticore.staff.freeze;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class FreezeCommand implements CommandExecutor {

	public Main plugin;

	public FreezeUtil freezeUtil;

	public BungeecordUtil bungeecordUtil;

	public Util util;

	public ServerUtil serverUtil;

	public FreezeCommand(Main plugin) {
		this.plugin = plugin;
		this.freezeUtil = this.plugin.freezeUtil;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.util = this.plugin.util;
		this.serverUtil = this.plugin.serverUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("freeze")) {

			if (args.length >= 3) {
				
				String target = args[0];
				String lengthString = args[1];
				long length;
				String reason = StringUtils.join(args, ' ', 2, args.length);

				if (freezeUtil.getActiveFreeze(target) == null) {

					if (util.isPermanent(lengthString)) {
						length = 0;
					} else if (util.isValidTimeString(lengthString)) {
						length = util.parse(lengthString);
					} else {
						util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /freeze <player> <time> <reason>");
						util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid time type for <time>.");
						util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "For example: 7d12h or permanent");
						return true;
					}

					if (plugin.getServer().getPlayer(target) != null) {
						// Target is online
						
						Player targetPlayer = plugin.getServer().getPlayer(target);
						
						freezeUtil.freezePlayer(targetPlayer.getName(), sender.getName(), length, reason);
						util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Froze player '" + targetPlayer.getName() + "'.");

					} else {

						String server = serverUtil.getPlayerServerName(target);

						if (server != null) {
							// Target is on another server
							
							if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
								// BungeeCord limitation handler: sending server is not empty and can send bungeecord message to target server
								bungeecordUtil.sendFreezeCommand(server, target, sender.getName(), String.valueOf(length), reason);
								util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Froze player '" + target + "'.");
								
							} else {
								// BungeeCord limitation handler: sending server is empty and cannot send bungeecord message to target server
								util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "BungeeCord Protocol: Unable to freeze player '" + target + "'. Please join the " + server + " server and run the command again.");
							}
						} else {
							// Target is offline
							
							freezeUtil.freezePlayer(target, sender.getName(), length, reason);
							util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Froze player '" + target + "'.");
						}
					}
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "The player '" + target + "' is already frozen.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /freeze <player> <time> <reason>");
			}
		}
		return true;
	}
}
