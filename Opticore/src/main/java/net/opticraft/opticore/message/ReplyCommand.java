package net.opticraft.opticore.message;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;

public class ReplyCommand implements CommandExecutor {

	public Main plugin;

	public Config config;

	public ReplyCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reply") || cmd.getName().equalsIgnoreCase("r")) {
			if (args.length == 0) {
				
				List<String> ranks = config.getRanks();
				for (String rank : ranks) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rank));
				}

			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /ranks");
			}
		}
		return true;
	}
}