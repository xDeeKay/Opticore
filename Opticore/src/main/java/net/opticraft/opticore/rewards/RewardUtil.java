package net.opticraft.opticore.rewards;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RewardUtil {

	public Main plugin;

	public Config config;

	public Util util;

	public RewardUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("rewards")) {

			Set<String> rewards = plugin.getConfig().getConfigurationSection("rewards").getKeys(false);

			if (!rewards.isEmpty()) {

				for (String reward : rewards) {

					int cost = plugin.getConfig().getInt("rewards." + reward + ".cost");
					List<String> items = plugin.getConfig().getStringList("rewards." + reward + ".items");
					List<String> commands = plugin.getConfig().getStringList("rewards." + reward + ".commands");
					List<String> disguises = plugin.getConfig().getStringList("rewards." + reward + ".disguises");

					plugin.rewards.put(reward, new Reward(cost, items, commands, disguises));
				}
			}
		}
	}

	public String resolveRewardCase(String rewardName) {

		String reward = null;

		for (Map.Entry<String, Reward> rewards : plugin.rewards.entrySet()) {
			String rewardKey = rewards.getKey();
			if (rewardName.toLowerCase().equalsIgnoreCase(rewardKey)) {
				reward = rewardKey;
			}
		}
		return reward;
	}

	public boolean rewardExists(String reward) {
		return plugin.rewards.containsKey(reward);
	}

	public void giveReward(Player player, String reward) {

		for (String item : plugin.rewards.get(reward).getItems()) {

			String[] itemParts = item.split(":");
			Material material = Material.valueOf(itemParts[0].toUpperCase());
			int amount = Integer.parseInt(itemParts[1]);

			ItemStack itemStack = new ItemStack(material, amount);

			player.getInventory().addItem(itemStack);

			String itemName = material.toString().toLowerCase().replace("_", " ");

			util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "Gave " + amount + " of " + itemName);
		}

		for (String command : plugin.rewards.get(reward).getCommands()) {

			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
		}
	}
	
	public void giveDailyReward(Player player) {

		long currentTimestamp = System.currentTimeMillis() / 1000;
		long lastDaily = plugin.players.get(player.getName()).getLastDaily();
		long difference = currentTimestamp - lastDaily;

		if (difference > 86400) {

			int pointsDaily = plugin.config.getPointsDaily();

			giveRewardPoints(player.getName(), pointsDaily);
			showRewardPoints(player);

			plugin.players.get(player.getName()).setLastDaily(currentTimestamp);

			plugin.mysql.update("oc_points", 
					Arrays.asList("last_daily"), 
					Arrays.asList(currentTimestamp), 
					"uuid", player.getUniqueId().toString());
		} else {
			util.sendStyledMessage(player, null, "RED", "R", "GOLD", "You must wait " + util.timeConversion(86400 - difference) + " to claim your daily reward.");
		}

	}

	@SuppressWarnings("deprecation")
	public void giveRewardPoints(String target, int amount) {
		
		int points = 0;

		if (plugin.getServer().getPlayer(target) != null) {
			Player player = plugin.getServer().getPlayer(target);
			points = plugin.players.get(player.getName()).getPoints();
			plugin.players.get(player.getName()).setPoints(points + amount);
			util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "You have been awarded " + amount + " reward points.");
		} else {
			points = plugin.mysql.getUUIDColumnValue(target, "oc_points", "points");
		}
		
		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
		if (!plugin.mysql.tableRowContainsUUID("oc_points", "uuid", uuid)) {
			plugin.mysql.insert("oc_points", 
					Arrays.asList("uuid", "points", "last_daily"), 
					Arrays.asList(uuid, plugin.config.getPointsJoin() + amount, null));
		} else {
			plugin.mysql.update("oc_points", 
					Arrays.asList("points"), 
					Arrays.asList(points + amount), 
					"uuid", plugin.getServer().getOfflinePlayer(target).getUniqueId().toString());
		}
	}

	public void takeRewardPoints(Player player, int amount) {

		int points = plugin.players.get(player.getName()).getPoints();

		plugin.players.get(player.getName()).setPoints(points - amount);

		plugin.mysql.update("oc_points", 
				Arrays.asList("points"), 
				Arrays.asList(points - amount), 
				"uuid", player.getUniqueId().toString());
	}
	
	public void showRewardPoints(Player player) {
		int points = plugin.players.get(player.getName()).getPoints();
		util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "You have " + points + " reward points.");
	}
}
