package net.opticraft.opticore.home;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Methods;

public class SethomeCommand implements CommandExecutor {

	public Main plugin;
	
	public Methods methods;

	public GuiMethods guiMethods;
	public HomeMethods homeMethods;

	public SethomeCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.guiMethods = this.plugin.guiMethods;
		this.homeMethods = this.plugin.homeMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sethome")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String home = args[0];
					
					if (plugin.players.get(player.getName()).getHomesRemaining() >= 1) {
						
						if (!homeMethods.homeExists(player.getName(), home)) {

							Location location = player.getLocation();
							
							ItemStack item = player.getInventory().getItemInMainHand();
							
							String itemMaterialId = null;
							
							if (item != null && (!item.getType().equals(Material.AIR))) {
								String itemMaterial = item.getType().toString().toLowerCase();
								int itemId = item.getDurability();
								
								itemMaterialId = itemMaterial + ":" + itemId;
							}
							
							homeMethods.setHome(player, home, location, itemMaterialId, false);

							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set home '" + home + "' in your current location.");
							
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' already exists.");
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no more homes remaining.");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /sethome <home-name>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
