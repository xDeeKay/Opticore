package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Methods;

public class HomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Config config;
	public Methods methods;

	public GuiMethods guiMethods;
	
	public HomeMethods homeMethods;

	public HomeCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.methods = this.plugin.methods;
		this.guiMethods = this.plugin.guiMethods;
		this.homeMethods = this.plugin.homeMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("home")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				if (args.length == 0) {
					guiMethods.openHomesGui(player, player.getName());

				} else if (args.length == 1) {
					
					// Own home
					String home = args[0];

					if (homeMethods.homeExists(player.getName(), home)) {
						if (!homeMethods.getLock(player.getName(), home) || player.getName().equalsIgnoreCase(player.getName()) || player.hasPermission("opticore.lockhome.bypass")) {
							homeMethods.teleportPlayerToHome(player, player.getName(), home);
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home '" + home + "' of '" + player.getName() + "'.");
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + player.getName() + "' is locked.");
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + player.getName() + "' does not exist.");
					}
					
				} else if (args.length == 2) {

					// Other home
					String target = args[0];
					String home = args[1];
					
					if (homeMethods.homeExists(target, home)) {
						if (!homeMethods.getLock(target, home) || player.getName().equalsIgnoreCase(target) || player.hasPermission("opticore.lockhome.bypass")) {
							homeMethods.teleportPlayerToHome(player, target, home);
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home '" + home + "' of '" + target + "'.");
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + target + "' is locked.");
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + target + "' does not exist.");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /home <home-name> or /home <player> <home-name>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
