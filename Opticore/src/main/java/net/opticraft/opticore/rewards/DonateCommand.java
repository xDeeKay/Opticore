package net.opticraft.opticore.rewards;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;

public class DonateCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public DonateCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("donate")) {
			
			sender.sendMessage(ChatColor.GOLD + "Visit www.opticraft.net/donate and enter your username upon donating.");
		}
		return true;
	}
}
