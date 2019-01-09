package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class OpticraftCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public Util util;

	public OpticraftCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("opticraft") || cmd.getName().equalsIgnoreCase("opti") || cmd.getName().equalsIgnoreCase("oc")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "opticraft", null);

				} else if (args.length == 1) {

					String guiName = args[0];
					boolean found = false;

					for (String gui : plugin.gui.keySet()) {
						if (guiName.toLowerCase().equalsIgnoreCase(gui)) {
							found = true;
							guiName = gui;
						}
					}

					if (found) {
						if (player.hasPermission("opticore.opticraft." + guiName)) {
							guiUtil.openGui(player, guiName, null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access this gui.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The gui '" + args[0] + "' does not exist.");
					}

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
