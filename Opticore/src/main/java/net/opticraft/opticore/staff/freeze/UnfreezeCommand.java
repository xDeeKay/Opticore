package net.opticraft.opticore.staff.freeze;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class UnfreezeCommand implements CommandExecutor {

	public Main plugin;

	public UnfreezeCommand(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("unfreeze")) {
			if (args.length == 0) {
				

			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /unfreeze <playe> <reason>");
			}
		}
		return true;
	}
}
