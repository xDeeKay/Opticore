package net.opticraft.opticore.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class DragonCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public DragonCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("dragon")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (args.length == 0) {
					
					new BukkitRunnable() {
						public void run() {

							new BukkitRunnable() {

								int count = 0;

								@Override
								public void run() {
									if (count >= 4 * 20) {
										cancel();
									}

									Location location = player.getLocation();
									ExperienceOrb exp = location.getWorld().spawn(location, ExperienceOrb.class);
									exp.setExperience((int) 36.5);

									count++;
								}

							}.runTaskTimer(plugin, 0L, 1L);

							// set egg atop the exit portal
							Block block = player.getWorld().getBlockAt(0, 65, 0);
							block.setType(Material.DRAGON_EGG);
							//event.getDrops().add(new ItemStack(Material.DRAGON_EGG));

						}
					}.runTaskLater(plugin, 10 * 20);

				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /dragon");
				}
			}
		}
		return true;
	}
}
