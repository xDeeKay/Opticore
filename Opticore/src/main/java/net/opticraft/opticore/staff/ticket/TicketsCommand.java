package net.opticraft.opticore.staff.ticket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class TicketsCommand implements CommandExecutor {

	public Main plugin;

	public TicketsCommand(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tickets") || cmd.getName().equalsIgnoreCase("ticketlist")) {
			if (args.length == 0) {
				

			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /notes <playe>");
			}
		}
		return true;
	}
}
