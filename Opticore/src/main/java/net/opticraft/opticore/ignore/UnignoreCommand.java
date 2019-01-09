package net.opticraft.opticore.ignore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class UnignoreCommand implements CommandExecutor {

	public Main plugin;
	
	public IgnoreUtil ignoreUtil;

	public Util util;

	public UnignoreCommand(Main plugin) {
		this.plugin = plugin;
		this.ignoreUtil = this.plugin.ignoreUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("unignore")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {
					
					String target = args[0];
					
					ignoreUtil.unignore(player, target);

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /unignore <player>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
