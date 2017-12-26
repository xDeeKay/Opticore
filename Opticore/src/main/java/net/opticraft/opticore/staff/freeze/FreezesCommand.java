package net.opticraft.opticore.staff.freeze;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class FreezesCommand implements CommandExecutor {

	public Main plugin;

	public FreezesCommand(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("freezes") || cmd.getName().equalsIgnoreCase("freezelist")) {
			if (args.length == 0) {
				

			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /freezes <playe>");
			}
		}
		return true;
	}
}
