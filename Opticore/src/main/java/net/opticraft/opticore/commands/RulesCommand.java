package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RulesCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public GuiUtil guiUtil;
	
	public Util util;

	public RulesCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rules") || cmd.getName().equalsIgnoreCase("rule")) {
			
			if (args.length == 0) {
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					guiUtil.openGui(player, "rules", null);
				} else {
					guiUtil.rules(sender);
				}
				
			} else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("list")) {
					guiUtil.rules(sender);
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /rules list");
				}
				
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /rules");
			}
		}
		return true;
	}
}
