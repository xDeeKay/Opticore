package net.opticraft.opticore.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class WildernessCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public Util util;

	public WildernessCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wilderness") || cmd.getName().equalsIgnoreCase("randomtp") || cmd.getName().equalsIgnoreCase("rtp")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {

					boolean safe = false;

					while (safe == false) {

						Random random = new Random();

						int radius = config.getTeleportWildernessRadius();

						int min = -radius;
						int max = radius;

						World world = player.getLocation().getWorld();
						int x = random.nextInt(max + 1 - min) + min;
						int z = random.nextInt(max + 1 - min) + min;
						int y = world.getHighestBlockYAt(x, z);
						float yaw = random.nextInt((180 - -90) + 1) + -90;
						float pitch = 0;

						Location location = new Location(world, x, y, z, yaw, pitch);

						Block block1 = world.getBlockAt(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
						Block block2 = world.getBlockAt(location);

						if (!block1.getType().equals(Material.STATIONARY_WATER) && !block1.getType().equals(Material.WATER) && 
								!block1.getType().equals(Material.STATIONARY_LAVA) && !block1.getType().equals(Material.LAVA) && 
								!block1.getType().equals(Material.CACTUS) && !block1.getType().equals(Material.AIR) && 
								!block2.getType().equals(Material.STATIONARY_LAVA) && !block2.getType().equals(Material.LAVA)) {

							safe = true;

							player.teleport(location);

							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "You have been teleported to a random location.");

						} else {
							safe = false;
						}
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /wilderness");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}

	public Location getCenterOfBlock(Location location) {
		return new Location(location.getWorld(), getRelativeCoord(location.getBlockX()), getRelativeCoord(location.getBlockY()), getRelativeCoord(location.getBlockZ()));
	}
	
	private double getRelativeCoord(int i) {
		double d = i;
		d = d < 0 ? d - .5 : d + .5;
		return d;
	}
}
