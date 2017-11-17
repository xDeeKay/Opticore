package net.opticraft.opticore.rewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;

public class VoteCommand implements CommandExecutor {

	public Main plugin;

	public GuiMethods guiMethods;

	public VoteCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vote")) {
			
			sender.sendMessage(ChatColor.GOLD + "Visit www.opticraft.net/vote and enter your username upon voting.");
		}
		return true;
	}
}
