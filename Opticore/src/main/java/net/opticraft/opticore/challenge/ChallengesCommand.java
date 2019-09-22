package net.opticraft.opticore.challenge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class ChallengesCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;
	
	public Util util;

	public ChallengesCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("challenges") || cmd.getName().equalsIgnoreCase("challenge")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "challenges", null);

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /challenges");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
