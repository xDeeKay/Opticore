package net.opticraft.opticore.rewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class DisguiseCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public GuiUtil guiUtil;

	public RewardUtil rewardUtil;

	public DisguiseCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
		this.rewardUtil = this.plugin.rewardUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("disguise") || cmd.getName().equalsIgnoreCase("d")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "disguise", null);

				} else if (args.length == 1) {

					//String mob = args[0];
					
				} else {
					util.sendStyledMessage(player, null, "RED", "R", "GOLD", "Incorrect syntax. Usage: /disguise <mob>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
