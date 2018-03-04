package net.opticraft.opticore.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RulesCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public Util util;

	public RulesCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rules")) {
			if (args.length == 0) {
				
				List<String> rules = config.getRules();
				for (String rule : rules) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rule));
				}

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /rules");
			}
		}
		return true;
	}
}
