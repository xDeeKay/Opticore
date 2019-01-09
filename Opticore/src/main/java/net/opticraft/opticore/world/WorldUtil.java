package net.opticraft.opticore.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class WorldUtil {

	public Main plugin;

	public Util util;

	public WorldUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("worlds")) {

			Set<String> worlds = plugin.getConfig().getConfigurationSection("worlds").getKeys(false);

			if (!worlds.isEmpty()) {
				for (String world : worlds) {

					String type = plugin.getConfig().getString("worlds." + world + ".type");

					String material = plugin.getConfig().getString("worlds." + world + ".material");

					String permission = plugin.getConfig().getString("worlds." + world + ".permission");

					boolean forced = plugin.getConfig().getBoolean("worlds." + world + ".spawn.forced");

					double spread = plugin.getConfig().getDouble("worlds." + world + ".spawn.spread");

					String world1 = plugin.getConfig().getString("worlds." + world + ".spawn.location.world");
					double x = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.x");
					double y = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.y");
					double z = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.z");
					double yaw = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.yaw");
					double pitch = plugin.getConfig().getDouble("worlds." + world + ".spawn.location.pitch");

					String group = plugin.getConfig().getString("worlds." + world + ".groups.default");

					List<String> owners = plugin.getConfig().getStringList("worlds." + world + ".groups.owners");
					List<String> members = plugin.getConfig().getStringList("worlds." + world + ".groups.members");
					List<String> guests = plugin.getConfig().getStringList("worlds." + world + ".groups.guests");
					List<String> spectators = plugin.getConfig().getStringList("worlds." + world + ".groups.spectators");

					plugin.worlds.put(world, new net.opticraft.opticore.world.World(type, material, permission, forced, spread, world1, x, y, z, yaw, pitch, group, owners, members, guests, spectators));
				}
			}
		}
	}

	public String resolveWorld(String worldName) {

		String world1 = worldName;

		for (String world : plugin.worlds.keySet()) {
			if (plugin.worlds.get(world).getWorld().equals(worldName)) {
				world1 = world;
			}
		}
		return world1;
	}

	public String resolveWorldCase(String worldName) {

		String world1 = null;

		for (String world : plugin.worlds.keySet()) {
			if (worldName.toLowerCase().equalsIgnoreCase(world)) {
				world1 = world;
			}
		}
		return world1;
	}

	public boolean worldExists(String world) {
		return plugin.worlds.containsKey(world);
	}

	public Location getWorldLocation(String world) {

		World world1 = plugin.getServer().getWorld(plugin.worlds.get(world).getWorld());
		double x = plugin.worlds.get(world).getX();
		double y = plugin.worlds.get(world).getY();
		double z = plugin.worlds.get(world).getZ();
		float yaw = (float) plugin.worlds.get(world).getYaw();
		float pitch = (float) plugin.worlds.get(world).getPitch();

		Location location = new Location(world1, x, y, z, yaw, pitch);

		return location;
	}

	public void setSpawn(String world, Location location) {

		world = resolveWorldCase(world);

		String world1 = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();

		plugin.getServer().getWorld(world1).setSpawnLocation(location);

		plugin.getConfig().set("worlds." + world + ".spawn.location.world", world1);
		plugin.getConfig().set("worlds." + world + ".spawn.location.x", x);
		plugin.getConfig().set("worlds." + world + ".spawn.location.y", y);
		plugin.getConfig().set("worlds." + world + ".spawn.location.z", z);
		plugin.getConfig().set("worlds." + world + ".spawn.location.yaw", yaw);
		plugin.getConfig().set("worlds." + world + ".spawn.location.pitch", pitch);
		plugin.saveConfig();

		plugin.worlds.get(world).setWorld(world1);
		plugin.worlds.get(world).setX(x);
		plugin.worlds.get(world).setY(y);
		plugin.worlds.get(world).setZ(z);
		plugin.worlds.get(world).setYaw(yaw);
		plugin.worlds.get(world).setPitch(pitch);
	}

	public void setGroup(String world, String group) {

		world = resolveWorldCase(world);
		group = group.toLowerCase();

		plugin.getConfig().set("worlds." + world + ".groups.default", group);
		plugin.saveConfig();

		plugin.worlds.get(world).setGroup(group);
	}

	public boolean isOwner(Player player, String world) {

		String uuid = player.getUniqueId().toString();

		String worldPermission =  plugin.worlds.get(world).getPermission();

		if (plugin.worlds.get(world).getGroup().equals("owner") || 
				plugin.worlds.get(world).getOwners().contains(uuid) || 
				player.hasPermission("opticore.world.owner." + worldPermission)) {
			return true;
		}

		return false;
	}

	public void addOwner(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> owners = plugin.worlds.get(world).getOwners();
		owners.add(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.owners", owners);
		plugin.saveConfig();

		plugin.worlds.get(world).getOwners().add(uuid);
	}

	public void removeOwner(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> owners = plugin.worlds.get(world).getOwners();
		owners.remove(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.owners", owners);
		plugin.saveConfig();

		plugin.worlds.get(world).getOwners().remove(uuid);
	}

	public void listOwners(Player player, String world) {

		world = resolveWorldCase(world);

		List<String> ownerList = plugin.worlds.get(world).getOwners();

		List<String> ownerNames = new ArrayList<String>();
		for (String uuid : ownerList) {
			String name = plugin.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
			ownerNames.add(name);
		}

		String owners = StringUtils.join(ownerNames, ", ");

		util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Owners of world '" + world + "': " + owners);
	}

	public boolean isMember(Player player, String world) {

		String uuid = player.getUniqueId().toString();

		String worldPermission =  plugin.worlds.get(world).getPermission();

		if (plugin.worlds.get(world).getGroup().equals("member") || 
				plugin.worlds.get(world).getMembers().contains(uuid) || 
				player.hasPermission("opticore.world.member." + worldPermission) || 
				player.hasPermission("opticore.world.build." + worldPermission)) {
			return true;
		}

		return false;
	}

	public void addMember(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> members = plugin.worlds.get(world).getMembers();
		members.add(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.members", members);
		plugin.saveConfig();

		plugin.worlds.get(world).getMembers().add(uuid);
	}

	public void removeMember(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> members = plugin.worlds.get(world).getMembers();
		members.remove(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.members", members);
		plugin.saveConfig();

		plugin.worlds.get(world).getMembers().remove(uuid);
	}

	public void listMembers(Player player, String world) {

		world = resolveWorldCase(world);

		List<String> memberList = plugin.worlds.get(world).getMembers();

		List<String> memberNames = new ArrayList<String>();
		for (String uuid : memberList) {
			String name = plugin.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
			memberNames.add(name);
		}

		String members = StringUtils.join(memberNames, ", ");

		util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Members of world '" + world + "': " + members);
	}

	public boolean isGuest(Player player, String world) {

		String uuid = player.getUniqueId().toString();

		String worldPermission =  plugin.worlds.get(world).getPermission();

		if (plugin.worlds.get(world).getGroup().equals("guest") || 
				plugin.worlds.get(world).getGuests().contains(uuid) || 
				player.hasPermission("opticore.world.guest." + worldPermission) || 
				player.hasPermission("opticore.world.interact." + worldPermission)) {
			return true;
		}

		return false;
	}

	public void addGuest(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> guests = plugin.worlds.get(world).getGuests();
		guests.add(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.guests", guests);
		plugin.saveConfig();

		plugin.worlds.get(world).getGuests().add(uuid);
	}

	public void removeGuest(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> guests = plugin.worlds.get(world).getGuests();
		guests.remove(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.guests", guests);
		plugin.saveConfig();

		plugin.worlds.get(world).getGuests().remove(uuid);
	}

	public void listGuests(Player player, String world) {

		world = resolveWorldCase(world);

		List<String> guestList = plugin.worlds.get(world).getGuests();

		List<String> guestNames = new ArrayList<String>();
		for (String uuid : guestList) {
			String name = plugin.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
			guestNames.add(name);
		}

		String guests = StringUtils.join(guestNames, ", ");

		util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Guests of world '" + world + "': " + guests);
	}

	public boolean isSpectator(Player player, String world) {

		String uuid = player.getUniqueId().toString();

		String worldPermission =  plugin.worlds.get(world).getPermission();

		if (plugin.worlds.get(world).getGroup().equals("spectator") || 
				plugin.worlds.get(world).getSpectators().contains(uuid) || 
				player.hasPermission("opticore.world.spectator." + worldPermission) || 
				player.hasPermission("opticore.world.join." + worldPermission)) {
			return true;
		}

		return false;
	}

	public void addSpectator(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> spectators = plugin.worlds.get(world).getSpectators();
		spectators.add(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.spectators", spectators);
		plugin.saveConfig();

		plugin.worlds.get(world).getSpectators().add(uuid);
	}

	public void removeSpectator(String world, String uuid) {

		world = resolveWorldCase(world);

		List<String> spectators = plugin.worlds.get(world).getSpectators();
		spectators.remove(uuid);

		plugin.getConfig().set("worlds." + world + ".groups.spectators", spectators);
		plugin.saveConfig();

		plugin.worlds.get(world).getSpectators().remove(uuid);
	}

	public void listSpectators(Player player, String world) {

		world = resolveWorldCase(world);

		List<String> spectatorList = plugin.worlds.get(world).getSpectators();

		List<String> spectatorNames = new ArrayList<String>();
		for (String uuid : spectatorList) {
			String name = plugin.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
			spectatorNames.add(name);
		}

		String spectators = StringUtils.join(spectatorNames, ", ");

		util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Spectators of world '" + world + "': " + spectators);
	}
}
