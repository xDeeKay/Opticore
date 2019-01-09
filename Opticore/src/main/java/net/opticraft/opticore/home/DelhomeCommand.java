package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class DelhomeCommand implements CommandExecutor {

	public Main plugin;
	
	public HomeUtil homeUtil;
	
	public Util util;

	public DelhomeCommand(Main plugin) {
		this.plugin = plugin;
		this.homeUtil = this.plugin.homeUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delhome")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String home = args[0];

					if (homeUtil.homeExists(player.getName(), home)) {

						homeUtil.delHome(player, home);
						
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Deleted home '" + home + "'.");
						
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /delhome <home>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
