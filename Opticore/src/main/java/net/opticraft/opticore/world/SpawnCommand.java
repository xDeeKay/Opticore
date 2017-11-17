package net.opticraft.opticore.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class SpawnCommand implements CommandExecutor {

	public Main plugin;

	public WorldMethods worldMethods;

	public SpawnCommand(Main plugin) {
		this.plugin = plugin;
		this.worldMethods = this.plugin.worldMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				String world = player.getLocation().getWorld().getName();

				if (args.length == 0) {

					if (worldMethods.worldExists(world)) {
						
						worldMethods.teleportPlayerToWorld(player, world);

					} else {
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "The world '" + world + "' does not exist.");
					}
				} else {
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "Incorrect syntax. Usage: /spawn");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
