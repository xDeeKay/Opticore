package net.opticraft.opticore.home;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.MySQL;

public class HomeUtil {

	public Main plugin;

	public Config config;

	public MySQL mysql;

	public HomeUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.mysql = this.plugin.mysql;
	}

	private File homeFile;
	private FileConfiguration homeConfig;
	
	public FileConfiguration getConfig() {
		return homeConfig;
	}

	public void loadConfig() {

		// Create home config file if absent
		homeFile = new File(plugin.getDataFolder().getAbsolutePath(), "homes.yml");
		if (!homeFile.exists()) {
			try {
				homeFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Create home config section if absent
		homeConfig = YamlConfiguration.loadConfiguration(homeFile);
		if (!homeConfig.contains("homes")) {
			homeConfig.createSection("homes");
			saveConfig();
		}
	}

	public void saveConfig() {
		try {
			homeConfig.save(homeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPlayerHomes(Player player) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		if (homeConfig.isSet("homes." + uuid)) {
			// Player exists in home config section

			// Get player homes from home config section
			Set<String> homes = homeConfig.getConfigurationSection("homes." + uuid).getKeys(false);

			if (!homes.isEmpty()) {
				// Player has at least 1 home
				
				for (String home : homes) {
					// Loop through player homes

					// Get home location values from home config
					String material = homeConfig.getString("homes." + uuid + "." + home + ".material");
					boolean locked = homeConfig.getBoolean("homes." + uuid + "." + home + ".locked");
					String world = homeConfig.getString("homes." + uuid + "." + home + ".location.world");
					double x = homeConfig.getDouble("homes." + uuid + "." + home + ".location.x");
					double y = homeConfig.getDouble("homes." + uuid + "." + home + ".location.y");
					double z = homeConfig.getDouble("homes." + uuid + "." + home + ".location.z");
					double yaw = homeConfig.getDouble("homes." + uuid + "." + home + ".location.yaw");
					double pitch = homeConfig.getDouble("homes." + uuid + "." + home + ".location.pitch");

					// Attempt to create player class
					if (!plugin.players.containsKey(player.getName())) {
						plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
					}

					// Add home to player class
					plugin.players.get(player.getName()).getHomes().put(home, new Home(material, locked, world, x, y, z, yaw, pitch));
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean homeExists(String target, String home) {

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// Return state of the homes existence in player class
			if (plugin.players.containsKey(target) && plugin.players.get(target).getHomes().containsKey(home)) {
				return true;
			}

		} else {
			// target is offline

			String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

			if (homeConfig.isSet("homes." + uuid)) {
				// Player exists in home config section
				
				// Resolve home for case sensitivity
				home = resolveHome(target, home);
				
				// Return state of the homes existence in home config
				if (homeConfig.contains("homes." + uuid + "." + home)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public String resolveHome(String target, String home) {

		// Get offline player uuid
		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

		// Get player homes from home config section
		Set<String> homes = homeConfig.getConfigurationSection("homes." + uuid).getKeys(false);

		// Loop through homes and get proper-case home
		for (String homeKey : homes) {
			if (homeKey.equalsIgnoreCase(home)) {
				home = homeKey;
			}
		}
		return home;
	}

	@SuppressWarnings("deprecation")
	public boolean getLock(String target, String home) {

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// Return state of the homes lock in player class
			if (plugin.players.get(target).getHomes().get(home).getLocked()) {
				return true;
			}

		} else {
			// target is offline

			String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

			if (homeConfig.isSet("homes." + uuid)) {
				// Player exists in home config section
				
				// Resolve home for case sensitivity
				home = resolveHome(target, home);

				// Return state of the homes lock in home config
				if (homeConfig.getBoolean("homes." + uuid + "." + home + ".locked")) {
					return true;
				}
			}
		}
		return false;
	}

	public void setLock(Player player, String home, boolean locked) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Resolve home for case sensitivity
		home = resolveHome(player.getName(), home);

		// Update state of the homes lock in home config
		homeConfig.set("homes." + uuid + "." + home + ".locked", locked);

		// Save the config
		saveConfig();

		// Update state of the homes lock in player class
		plugin.players.get(player.getName()).getHomes().get(home).setLocked(locked);
	}

	public void setHome(Player player, String home, Location location, String material, boolean main, boolean locked) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Set default item
		if (material == null) {
			material = plugin.gui.get("homes").getToolbars().get("page").getMaterial();
		}

		// Split player location values
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();

		// Add home to home config
		homeConfig.set("homes." + uuid + "." + home, "");
		homeConfig.set("homes." + uuid + "." + home + ".material", material);
		homeConfig.set("homes." + uuid + "." + home + ".locked", locked);
		homeConfig.set("homes." + uuid + "." + home + ".location.world", world);
		homeConfig.set("homes." + uuid + "." + home + ".location.x", x);
		homeConfig.set("homes." + uuid + "." + home + ".location.y", y);
		homeConfig.set("homes." + uuid + "." + home + ".location.z", z);
		homeConfig.set("homes." + uuid + "." + home + ".location.yaw", yaw);
		homeConfig.set("homes." + uuid + "." + home + ".location.pitch", pitch);

		// Save home config
		saveConfig();

		// Add home to player class
		plugin.players.get(player.getName()).getHomes().put(home, new Home(material, locked, world, x, y, z, yaw, pitch));

		// Update homes remaining for player class and database column
		int homesRemaining = plugin.players.get(player.getName()).getHomesRemaining() - 1;
		plugin.players.get(player.getName()).setHomesRemaining(homesRemaining);
		plugin.mysql.setUsersColumnValue(player.getName(), "homes_remaining", homesRemaining);
	}

	public void delHome(Player player, String home) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Resolve home for case sensitivity
		home = resolveHome(player.getName(), home);

		// Remove home from config
		homeConfig.set("homes." + uuid + "." + home, null);

		// Save the config
		saveConfig();

		// Remove home from player class
		plugin.players.get(player.getName()).getHomes().remove(home);

		// Update homes remaining for player class and database column
		int homesRemaining = plugin.players.get(player.getName()).getHomesRemaining() + 1;
		plugin.players.get(player.getName()).setHomesRemaining(homesRemaining);
		plugin.mysql.setUsersColumnValue(player.getName(), "homes_remaining", homesRemaining);
	}

	@SuppressWarnings("deprecation")
	public void teleportPlayerToHome(Player player, String target, String home) {

		// Initialise home location values
		World world;
		double x;
		double y;
		double z;
		double yaw;
		double pitch;

		if (plugin.getServer().getPlayer(target) != null) {
			// Target is online

			// Get target player
			Player targetPlayer = plugin.getServer().getPlayer(target);

			// Get home location values from player class
			world = plugin.getServer().getWorld(plugin.players.get(targetPlayer.getName()).getHomes().get(home).getWorld());
			x = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getX();
			y = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getY();
			z = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getZ();
			yaw = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getYaw();
			pitch = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getPitch();

		} else {
			// Target is offline

			// Get target uuid
			String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

			// Resolve home for case sensitivity
			home = resolveHome(target, home);

			// Get home location values from home config
			world = plugin.getServer().getWorld(homeConfig.getString("homes." + uuid + "." + home + ".location.world"));
			x = homeConfig.getDouble("homes." + uuid + "." + home + ".location.x");
			y = homeConfig.getDouble("homes." + uuid + "." + home + ".location.y");
			z = homeConfig.getDouble("homes." + uuid + "." + home + ".location.z");
			yaw = homeConfig.getDouble("homes." + uuid + "." + home + ".location.yaw");
			pitch = homeConfig.getDouble("homes." + uuid + "." + home + ".location.pitch");
		}

		// Create location from home location values
		Location location = new Location(world, x, y, z, (float) yaw, (float) pitch);

		// Teleport player to home location
		player.teleport(location);
	}
}
