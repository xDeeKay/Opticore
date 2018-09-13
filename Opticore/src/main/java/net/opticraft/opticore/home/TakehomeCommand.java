package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class TakehomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Util util;
	
	public HomeUtil homeUtil;

	public TakehomeCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.homeUtil = this.plugin.homeUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("takehome")) {

			if (args.length == 2) {

				String target = args[0];
				
				if (util.isInt(args[1])) {
					
					int amount = Integer.parseInt(args[1]);
					
					int homesRemaining = homeUtil.getHomesAmount(target);
					
					// Update homes remaining for player
					homeUtil.setHomesAmount(target, homesRemaining - amount);
					
					util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Took " + amount + " set homes from player '" + target + "'.");
					
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /takehome <player> <amount>");
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid number for <amount>.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /takehome <player> <amount>");
			}
		}
		return true;
	}
}
