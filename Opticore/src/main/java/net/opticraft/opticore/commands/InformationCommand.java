package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class InformationCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public Util util;

	public InformationCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("information") || cmd.getName().equalsIgnoreCase("info")) {

			if (args.length == 0) {
				guiUtil.sendListAsMessage(sender, plugin.config.getInformation());

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /information");
			}
		}
		return true;
	}
}
