package net.opticraft.opticore.rewards;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class RewardUtil {

	public Main plugin;

	public Config config;

	public Util util;

	public BukkitTask voteReminder;
	public BukkitTask dailyReminder;

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

	@SuppressWarnings("deprecation")
	public void giveRewardPoint(String target, int amount) {

		int points = 0;

		if (plugin.getServer().getPlayer(target) != null) {
			Player player = plugin.getServer().getPlayer(target);
			points = plugin.mysql.getUUIDColumnValue(player.getName(), "oc_points", "points");
			plugin.players.get(player.getName()).setPoints(points + amount);
			util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "You have been awarded " + amount + " reward points.");
		} else {
			points = plugin.mysql.getUUIDColumnValue(target, "oc_points", "points");
		}

		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

		if (!plugin.mysql.tableRowContainsUUID("oc_points", "uuid", uuid)) {
			plugin.mysql.insert("oc_points", 
					Arrays.asList("uuid", "points", "last_daily", "last_vote"), 
					Arrays.asList(uuid, plugin.config.getPointsJoin() + amount, null, null));
		} else {
			plugin.mysql.update("oc_points", 
					Arrays.asList("points"), 
					Arrays.asList(points + amount), 
					"uuid", uuid);
		}
	}

	@SuppressWarnings("deprecation")
	public void takeRewardPoint(String target, int amount) {

		int points = plugin.mysql.getUUIDColumnValue(target, "oc_points", "points");

		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

		if (!plugin.mysql.tableRowContainsUUID("oc_points", "uuid", uuid)) {
			plugin.mysql.insert("oc_points", 
					Arrays.asList("uuid", "points", "last_daily", "last_vote"), 
					Arrays.asList(uuid, plugin.config.getPointsJoin() - amount, null, null));
		} else {
			plugin.mysql.update("oc_points", 
					Arrays.asList("points"), 
					Arrays.asList(points - amount), 
					"uuid", uuid);
		}
	}

	public void showRewardPoints(Player player) {
		int points = plugin.mysql.getUUIDColumnValue(player.getName(), "oc_points", "points");
		util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "You have " + points + " reward points.");
	}

	public long getTimeSinceLastDaily(Player player, long timestamp, boolean fromDB) {

		long lastDaily;
		if (fromDB) {
			lastDaily = plugin.mysql.getUUIDColumnValue(player.getName(), "oc_points", "last_daily");
		} else {
			lastDaily = plugin.players.get(player.getName()).getLastDaily();
		}

		long timeSince = timestamp - lastDaily;

		return timeSince;
	}

	public void giveDailyReward(Player player) {

		long timestamp = System.currentTimeMillis() / 1000;

		if (getTimeSinceLastDaily(player, timestamp, false) >= 86400) {

			giveRewardPoint(player.getName(), plugin.config.getPointsDaily());
			showRewardPoints(player);

			plugin.players.get(player.getName()).setLastDaily(timestamp);

			plugin.mysql.update("oc_points", 
					Arrays.asList("last_daily"), 
					Arrays.asList(timestamp), 
					"uuid", player.getUniqueId().toString());
		} else {
			util.sendStyledMessage(player, null, "RED", "R", "GOLD", "You must wait " + util.timeConversion(86400 - getTimeSinceLastDaily(player, timestamp, false)) + " to claim your daily reward.");
		}
	}

	public boolean canVote(Player player, boolean fromDB) {

		long timestamp = System.currentTimeMillis() / 1000;

		long lastVote;
		if (fromDB) {
			lastVote = plugin.mysql.getUUIDColumnValue(player.getName(), "oc_points", "last_vote");
		} else {
			lastVote = plugin.players.get(player.getName()).getLastVote();
		}

		long timeSince = timestamp - lastVote;

		if (timeSince >= 86400) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("deprecation")
	public void giveVoteReward(String playerName, long timestamp) {

		String uuid = plugin.getServer().getOfflinePlayer(playerName).getUniqueId().toString();

		giveRewardPoint(playerName, plugin.config.getPointsVote());

		plugin.mysql.update("oc_points", 
				Arrays.asList("last_vote"), 
				Arrays.asList(timestamp), 
				"uuid", uuid);
	}

	public void logVote(String uuid, String username, String address, long timestamp, String serviceName) {

		plugin.mysql.insert("oc_votes", 
				Arrays.asList("uuid", "username", "ip", "timestamp", "service"), 
				Arrays.asList(uuid, username, address, timestamp, serviceName));
	}

	public void startVoteReminder() {
		this.voteReminder = new BukkitRunnable() {
			public void run() {
				sendVoteReminder();
			}
		}.runTaskTimer(plugin, 20 * config.getReminderVoteInterval(), 20 * config.getReminderVoteInterval());
	}

	public void stopVoteReminder() {
		if (this.voteReminder != null) {
			this.voteReminder.cancel();
			this.voteReminder = null;
		}
	}

	public void sendVoteReminder() {

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (canVote(player, true)) {
				if (plugin.players.get(player.getName()).getSettings().get("reward_reminder").getValue() == 1) {
					for (String voteMessage : config.getReminderVoteMessages()) {
						player.sendMessage(ChatColor.translateAlternateColorCodes ('&', voteMessage));
					}
				}
			}
		}
	}

	public void startDailyReminder() {
		this.dailyReminder = new BukkitRunnable() {
			public void run() {
				sendDailyReminder();
			}
		}.runTaskTimer(plugin, 20 * config.getReminderDailyInterval(), 20 * config.getReminderDailyInterval());
	}

	public void stopDailyReminder() {
		if (this.dailyReminder != null) {
			this.dailyReminder.cancel();
			this.dailyReminder = null;
		}
	}

	public void sendDailyReminder() {

		long timestamp = System.currentTimeMillis() / 1000;

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (plugin.rewardUtil.getTimeSinceLastDaily(player, timestamp, false) >= 86400) {
				if (plugin.players.get(player.getName()).getSettings().get("reward_reminder").getValue() == 1) {
					for (String dailyMessage : config.getReminderDailyMessages()) {
						player.sendMessage(ChatColor.translateAlternateColorCodes ('&', dailyMessage));
					}
				}
			}
		}
	}
}
