package net.opticraft.opticore.util.wither;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class WitherCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public WitherCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wither")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				String playerUUID = player.getUniqueId().toString();

				if (args.length == 0) {
					
					if (plugin.wither.contains(playerUUID)) {
						plugin.wither.remove(playerUUID);
						util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Toggled /wither off.");
					} else {
						plugin.wither.add(playerUUID);
						util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Toggled /wither on.");
					}

				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /wither.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
