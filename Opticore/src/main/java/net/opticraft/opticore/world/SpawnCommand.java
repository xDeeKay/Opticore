package net.opticraft.opticore.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class SpawnCommand implements CommandExecutor {

	public Main plugin;

	public WorldUtil worldUtil;
	
	public Util util;

	public SpawnCommand(Main plugin) {
		this.plugin = plugin;
		this.worldUtil = this.plugin.worldUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				String world = worldUtil.resolveWorld(player.getLocation().getWorld().getName());

				if (args.length == 0) {

					if (world != null && worldUtil.worldExists(world)) {
						
						player.teleport(worldUtil.getWorldLocation(world));
						
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to world '" + world + "'.");

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The world '" + world + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /spawn");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
