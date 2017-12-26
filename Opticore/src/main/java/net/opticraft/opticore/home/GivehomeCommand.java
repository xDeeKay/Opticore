package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.MySQL;

public class GivehomeCommand implements CommandExecutor {

	public Main plugin;
	
	public MySQL mysql;

	public Util util;

	public GivehomeCommand(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("givehome")) {

			if (args.length == 2) {

				String target = args[0];
				
				if (util.isInt(args[1])) {
					
					int amount = Integer.parseInt(args[1]);
					
					int homesRemaining = mysql.getUsersColumnValue(target, "homes_remaining");
					
					if (plugin.getServer().getPlayer(target) != null && plugin.players.containsKey(target)) {
						// Update homes remaining for player class
						plugin.players.get(target).setHomesRemaining(homesRemaining + amount);
					}

					// Update homes remaining for player database column
					plugin.mysql.setUsersColumnValue(target, "homes_remaining", homesRemaining + amount);
					
					util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Gave " + amount + " set homes to player '" + target + "'.");
					
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /givehome <target> <amount>");
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid number for <amount>.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /givehome <target> <amount>");
			}
		}
		return true;
	}
}
