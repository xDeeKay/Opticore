package net.opticraft.opticore.elytra;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class ElytraCommand implements CommandExecutor {

	public Main plugin;

	public Config config;

	public Util util;

	public ElytraCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("elytra")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (args.length == 0) {

					if (plugin.elytra.contains(player.getName())) {
						plugin.elytra.remove(player.getName());
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Toggled /elytra off.");
					} else {
						plugin.elytra.add(player.getName());
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Toggled /elytra on.");
						
						if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
							ItemStack elytra = new ItemStack(Material.ELYTRA, 1);
							player.getInventory().setChestplate(elytra);
						}
					}

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /elytra");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
