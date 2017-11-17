package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class JailCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;

	public JailCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("jail")) {
			
		}
		return true;
	}
}
