package net.opticraft.opticore.trade;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class TradeCommand implements CommandExecutor {

	public Main plugin;

	public TradeUtil tradeUtil;

	public GuiUtil guiUtil;

	public Util util;

	public TradeCommand(Main plugin) {
		this.plugin = plugin;
		this.tradeUtil = this.plugin.tradeUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("trade")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				String uuid = player.getUniqueId().toString();

				if (args.length == 0) {
					guiUtil.openGui(player, "trade", null);

				} else if (args.length == 1) {

					String subCommand = args[0];

					if (subCommand.equalsIgnoreCase("accept")) {

					} else if (subCommand.equalsIgnoreCase("deny")) {

					} else if (subCommand.equalsIgnoreCase("cancel")) {

					} else if (subCommand.equalsIgnoreCase("shop")) {
						guiUtil.openGui(player, "trade-shop", null);

					} else {

					}

				} else if (args.length == 2) {

					String subCommand = args[0];

					if (subCommand.equalsIgnoreCase("request")) {

					} else if (subCommand.equalsIgnoreCase("accept")) {

					} else if (subCommand.equalsIgnoreCase("deny")) {

					} else if (subCommand.equalsIgnoreCase("delete")) {

						if (player.hasPermission("opticore.trade.delete")) {

							String trade = args[1];

							if (plugin.tradeUtil.tradeExists(player.getName(), trade)) {
								
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Deleted trade '" + trade + "'.");

								if (plugin.tradeUtil.getTrade(player.getName(), trade).isActive() || plugin.tradeUtil.getTrade(player.getName(), trade).isExpired()) {
									tradeUtil.deleteTrade(player, trade);

								} else if (plugin.tradeUtil.getTrade(player.getName(), trade).isSold()) {
									
								}

							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Your trade '" + trade + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You don't have permission to perform that command.");
						}

					} else if (subCommand.equalsIgnoreCase("list")) {

					} else {

					}

				} else if (args.length == 3) {

					String subCommand = args[0];

					if (subCommand.equalsIgnoreCase("buy")) {

						if (player.hasPermission("opticore.trade.buy")) {

							String target = args[1];
							String tradeName = args[2];

							if (plugin.tradeUtil.tradeExists(target, tradeName) && !plugin.tradeUtil.getTrade(target, tradeName).isSold()) {

								Trade trade = plugin.tradeUtil.getTrade(target, tradeName);

								String wantsItem = trade.getWantsItem();
								int wantsAmount = trade.getWantsAmount();

								Material hasItem = trade.getHasItem().getType();
								int hasAmount = trade.getHasItem().getAmount();

								if (player.getInventory().contains(Material.valueOf(wantsItem.toUpperCase()), wantsAmount)) {

									// finally claim trade
									tradeUtil.buyTrade(player, target, tradeName);
									util.sendStyledMessage(player, null, "GREEN", "T", "GOLD", "Claimed trade '" + tradeName + "' from player '" + target + "'.");
									util.sendStyledMessage(player, null, "GREEN", "T", "GOLD", "Gave " + hasAmount + " of " + hasItem);

								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have enough of '" + wantsItem + "' to claim the trade '" + tradeName + "'.");
								}
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' does not have a trade called '" + tradeName + "'.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You don't have permission to perform that command.");
						}

					} else {

					}

				} else if (args.length >= 6) {

					String subCommand = args[0];
					String trade = args[1];
					ItemStack hasItem = player.getInventory().getItemInMainHand();
					String wantsItem = args[2];
					String wantsAmount = args[3];
					String lengthString = args[4];
					String description = StringUtils.join(args, ' ', 5, args.length);

					// /trade create <trade> <wants-item> <wants-amount> <length> <description>
					if (subCommand.equalsIgnoreCase("sell")) {

						if (player.hasPermission("opticore.trade.sell")) {

							if (!plugin.trades.containsKey(uuid) || plugin.trades.get(uuid).size() < plugin.config.getMarketMaxTrades()) {

								if (!plugin.tradeUtil.tradeExists(player.getName(), trade)) {

									if (!hasItem.getType().equals(Material.AIR)) {

										if (Material.getMaterial(wantsItem.toUpperCase()) != null) {

											if (plugin.util.isInt(wantsAmount)) {
												int wantsAmountInt = Integer.parseInt(wantsAmount);

												if (plugin.util.isValidTimeString(lengthString)) {

													long timestamp = System.currentTimeMillis() / 1000;
													long length = plugin.util.parse(lengthString);
													long expiry = timestamp + length;

													// finally create trade
													tradeUtil.sellTrade(player, trade, hasItem, wantsItem, wantsAmountInt, expiry, description);
													util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Created trade '" + trade + "'.");
													//player.sendMessage(ChatColor.GREEN + "Created trade '" + trade + "' wanting " + wantsAmount + " of " + wantsItem.toUpperCase() + " for " + hasItem.getAmount() + " of " + hasItem.getType().toString().toUpperCase() + " expiring on " + String.valueOf(expiry) + " with the description: " + description);

												} else {
													util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /trade sell <trade> <want-item> <want-amount> <length> <description>");
													util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You must specify a valid time for <length>");
													util.sendStyledMessage(player, null, "RED", "/", "GOLD", "For example: 7d or 4d10h30m");
												}
											} else {
												util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /trade sell <trade> <want-item> <want-amount> <length> <description>");
												util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You must specify a valid number for <want-amount>");
												util.sendStyledMessage(player, null, "RED", "/", "GOLD", "For example: 1 or 64");
											}
										} else {
											util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /trade sell <trade> <want-item> <want-amount> <length> <description>");
											util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You must specify a valid item name for <want-item>");
											util.sendStyledMessage(player, null, "RED", "/", "GOLD", "For example: emerald or blue_wool");
										}
									} else {
										util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You must hold an item in your hand to sell.");
									}
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You already have a trade called '" + trade + "'.");
								}
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no more shop trades remaining.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You don't have permission to perform that command.");
						}
					} else {

					}

				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /trade");
				}
			} else {
				plugin.util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
