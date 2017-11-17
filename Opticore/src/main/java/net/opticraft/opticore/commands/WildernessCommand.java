package net.opticraft.opticore.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class WildernessCommand implements CommandExecutor {

	public Main plugin;

	public WildernessCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wilderness") || cmd.getName().equalsIgnoreCase("rtp")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					
					Random random = new Random();
					
					int min = -4000;
					int max = 4000;

					World world = player.getLocation().getWorld();
					int x = random.nextInt(max + 1 - min) + min;
					int z = random.nextInt(max + 1 - min) + min;
					int y = world.getHighestBlockYAt(x, z);
					float yaw = (float) 0.0;
					float pitch = (float) 0.0;
					
					Location location = new Location(world, x, y, z, yaw, pitch);
					
					player.teleport(location);
					 
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "You have been teleported to a random location.");

				} else {
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "Incorrect syntax. Usage: /wilderness");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
