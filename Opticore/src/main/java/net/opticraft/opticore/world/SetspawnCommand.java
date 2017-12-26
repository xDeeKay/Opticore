package net.opticraft.opticore.world;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;

public class SetspawnCommand implements CommandExecutor {

	public Main plugin;

	public WorldUtil worldUtil;

	public SetspawnCommand(Main plugin) {
		this.plugin = plugin;
		this.worldUtil = this.plugin.worldUtil;
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
						
						worldUtil.setSpawn(location);
						
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
