package net.opticraft.opticore.staff.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class StaffchatCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public StaffchatCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staffchat") || cmd.getName().equalsIgnoreCase("sc")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (args.length == 0) {

					if (plugin.staffchat.contains(player.getName())) {
						plugin.staffchat.remove(player.getName());
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "You are no longer speaking in staff chat.");
					} else {
						plugin.staffchat.add(player.getName());
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "You are now speaking in staff chat.");
					}
					
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /staffchat");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
