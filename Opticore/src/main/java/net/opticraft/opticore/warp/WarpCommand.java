package net.opticraft.opticore.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class WarpCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public WarpUtil warpUtil;

	public Util util;

	public WarpCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.warpUtil = this.plugin.warpUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp") || cmd.getName().equalsIgnoreCase("warps")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "warps", null);

				} else if (args.length == 1) {

					String warp = args[0];

					if (warpUtil.warpExists(warp)) {

						if (player.hasPermission("opticore.warp." + warp.toLowerCase())) {

							player.teleport(warpUtil.getWarpLocation(warp));

							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to warp '" + warp + "'.");

						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access warp '" + warp + "'.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The warp '" + warp + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /delwarp <warp-name>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
