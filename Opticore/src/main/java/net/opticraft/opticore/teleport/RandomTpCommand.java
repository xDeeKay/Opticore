package net.opticraft.opticore.teleport;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RandomTpCommand implements CommandExecutor {

	public Main plugin;

	public Config config;

	public Util util;

	public RandomTpCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("randomtp") || cmd.getName().equalsIgnoreCase("rtp") || cmd.getName().equalsIgnoreCase("wilderness")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {

					boolean safe = false;

					while (safe == false) {

						Random random = new Random();

						int radius = config.getTeleportRandomtpRadius();

						int min = -radius;
						int max = radius;

						World world = player.getLocation().getWorld();
						int x = random.nextInt(max + 1 - min) + min;
						int z = random.nextInt(max + 1 - min) + min;
						int y = world.getHighestBlockYAt(x, z);
						float yaw = random.nextInt((180 - -90) + 1) + -90;
						float pitch = 0;

						Location location = new Location(world, x, y, z, yaw, pitch);
						Block ground = world.getBlockAt(location);
						Block feet = ground.getRelative(BlockFace.UP);

						if (!ground.getType().equals(Material.WATER) && !ground.getType().equals(Material.LAVA) && 
								!ground.getType().equals(Material.CACTUS) && !ground.getType().equals(Material.MAGMA_BLOCK) && 
								!ground.getType().equals(Material.CAMPFIRE) && !ground.getType().equals(Material.SOUL_CAMPFIRE) && 
								!feet.getType().equals(Material.SWEET_BERRY_BUSH) && !feet.getType().equals(Material.WITHER_ROSE) &&
								!feet.getType().equals(Material.FIRE) && !feet.getType().equals(Material.SOUL_FIRE)) {
							
								safe = true;

								Location center = new Location(world, location.getBlockX() + 0.5D, location.getBlockY() + 1, location.getBlockZ() + 0.5D, location.getYaw(), location.getPitch());

								player.teleport(center);

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
}
