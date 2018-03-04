package net.opticraft.opticore.world;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class SetspawnCommand implements CommandExecutor {

	public Main plugin;

	public WorldUtil worldUtil;
	
	public Util util;

	public SetspawnCommand(Main plugin) {
		this.plugin = plugin;
		this.worldUtil = this.plugin.worldUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				String world = plugin.players.get(player.getName()).getWorld();

				if (args.length == 0) {

					if (worldUtil.worldExists(world)) {

						Location location = player.getLocation();
						
						worldUtil.setSpawn(world, location);
						
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set spawn for '" + world + "' in your current location.");
						
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The world '" + world + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /setspawn");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
