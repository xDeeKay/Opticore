package net.opticraft.opticore.world;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.World;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class WorldUtil {

	public Main plugin;

	public Config config;
	public BungeecordUtil bungeecordUtil;

	public WorldUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}
	
	public String resolveWorld(String worldName) {
		
		String world = null;
		
		for (Map.Entry<String, net.opticraft.opticore.world.World> worlds : plugin.worlds.entrySet()) {
			String worldKey = worlds.getKey();
			if (plugin.worlds.get(worldKey).getWorld().equals(worldName)) {
				world = worldKey;
			}
		}
		return world;
	}
	
	public boolean worldExists(String world) {
		return plugin.worlds.containsKey(world);
	}

	public void teleportPlayerToWorld(Player player, String world) {
		
		World world1 = plugin.getServer().getWorld(plugin.worlds.get(world).getWorld());
		double x = plugin.worlds.get(world).getX();
		double y = plugin.worlds.get(world).getY();
		double z = plugin.worlds.get(world).getZ();
		float yaw = (float) plugin.worlds.get(world).getYaw();
		float pitch = (float) plugin.worlds.get(world).getPitch();
		
		Location location = new Location(world1, x, y, z, yaw, pitch);
		
		player.teleport(location);
	}
	
	public void setSpawn(Location location) {

		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();
		
		plugin.getConfig().set("warps." + world + ".location.world", world);
		plugin.getConfig().set("warps." + world + ".location.x", x);
		plugin.getConfig().set("warps." + world + ".location.y", y);
		plugin.getConfig().set("warps." + world + ".location.z", z);
		plugin.getConfig().set("warps." + world + ".location.yaw", yaw);
		plugin.getConfig().set("warps." + world + ".location.pitch", pitch);
		plugin.saveConfig();
		
		plugin.worlds.get(world).setWorld(world);
		plugin.worlds.get(world).setX(x);
		plugin.worlds.get(world).setY(y);
		plugin.worlds.get(world).setZ(z);
		plugin.worlds.get(world).setYaw(yaw);
		plugin.worlds.get(world).setPitch(pitch);
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("worlds")) {

			Set<String> worlds = plugin.getConfig().getConfigurationSection("worlds").getKeys(false);

			if (!worlds.isEmpty()) {
				for (String world : worlds) {
					
					String type = plugin.getConfig().getString("worlds." + world + ".type");

					String item = plugin.getConfig().getString("worlds." + world + ".item");
					
					String permission = plugin.getConfig().getString("worlds." + world + ".permission");
					
					boolean forced = plugin.getConfig().getBoolean("worlds." + world + ".spawn.forced");
					
					double spread = plugin.getConfig().getDouble("worlds." + world + ".spawn.spread");
					
					String world1 = plugin.getConfig().getString("worlds." + world + ".spawn.location.world");
					double x = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.x");
					double y = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.y");
					double z = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.z");
					double yaw = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.yaw");
					double pitch = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.pitch");
					
					List<String> owners = plugin.getConfig().getStringList("worlds." + world + ".owners");
					List<String> members = plugin.getConfig().getStringList("worlds." + world + ".members");

					plugin.worlds.put(world, new net.opticraft.opticore.world.World(type, item, permission, forced, spread, world1, x, y, z, yaw, pitch, owners, members));
				}
			}
		}
	}
}
