package net.opticraft.opticore.warp;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.World;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class WarpUtil {

	public Main plugin;

	public Config config;
	public BungeecordUtil bungeecordUtil;

	public WarpUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("warps")) {

			Set<String> warps = plugin.getConfig().getConfigurationSection("warps").getKeys(false);

			if (!warps.isEmpty()) {
				for (String warp : warps) {

					String material = plugin.getConfig().getString("warps." + warp + ".material");

					String world = plugin.getConfig().getString("warps." + warp + ".location.world");
					double x = plugin.getConfig().getDouble("warps." + warp + ".location.x");
					double y = plugin.getConfig().getDouble("warps." + warp + ".location.y");
					double z = plugin.getConfig().getDouble("warps." + warp + ".location.z");
					double yaw = plugin.getConfig().getDouble("warps." + warp + ".location.yaw");
					double pitch = plugin.getConfig().getDouble("warps." + warp + ".location.pitch");

					plugin.warps.put(warp, new Warp(material, world, x, y, z, yaw, pitch));
				}
			}
		}
	}

	public boolean warpExists(String warp) {

		return plugin.warps.containsKey(warp);
	}

	public void setWarp(String warp, Location location, String material) {

		if (material == null) {
			material = plugin.gui.get("opticraft").getSlots().get("warps").getMaterial();
		}

		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();

		plugin.getConfig().set("warps." + warp, "");
		plugin.getConfig().set("warps." + warp + ".material", material);
		plugin.getConfig().set("warps." + warp + ".location.world", world);
		plugin.getConfig().set("warps." + warp + ".location.x", x);
		plugin.getConfig().set("warps." + warp + ".location.y", y);
		plugin.getConfig().set("warps." + warp + ".location.z", z);
		plugin.getConfig().set("warps." + warp + ".location.yaw", yaw);
		plugin.getConfig().set("warps." + warp + ".location.pitch", pitch);
		plugin.saveConfig();

		plugin.warps.put(warp, new Warp(material, world, x, y, z, yaw, pitch));
	}

	public void delWarp(String warp) {

		Set<String> warps = plugin.getConfig().getConfigurationSection("warps").getKeys(false);

		for (String key : warps) {
			if (key.equalsIgnoreCase(warp)) {
				warp = key;
			}
		}

		plugin.getConfig().set("warps." + warp, null);
		plugin.saveConfig();

		plugin.warps.remove(warp);
	}

	public void teleportPlayerToWarp(Player player, String warp) {

		World world = plugin.getServer().getWorld(plugin.warps.get(warp).getWorld());
		double x = plugin.warps.get(warp).getX();
		double y = plugin.warps.get(warp).getY();
		double z = plugin.warps.get(warp).getZ();
		float yaw = (float) plugin.warps.get(warp).getYaw();
		float pitch = (float) plugin.warps.get(warp).getPitch();

		Location location = new Location(world, x, y, z, yaw, pitch);

		player.teleport(location);
	}
}
