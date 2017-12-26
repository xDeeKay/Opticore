package net.opticraft.opticore.staff.kick;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class KicksCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public GuiUtil guiUtil;

	public KicksCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kicks") || cmd.getName().equalsIgnoreCase("kickrecent")) {

			if (args.length == 1) {

				if (util.isInt(args[0])) {

					//int recent = Integer.parseInt(args[0]);

					//guiUtil.openKickRecentGui(player, recent);
					
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "This command does nothing right now.");

				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /kicks <amount>");
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid number for <amount>.");
				}

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /kicks <amount>");
			}
		}
		return true;
	}
}
