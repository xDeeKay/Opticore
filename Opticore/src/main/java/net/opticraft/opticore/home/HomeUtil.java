package net.opticraft.opticore.home;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;

public class HomeUtil {

	public Main plugin;

	public Config config;

	public HomeUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
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

		homeConfig = YamlConfiguration.loadConfiguration(homeFile);
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

		if (!homeConfig.isSet(uuid)) {
			// Player exists in home config section

			homeConfig.set(uuid, "");
			homeConfig.set(uuid + ".amount", 1);
			homeConfig.set(uuid + ".homes", Collections.emptyMap());

			// Save the config
			saveConfig();
			loadConfig();
		}

		// Get player homes amount
		int homesAmount = homeConfig.getInt(uuid + ".amount");

		// Attempt to create player class
		if (!plugin.players.containsKey(player.getName())) {
			plugin.players.put(player.getName(), new net.opticraft.opticore.player.Player());
		}

		// Set homes amount to player class
		plugin.players.get(player.getName()).setHomesAmount(homesAmount);

		// Get player homes from home config section
		Set<String> homes = homeConfig.getConfigurationSection(uuid + ".homes").getKeys(false);

		if (!homes.isEmpty()) {
			// Player has at least 1 home

			for (String home : homes) {
				// Loop through player homes

				// Get home location values from home config
				String material = homeConfig.getString(uuid + ".homes." + home + ".material");
				boolean locked = homeConfig.getBoolean(uuid + ".homes." + home + ".locked");
				String world = homeConfig.getString(uuid + ".homes." + home + ".location.world");
				double x = homeConfig.getDouble(uuid + ".homes." + home + ".location.x");
				double y = homeConfig.getDouble(uuid + ".homes." + home + ".location.y");
				double z = homeConfig.getDouble(uuid + ".homes." + home + ".location.z");
				double yaw = homeConfig.getDouble(uuid + ".homes." + home + ".location.yaw");
				double pitch = homeConfig.getDouble(uuid + ".homes." + home + ".location.pitch");

				// Add home to player class
				plugin.players.get(player.getName()).getHomes().put(home, new Home(material, locked, world, x, y, z, yaw, pitch));
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

			if (homeConfig.isSet(uuid)) {
				// Player exists in home config section

				// Resolve home for case sensitivity
				home = resolveHome(target, home);

				// Return state of the homes existence in home config
				if (homeConfig.contains(uuid + ".homes." + home)) {
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
		Set<String> homes = homeConfig.getConfigurationSection(uuid + ".homes").getKeys(false);

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

		if (plugin.getServer().getPlayer(target) != null && plugin.players.containsKey(target)) {
			// target is online
			// Return state of the homes lock in player class
			if (plugin.players.get(target).getHomes().get(home).getLocked() || getPrivacy(target) == 0) {
				return true;
			}

		} else {
			// target is offline

			String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

			if (homeConfig.isSet(uuid)) {
				// Player exists in home config section

				// Resolve home for case sensitivity
				home = resolveHome(target, home);

				// Return state of the homes lock in home config
				if (homeConfig.getBoolean(uuid + ".homes." + home + ".locked") || getPrivacy(target) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public int getPrivacy(String target) {
		
		int privacy;

		if (plugin.getServer().getPlayer(target) != null && plugin.players.containsKey(target)) {
			// target is online
			// Return state of the home privacy from player class
			privacy = plugin.players.get(target).getSettings().get("home_privacy").getValue();

		} else {
			// target is offline
			// Return state of the home privacy from database
			String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
			privacy = (int) plugin.mysql.getUUIDColumnValue(uuid, "oc_users", "setting_home_privacy");
		}
		
		System.out.print(privacy);

		return privacy;
	}

	public void setLock(Player player, String home, boolean locked) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Resolve home for case sensitivity
		home = resolveHome(player.getName(), home);

		// Update state of the homes lock in home config
		homeConfig.set(uuid + ".homes." + home + ".locked", locked);

		// Save the config
		saveConfig();

		// Update state of the homes lock in player class
		plugin.players.get(player.getName()).getHomes().get(home).setLocked(locked);
	}

	@SuppressWarnings("deprecation")
	public int getHomesAmount(String target) {

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// Return player homes amount from player class
			if (plugin.players.containsKey(target)) {
				return plugin.players.get(target).getHomesAmount();
			}

		} else {
			// target is offline

			String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

			if (homeConfig.isSet(uuid)) {
				// Player exists in home config section

				// Return player homes amount from player homes config
				return homeConfig.getInt(uuid + ".amount");
			}
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	public void setHomesAmount(String target, int amount) {

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// Set player homes amount in player class
			if (plugin.players.containsKey(target)) {
				plugin.players.get(target).setHomesAmount(amount);
			}
		}

		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

		if (homeConfig.isSet(uuid)) {
			// Player exists in home config section

			// Set player homes amount in player homes config
			homeConfig.set(uuid + ".amount", amount);

			// Save home config
			saveConfig();
		}
	}

	public void setHome(Player player, String home, Location location, String material, boolean main, boolean locked) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Set default item
		if (material == null) {
			material = plugin.gui.get("opticraft").getSlots().get("homes").getMaterial();
		}

		// Split player location values
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();

		// Add home to home config
		homeConfig.set(uuid + ".homes." + home, "");
		homeConfig.set(uuid + ".homes." + home + ".material", material);
		homeConfig.set(uuid + ".homes." + home + ".locked", locked);
		homeConfig.set(uuid + ".homes." + home + ".location.world", world);
		homeConfig.set(uuid + ".homes." + home + ".location.x", x);
		homeConfig.set(uuid + ".homes." + home + ".location.y", y);
		homeConfig.set(uuid + ".homes." + home + ".location.z", z);
		homeConfig.set(uuid + ".homes." + home + ".location.yaw", yaw);
		homeConfig.set(uuid + ".homes." + home + ".location.pitch", pitch);

		// Add home to player class
		plugin.players.get(player.getName()).getHomes().put(home, new Home(material, locked, world, x, y, z, yaw, pitch));

		// Update homes remaining for player class and homes config
		int homesRemaining = plugin.players.get(player.getName()).getHomesAmount() - 1;
		plugin.players.get(player.getName()).setHomesAmount(homesRemaining);

		homeConfig.set(uuid + ".amount", homesRemaining);

		// Save home config
		saveConfig();
	}

	public void delHome(Player player, String home) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Resolve home for case sensitivity
		home = resolveHome(player.getName(), home);

		// Remove home from config
		homeConfig.set(uuid + ".homes." + home, null);

		// Remove home from player class
		plugin.players.get(player.getName()).getHomes().remove(home);

		// Update homes remaining for player class and database column
		int homesRemaining = plugin.players.get(player.getName()).getHomesAmount() + 1;
		plugin.players.get(player.getName()).setHomesAmount(homesRemaining);

		homeConfig.set(uuid + ".amount", homesRemaining);

		// Save the config
		saveConfig();
	}

	public void moveHome(Player player, String home, Location location) {

		// Get player uuid
		String uuid = player.getUniqueId().toString();

		// Split player location values
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();

		// Update home to home config
		homeConfig.set(uuid + ".homes." + home + ".location.world", world);
		homeConfig.set(uuid + ".homes." + home + ".location.x", x);
		homeConfig.set(uuid + ".homes." + home + ".location.y", y);
		homeConfig.set(uuid + ".homes." + home + ".location.z", z);
		homeConfig.set(uuid + ".homes." + home + ".location.yaw", yaw);
		homeConfig.set(uuid + ".homes." + home + ".location.pitch", pitch);

		// Update home to player class
		plugin.players.get(player.getName()).getHomes().get(home).setWorld(world);
		plugin.players.get(player.getName()).getHomes().get(home).setX(x);
		plugin.players.get(player.getName()).getHomes().get(home).setY(y);
		plugin.players.get(player.getName()).getHomes().get(home).setZ(z);
		plugin.players.get(player.getName()).getHomes().get(home).setYaw(yaw);
		plugin.players.get(player.getName()).getHomes().get(home).setPitch(pitch);

		// Save home config
		saveConfig();
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
			world = plugin.getServer().getWorld(homeConfig.getString(uuid + ".homes." + home + ".location.world"));
			x = homeConfig.getDouble(uuid + ".homes." + home + ".location.x");
			y = homeConfig.getDouble(uuid + ".homes." + home + ".location.y");
			z = homeConfig.getDouble(uuid + ".homes." + home + ".location.z");
			yaw = homeConfig.getDouble(uuid + ".homes." + home + ".location.yaw");
			pitch = homeConfig.getDouble(uuid + ".homes." + home + ".location.pitch");
		}

		// Create location from home location values
		Location location = new Location(world, x, y, z, (float) yaw, (float) pitch);

		// Teleport player to home location
		player.teleport(location);
	}
}
