package net.opticraft.opticore.staff.mute;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class UnmuteCommand implements CommandExecutor {

	public Main plugin;

	public UnmuteCommand(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("unmute")) {
			if (args.length == 0) {
				

			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /unmute <playe> <reason>");
			}
		}
		return true;
	}
}
