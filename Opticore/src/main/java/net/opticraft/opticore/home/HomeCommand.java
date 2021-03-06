package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class HomeCommand implements CommandExecutor {

	public Main plugin;

	public HomeUtil homeUtil;

	public Util util;

	public HomeCommand(Main plugin) {
		this.plugin = plugin;
		this.homeUtil = this.plugin.homeUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("home") || cmd.getName().equalsIgnoreCase("hoem")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					if (homeUtil.homeExists(player.getName(), "home")) {
						player.teleport(homeUtil.getHomeLocation(player.getName(), "home"));
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home 'home' of '" + player.getName() + "'.");
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home 'home' of '" + player.getName() + "' does not exist.");
					}

				} else if (args.length == 1) {

					// Own home
					String home = args[0];

					if (homeUtil.homeExists(home, "home")) {
						if (!homeUtil.getLock(home, "home") || player.getName().equalsIgnoreCase(home) || player.hasPermission("opticore.lockhome.bypass")) {
							player.teleport(homeUtil.getHomeLocation(home, "home"));
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home 'home' of '" + home + "'.");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home 'home' of '" + home + "' is locked.");
						}
					} else if (homeUtil.homeExists(player.getName(), home)) {
						player.teleport(homeUtil.getHomeLocation(player.getName(), home));
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home '" + home + "' of '" + player.getName() + "'.");
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + player.getName() + "' does not exist.");
					}

				} else if (args.length == 2) {

					// Other home
					String target = args[0];
					String home = args[1];

					if (homeUtil.homeExists(target, home)) {
						if (!homeUtil.getLock(target, home) || player.getName().equalsIgnoreCase(target) || player.hasPermission("opticore.lockhome.bypass")) {
							player.teleport(homeUtil.getHomeLocation(target, home));
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home '" + home + "' of '" + target + "'.");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + target + "' is locked.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + target + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /home <home> or /home <player> <home>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
