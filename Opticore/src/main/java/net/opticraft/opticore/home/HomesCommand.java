package net.opticraft.opticore.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class HomesCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	public Util util;

	public GuiUtil guiUtil;

	public HomeUtil homeUtil;

	public HomesCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
		this.homeUtil = this.plugin.homeUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("homes")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				if (args.length == 0) {
					// Own homes
					guiUtil.openHomesGui(player, player.getName());

				} else if (args.length == 1) {
					// Other homes
					String target = args[0];
					guiUtil.openHomesGui(player, target);

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /homes or /homes <player>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
