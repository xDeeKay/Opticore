package net.opticraft.opticore.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;

public class WorldCommand implements CommandExecutor {

	public Main plugin;

	public GuiMethods guiMethods;
	public WorldMethods worldMethods;

	public WorldCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
		this.worldMethods = this.plugin.worldMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("j") || cmd.getName().equalsIgnoreCase("worlds") || cmd.getName().equalsIgnoreCase("join") || cmd.getName().equalsIgnoreCase("world")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (args.length == 0) {
					guiMethods.openWorldsGui(player);
					
				} else if (args.length == 1) {
					
					String world = args[0];
					
					if (worldMethods.worldExists(world)) {
						
						if (player.hasPermission("opticore.world.join." + plugin.worlds.get(world).getPermission())) {
							worldMethods.teleportPlayerToWorld(player, world);
							
						} else {
							player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
									ChatColor.GOLD + "You do not have permission to access the '" + world + "' world.");
						}
					} else {
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "The world '" + world + "' does not exist.");
					}
				} else {
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "Incorrect syntax. Usage: /world <world-name>");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
