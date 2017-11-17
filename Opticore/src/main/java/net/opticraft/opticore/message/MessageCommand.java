package net.opticraft.opticore.message;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.world.WorldMethods;

public class MessageCommand implements CommandExecutor {

	public Main plugin;

	public WorldMethods worldMethods;

	public MessageCommand(Main plugin) {
		this.plugin = plugin;
		this.worldMethods = this.plugin.worldMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				String world = player.getLocation().getWorld().getName();

				if (args.length == 0) {

					if (!worldMethods.worldExists(world)) {

						Location location = player.getLocation();
						
						worldMethods.setSpawn(location);
						
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "Set spawn for '" + world + "' in your current location.");
						
					} else {
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "The world '" + world + "' does not exist.");
					}
				} else {
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "Incorrect syntax. Usage: /setspawn");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
