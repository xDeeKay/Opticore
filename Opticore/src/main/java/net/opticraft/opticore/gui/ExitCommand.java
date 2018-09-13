package net.opticraft.opticore.gui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;

public class ExitCommand implements CommandExecutor {

	public Main plugin;

	public ExitCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("exit")) {
			
			if (sender instanceof Player) {
				
				Player player = (Player) sender;

				if (args.length == 0) {
					player.closeInventory();
				}
			}
		}
		return true;
	}
}
