package net.opticraft.opticore.rewards;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class RewardCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public RewardUtil rewardUtil;
	
	public Util util;

	public RewardCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.rewardUtil = this.plugin.rewardUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reward") || cmd.getName().equalsIgnoreCase("rewards")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "rewards", null);

				} else if (args.length == 1) {

					String subCommand = args[0];

					if (subCommand.equalsIgnoreCase("store") || subCommand.equalsIgnoreCase("buy")) {
						guiUtil.openGui(player, "store", null);

					} else if (subCommand.equalsIgnoreCase("points") || subCommand.equalsIgnoreCase("point")) {
						rewardUtil.showRewardPoints(player);

					} else if (subCommand.equalsIgnoreCase("daily")) {
						rewardUtil.giveDailyReward(player);
						
					} else if (subCommand.equalsIgnoreCase("vote")) {
						guiUtil.sendListAsMessage(sender, plugin.config.getVote());
						
					} else if (subCommand.equalsIgnoreCase("donate")) {
						guiUtil.sendListAsMessage(sender, plugin.config.getDonate());
						
					} else {
						util.sendStyledMessage(player, null, "RED", "R", "GOLD", "Incorrect syntax. Usage: /reward buy, daily, points, store");
					}

				} else if (args.length == 2) {

					String reward = args[1];

					if (args[0].equalsIgnoreCase("buy")) {

						if (rewardUtil.rewardExists(reward)) {

							int points = plugin.mysql.getUUIDColumnValue(player.getName(), "oc_points", "points");
							int cost = plugin.rewards.get(reward).getCost();

							if (points >= cost) {

								int size = plugin.rewards.get(reward).getItems().size();
								int freeSpace = 0;

								for (int i = 0; i <= 35; i++) {
									if (player.getInventory().getItem(i) == null) {
										freeSpace++;
									}
								}

								if (freeSpace >= size) {

									util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "Bought reward '" + reward + "' for " + cost + " reward points.");
									rewardUtil.giveReward(player, reward);
									rewardUtil.takeRewardPoints(player, cost);

									long timestamp = System.currentTimeMillis() / 1000;

									String server = plugin.config.getServerName();

									// Log reward to the database
									plugin.mysql.insert("oc_rewards", 
											Arrays.asList("uuid", "reward", "cost", "timestamp", "server"), 
											Arrays.asList(player.getUniqueId().toString(), reward, cost, timestamp, server));

								} else {
									util.sendStyledMessage(player, null, "RED", "R", "GOLD", "You need " + String.valueOf(size - freeSpace) + " more free slots to buy this reward.");
								}
							} else {
								util.sendStyledMessage(player, null, "RED", "R", "GOLD", "You need " + String.valueOf(cost - points) + " more reward points to buy this reward.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "R", "GOLD", "The reward '" + reward + "' does not exist.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "R", "GOLD", "Incorrect syntax. Usage: /rewards buy <reward>");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "R", "GOLD", "Incorrect syntax. Usage: /reward buy, daily, points, store");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
