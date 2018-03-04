package net.opticraft.opticore.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RanksCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public Util util;

	public RanksCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ranks")) {
			if (args.length == 0) {
				
				List<String> ranks = config.getRanks();
				for (String rank : ranks) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rank));
				}

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /ranks");
			}
		}
		return true;
	}
}
