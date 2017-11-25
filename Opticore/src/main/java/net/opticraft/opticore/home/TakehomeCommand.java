package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.MySQL;

public class TakehomeCommand implements CommandExecutor {

	public Main plugin;
	
	public MySQL mysql;
	
	public Methods methods;

	public TakehomeCommand(Main plugin) {
		this.plugin = plugin;
		this.mysql = this.plugin.mysql;
		this.methods = this.plugin.methods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("takehome")) {

			if (args.length == 2) {

				String target = args[0];
				String amount = args[1];
				
				if (methods.isInt(amount)) {
					
					int homesRemaining = mysql.getUsersColumnValue(target, "homes_remaining");
					
					if (plugin.getServer().getPlayer(target) != null && plugin.players.containsKey(target)) {
						// Update homes remaining for player class
						plugin.players.get(target).setHomesRemaining(homesRemaining - 1);
					}

					// Update homes remaining for player database column
					plugin.mysql.setUsersColumnValue(target, "homes_remaining", homesRemaining - 1);
					
					methods.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Took " + amount + " set homes from player '" + target + "'.");
					
				} else {
					methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /takehome <target> <amount>");
					methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid number for <amount>.");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /takehome <target> <amount>");
			}
		}
		return true;
	}
}
