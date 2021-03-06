package net.opticraft.opticore.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class StaffCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;
	
	public Util util;

	public StaffCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staff") || cmd.getName().equalsIgnoreCase("mod") || cmd.getName().equalsIgnoreCase("punish")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "staff", null);

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /staff");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
