package net.opticraft.opticore.home;

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

public class SethomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Util util;

	public GuiUtil guiUtil;
	public HomeUtil homeUtil;

	public SethomeCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
		this.homeUtil = this.plugin.homeUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sethome")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String home = args[0];
					
					if (plugin.players.get(player.getName()).getHomesRemaining() >= 1) {
						
						if (!homeUtil.homeExists(player.getName(), home)) {

							Location location = player.getLocation();
							
							ItemStack item = player.getInventory().getItemInMainHand();
							
							String itemMaterialId = null;
							
							if (item != null && (!item.getType().equals(Material.AIR))) {
								String itemMaterial = item.getType().toString().toLowerCase();
								int itemId = item.getDurability();
								
								itemMaterialId = itemMaterial + ":" + itemId;
							}
							
							homeUtil.setHome(player, home, location, itemMaterialId, false, false);

							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set home '" + home + "' in your current location.");
							
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' already exists.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no more homes remaining.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /sethome <home>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
