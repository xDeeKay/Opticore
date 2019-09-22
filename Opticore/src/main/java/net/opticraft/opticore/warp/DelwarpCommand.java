package net.opticraft.opticore.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class DelwarpCommand implements CommandExecutor {

	public Main plugin;

	public WarpUtil warpUtil;
	
	public Util util;

	public DelwarpCommand(Main plugin) {
		this.plugin = plugin;
		this.warpUtil = this.plugin.warpUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String warp = args[0];

					if (warpUtil.warpExists(warp)) {

						warpUtil.delWarp(warp);
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Deleted warp '" + warp + "'.");

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The warp '" + warp + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /delwarp <warp>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
