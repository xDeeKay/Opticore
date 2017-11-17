package net.opticraft.opticore.rewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;

public class RewardsCommand implements CommandExecutor {

	public Main plugin;

	public GuiMethods guiMethods;

	public RewardsCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rewards")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (args.length == 0) {
					guiMethods.openRewardsGui(player);
					
				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /rewards");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
