package net.opticraft.opticore.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;

public class RulesCommand implements CommandExecutor {

	public Main plugin;

	public Config config;

	public RulesCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rules")) {
			if (args.length == 0) {
				
				List<String> rules = config.getRules();
				for (String rule : rules) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rule));
				}

			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /rules");
			}
		}
		return true;
	}
}
