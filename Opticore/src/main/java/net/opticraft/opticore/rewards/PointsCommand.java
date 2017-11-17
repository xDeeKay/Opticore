package net.opticraft.opticore.rewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;

public class PointsCommand implements CommandExecutor {

	public Main plugin;

	public GuiMethods guiMethods;

	public PointsCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("points")) {
			
			//TODO add challenges content
		}
		return true;
	}
}
