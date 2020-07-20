package net.opticraft.opticore.home;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class SethomeCommand implements CommandExecutor {

	public Main plugin;

	public HomeUtil homeUtil;

	public Util util;

	public SethomeCommand(Main plugin) {
		this.plugin = plugin;
		this.homeUtil = this.plugin.homeUtil;
		this.util = this.plugin.util;
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

							String material = null;

							if (item != null && (!item.getType().equals(Material.AIR))) {
								material = item.getType().toString().toLowerCase();
							}

							homeUtil.setHome(player, home, location, material, false, false);

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
