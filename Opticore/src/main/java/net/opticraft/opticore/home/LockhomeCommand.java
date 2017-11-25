package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Methods;

public class LockhomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Methods methods;

	public HomeMethods homeMethods;

	public LockhomeCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.homeMethods = this.plugin.homeMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lockhome")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String home = args[0];

					if (homeMethods.homeExists(player.getName(), home)) {
						
						if (plugin.players.get(player.getName()).getHomes().get(home).getLocked()) {
							homeMethods.setLock(player, home, false);
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Unlocked home '" + home + "'.");
						} else {
							homeMethods.setLock(player, home, true);
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Locked home '" + home + "'.");
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' does not exist.");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /lockhome <home-name>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
