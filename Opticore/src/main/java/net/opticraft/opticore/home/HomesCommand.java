package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Methods;

public class HomesCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	public Methods methods;

	public GuiMethods guiMethods;

	public HomeMethods homeMethods;

	public HomesCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.methods = this.plugin.methods;
		this.guiMethods = this.plugin.guiMethods;
		this.homeMethods = this.plugin.homeMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("homes")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				if (args.length == 0) {
					// Own homes
					guiMethods.openHomesGui(player, player.getName());

				} else if (args.length == 1) {
					// Other homes
					String target = args[0];
					guiMethods.openHomesGui(player, target);

				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /homes or /homes <player>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
