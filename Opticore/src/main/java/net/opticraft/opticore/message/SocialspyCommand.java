package net.opticraft.opticore.message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class SocialspyCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public SocialspyCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("socialspy")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {

					/*
					if (plugin.socialspy.contains(player.getName())) {
						plugin.socialspy.remove(player.getName());
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Toggled /socialspy off.");
					} else {
						plugin.socialspy.add(player.getName());
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Toggled /socialspy on.");
					}
					 */

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /socialspy");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}