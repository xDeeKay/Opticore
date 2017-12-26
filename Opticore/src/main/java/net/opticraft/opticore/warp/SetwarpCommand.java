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
import net.opticraft.opticore.gui.GuiUtil;

public class SetwarpCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;
	public WarpUtil warpUtil;

	public SetwarpCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.warpUtil = this.plugin.warpUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setwarp")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String warp = args[0];

					if (!warpUtil.warpExists(warp)) {

						Location location = player.getLocation();
						
						ItemStack item = player.getInventory().getItemInMainHand();
						
						String itemMaterialId = null;
						
						if (item != null && (!item.getType().equals(Material.AIR))) {
							String itemMaterial = item.getType().toString().toLowerCase();
							int itemId = item.getDurability();
							
							itemMaterialId = itemMaterial + ":" + itemId;
						}
						
						warpUtil.setWarp(warp, location, itemMaterialId);

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
