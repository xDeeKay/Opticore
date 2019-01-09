package net.opticraft.opticore.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class TeamUtil {

	public Main plugin;

	public Util util;

	public TeamUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	@SuppressWarnings("unchecked")
	public void loadConfig() {

		if (plugin.getConfig().isSet("teams")) {

			Set<String> teams = plugin.getConfig().getConfigurationSection("teams").getKeys(false);

			if (!teams.isEmpty()) {
				for (String team : teams) {

					List<String> members = plugin.getConfig().getStringList("teams." + team + ".members");

					String world = plugin.getConfig().getString("teams." + team + ".spawn.world");
					double x = plugin.getConfig().getDouble("teams." + team + ".spawn.x");
					double y = plugin.getConfig().getDouble("teams." + team + ".spawn.y");
					double z = plugin.getConfig().getDouble("teams." + team + ".spawn.z");
					double yaw = plugin.getConfig().getDouble("teams." + team + ".spawn.yaw");
					double pitch = plugin.getConfig().getDouble("teams." + team + ".spawn.pitch");

					ItemStack[] inventory = ((List<ItemStack>)plugin.getConfig().get("teams." + team + ".inventory")).toArray(new ItemStack[0]);

					String gamemode = plugin.getConfig().getString("teams." + team + ".gamemode");

					plugin.teams.put(team, new Team(members, world, x, y, z, yaw, pitch, inventory, gamemode));
				}
			}
		}
	}

	public String resolveTeamCase(String teamName) {

		String team = null;

		for (String teamKey : plugin.teams.keySet()) {
			if (teamName.toLowerCase().equalsIgnoreCase(teamKey)) {
				team = teamKey;
			}
		}
		return team;
	}

	public boolean teamExists(String team) {
		return plugin.teams.containsKey(team);
	}

	public void setSpawn(String team, Location location) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			String world = location.getWorld().getName();
			double x = location.getX();
			double y = location.getY();
			double z = location.getZ();
			double yaw = location.getYaw();
			double pitch = location.getPitch();

			plugin.getConfig().set("teams." + team + ".spawn.world", world);
			plugin.getConfig().set("teams." + team + ".spawn.x", x);
			plugin.getConfig().set("teams." + team + ".spawn.y", y);
			plugin.getConfig().set("teams." + team + ".spawn.z", z);
			plugin.getConfig().set("teams." + team + ".spawn.yaw", yaw);
			plugin.getConfig().set("teams." + team + ".spawn.pitch", pitch);
			plugin.saveConfig();

			plugin.teams.get(team).setWorld(world);
			plugin.teams.get(team).setX(x);
			plugin.teams.get(team).setY(y);
			plugin.teams.get(team).setZ(z);
			plugin.teams.get(team).setYaw(yaw);
			plugin.teams.get(team).setPitch(pitch);
		}
	}

	public Location getTeamSpawn(String team) {

		team = resolveTeamCase(team);

		World world = plugin.getServer().getWorld(plugin.teams.get(team).getWorld());
		double x = plugin.teams.get(team).getX();
		double y = plugin.teams.get(team).getY();
		double z = plugin.teams.get(team).getZ();
		float yaw = (float) plugin.teams.get(team).getYaw();
		float pitch = (float) plugin.teams.get(team).getPitch();

		Location location = new Location(world, x, y, z, yaw, pitch);

		return location;
	}

	public void teleportPlayerToTeamSpawn(String team, Player player) {

		team = resolveTeamCase(team);

		World world = plugin.getServer().getWorld(plugin.teams.get(team).getWorld());
		double x = plugin.teams.get(team).getX();
		double y = plugin.teams.get(team).getY();
		double z = plugin.teams.get(team).getZ();
		float yaw = (float) plugin.teams.get(team).getYaw();
		float pitch = (float) plugin.teams.get(team).getPitch();

		Location location = new Location(world, x, y, z, yaw, pitch);

		player.teleport(location);
	}

	public void create(String team, Player player) {

		if (!teamExists(team)) {

			String world = player.getLocation().getWorld().getName();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			double yaw = player.getLocation().getYaw();
			double pitch = player.getLocation().getPitch();

			ItemStack[] inventory = player.getInventory().getContents();

			String gamemode = player.getGameMode().name();

			plugin.getConfig().set("teams." + team, "");

			plugin.getConfig().set("teams." + team + ".members", new ArrayList<String>());

			plugin.getConfig().set("teams." + team + ".spawn.world", world);
			plugin.getConfig().set("teams." + team + ".spawn.x", x);
			plugin.getConfig().set("teams." + team + ".spawn.y", y);
			plugin.getConfig().set("teams." + team + ".spawn.z", z);
			plugin.getConfig().set("teams." + team + ".spawn.yaw", yaw);
			plugin.getConfig().set("teams." + team + ".spawn.pitch", pitch);

			plugin.getConfig().set("teams." + team + ".inventory", inventory);

			plugin.getConfig().set("teams." + team + ".gamemode", gamemode);

			plugin.saveConfig();

			plugin.teams.put(team, new Team(new ArrayList<String>(), world, x, y, z, yaw, pitch, inventory, gamemode));
		}
	}

	public void delete(String team) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			plugin.getConfig().set("teams." + team, null);
			plugin.saveConfig();

			plugin.teams.remove(team);
		}
	}

	public void setInventory(String team, ItemStack[] inventory) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			plugin.getConfig().set("teams." + team + ".inventory", inventory);
			plugin.saveConfig();

			plugin.teams.get(team).setInventory(inventory);
		}
	}

	public void setPlayerInventory(String team, Player player) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			ItemStack[] inventory = plugin.teams.get(team).getInventory();

			int i = 0;
			for (ItemStack item : inventory) {
				player.getInventory().setItem(i, item);
				i++;
			}
		}
	}

	public void setGamemode(String team, String gamemode) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			plugin.getConfig().set("teams." + team + ".gamemode", gamemode);
			plugin.saveConfig();

			plugin.teams.get(team).setGamemode(gamemode);
		}
	}

	public void setPlayerGamemode(String team, Player player) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			String gamemode = plugin.teams.get(team).getGamemode();

			player.setGameMode(GameMode.valueOf(gamemode));
		}
	}

	public boolean isMember(String team, Player player) {

		team = resolveTeamCase(team);

		String uuid = player.getUniqueId().toString();

		if (plugin.teams.get(team).getMembers().contains(uuid)) {
			return true;
		}

		return false;
	}

	public String getTeam(Player player) {

		String teamName = null;

		String uuid = player.getUniqueId().toString();

		for (String team : plugin.teams.keySet()) {
			if (plugin.teams.get(team).getMembers().contains(uuid)) {
				teamName = team;
				return teamName;
			}
		}
		return teamName;
	}

	public void join(String team, Player player) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			if (!isMember(team, player)) {

				String uuid = player.getUniqueId().toString();

				List<String> members1 = plugin.teams.get(team).getMembers();
				members1.add(uuid);
				plugin.teams.get(team).setMembers(members1);

				List<String> members2 = plugin.getConfig().getStringList("teams." + team + ".members");
				members2.add(uuid);
				plugin.getConfig().set("teams." + team + ".members", members2);
				plugin.saveConfig();
			}
		}
	}

	public void leave(String team, Player player) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			String uuid = player.getUniqueId().toString();

			List<String> members = plugin.teams.get(team).getMembers();
			members.remove(uuid);

			plugin.getConfig().set("teams." + team + ".members", members);
			plugin.saveConfig();

			plugin.teams.get(team).getMembers().remove(uuid);
		}
	}

	public void listMembers(String team, Player player) {

		team = resolveTeamCase(team);

		if (teamExists(team)) {

			List<String> memberList = plugin.teams.get(team).getMembers();

			List<String> memberNames = new ArrayList<String>();
			for (String uuid : memberList) {
				String name = plugin.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
				memberNames.add(name);
			}

			String members = StringUtils.join(memberNames, ", ");

			util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Members of team '" + team + "': " + members);
		}
	}
}
