package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RanksCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public GuiUtil guiUtil;
	
	public Util util;

	public RanksCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ranks") || cmd.getName().equalsIgnoreCase("rank")) {
			
			if (args.length == 0) {
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					guiUtil.openGui(player, "ranks", null);
				} else {
					guiUtil.ranks(sender);
				}
				
			} else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("list")) {
					guiUtil.ranks(sender);
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /ranks list");
				}
				
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /ranks");
			}
		}
		return true;
	}
}
