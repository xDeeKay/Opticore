package net.opticraft.opticore.teleport;

import java.util.Random;

import org.bukkit.Location;
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
						
						//System.out.println("ground:" + ground.getType().toString().toLowerCase());
						//System.out.println("feet:" + feet.getType().toString().toLowerCase());
						
						boolean blacklisted = false;
						for (String blacklistedBlock : config.getTeleportRandomtpBlacklist()) {
							
							//System.out.println("blacklistedBlock:" + blacklistedBlock);
							
							if (ground.getType().toString().toLowerCase().equalsIgnoreCase(blacklistedBlock) || feet.getType().toString().toLowerCase().equalsIgnoreCase(blacklistedBlock)) {
								
								blacklisted = true;
								
								//System.out.println("blacklisted:" + blacklisted);
							}
						}

						if (blacklisted == false) {
							
							//System.out.println("blacklisted is false, location is safe");
							
							safe = true;

							Location center = new Location(world, location.getBlockX() + 0.5D, location.getBlockY() + 1, location.getBlockZ() + 0.5D, location.getYaw(), location.getPitch());

							player.teleport(center);

							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "You have been teleported to a random location.");
							
						} else {
							//System.out.println("blacklisted is true, location is unsafe");
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
