package net.opticraft.opticore.warp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;

public class SetwarpCommand implements CommandExecutor {

	public Main plugin;

	public GuiMethods guiMethods;
	public WarpMethods warpMethods;

	public SetwarpCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
		this.warpMethods = this.plugin.warpMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setwarp")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String warp = args[0];

					if (!warpMethods.warpExists(warp)) {

						Location location = player.getLocation();
						
						ItemStack item = player.getInventory().getItemInMainHand();
						
						String itemMaterialId = null;
						
						if (item != null && (!item.getType().equals(Material.AIR))) {
							String itemMaterial = item.getType().toString().toLowerCase();
							int itemId = item.getDurability();
							
							itemMaterialId = itemMaterial + ":" + itemId;
						}
						
						warpMethods.setWarp(warp, location, itemMaterialId);

						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "Set warp '" + warp + "' in your current location.");
						
					} else {
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "The warp '" + warp + "' already exists.");
					}
				} else {
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "Incorrect syntax. Usage: /setwarp <warp-name>");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
