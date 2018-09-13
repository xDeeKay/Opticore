package net.opticraft.opticore.home;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class MovehomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Util util;

	public HomeUtil homeUtil;

	public MovehomeCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.homeUtil = this.plugin.homeUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("movehome")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String home = args[0];

					if (homeUtil.homeExists(player.getName(), home)) {
						
						Location location = player.getLocation();
						
						homeUtil.moveHome(player, home, location);

						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Moved home '" + home + "' to your current location.");
						
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /movehome <home>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
