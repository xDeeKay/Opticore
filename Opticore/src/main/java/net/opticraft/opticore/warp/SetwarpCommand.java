package net.opticraft.opticore.warp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class SetwarpCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;
	public WarpUtil warpUtil;
	
	public Util util;

	public SetwarpCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.warpUtil = this.plugin.warpUtil;
		this.util = this.plugin.util;
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
						
						String material = null;
						
						if (item != null && (!item.getType().equals(Material.AIR))) {
							material = item.getType().toString().toLowerCase();
						}
						
						warpUtil.setWarp(warp, location, material);
						
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set warp '" + warp + "' in your current location.");

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The warp '" + warp + "' already exists.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /setwarp <warp>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
