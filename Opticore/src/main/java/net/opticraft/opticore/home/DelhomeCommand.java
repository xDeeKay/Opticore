package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Methods;

public class DelhomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Methods methods;

	public GuiMethods guiMethods;
	public HomeMethods homeMethods;

	public DelhomeCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.guiMethods = this.plugin.guiMethods;
		this.homeMethods = this.plugin.homeMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delhome")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String home = args[0];

					if (homeMethods.homeExists(player.getName(), home)) {

						homeMethods.delHome(player, home);
						
						methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Deleted home '" + home + "'.");
						
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' does not exist.");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /delhome <home-name>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
