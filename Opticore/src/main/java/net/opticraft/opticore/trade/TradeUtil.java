package net.opticraft.opticore.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class TradeUtil {

	public Main plugin;

	public Util util;

	public TradeUtil(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("trades")) {

			Set<String> players = plugin.getConfig().getConfigurationSection("trades").getKeys(false);
			
			System.out.println("players:" + players);
			
			for (String uuid : players) {

				Set<String> trades = plugin.getConfig().getConfigurationSection("trades." + uuid).getKeys(false);
				
				System.out.println(uuid + " trades:" + trades);

				if (!trades.isEmpty()) {

					List<Trade> tradeList = new ArrayList<Trade>();

					for (String trade : trades) {

						String name = trade;
						
						ItemStack hasItem = plugin.getConfig().getItemStack("trades." + uuid + "." + trade + ".has");

						String[] wantsParts = plugin.getConfig().getString("trades." + uuid + "." + trade + ".wants").split(":");
						String wantsItem = wantsParts[0];
						int wantsAmount = Integer.parseInt(wantsParts[1]);

						long length = plugin.getConfig().getLong("trades." + uuid + "." + trade + ".length");
						String description = plugin.getConfig().getString("trades." + uuid + "." + trade + ".description");
						int status = plugin.getConfig().getInt("trades." + uuid + "." + trade + ".status");

						tradeList.add(new Trade(name, hasItem, wantsItem, wantsAmount, length, description, status));
						
						System.out.println("trade:" + trade + ":" + uuid);
					}

					plugin.trades.put(uuid, tradeList);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public String resolveTradeCase(String target, String trade) {

		// Get offline player uuid
		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
		if (plugin.getConfig().isSet("trades." + uuid)) {
			
			// Get player trades from trades config section
			Set<String> trades = plugin.getConfig().getConfigurationSection("trades." + uuid).getKeys(false);

			// Loop through trades and get trade proper case
			for (String tradeName : trades) {
				if (tradeName.equalsIgnoreCase(trade)) {
					trade = tradeName;
				}
			}
		}
		
		return trade;
	}
	
	@SuppressWarnings("deprecation")
	public Trade getTrade(String target, String tradeName) {
		
		Trade t = null;
		
		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
		tradeName = resolveTradeCase(target, tradeName);
		
		if (plugin.trades.containsKey(uuid)) {
			
			for (Trade trade : plugin.trades.get(uuid)) {
				
				if (trade.getName().equals(tradeName)) {
					
					t = trade;
				}
			}
		}
		return t;
	}
	
	public boolean tradeExists(String target, String trade) {
		
		trade = resolveTradeCase(target, trade);
		
		if (getTrade(target, trade) != null) {
			return true;
		}
		
		return false;
	}

	public void sellTrade(Player player, String trade, ItemStack hasItem, String wantsItem, int wantsAmount, long expiry, String description) {

		String uuid = player.getUniqueId().toString();
		
		trade = resolveTradeCase(player.getName(), trade);

		plugin.getConfig().set("trades." + uuid + "." + trade, "");
		plugin.getConfig().set("trades." + uuid + "." + trade + ".has", hasItem);
		plugin.getConfig().set("trades." + uuid + "." + trade + ".wants", wantsItem + ":" + wantsAmount);
		plugin.getConfig().set("trades." + uuid + "." + trade + ".length", expiry);
		plugin.getConfig().set("trades." + uuid + "." + trade + ".description", description);
		plugin.getConfig().set("trades." + uuid + "." + trade + ".status", 0);
		plugin.saveConfig();
		
		if (!plugin.trades.containsKey(uuid)) {
			plugin.trades.put(uuid, new ArrayList<Trade>());
		}
		
		plugin.trades.get(uuid).add(new Trade(trade, hasItem, wantsItem, wantsAmount, expiry, description, 0));

		player.getInventory().removeItem(new ItemStack(hasItem));
	}
	
	public void deleteTrade(Player player, String tradeName) {
		
		String uuid = player.getUniqueId().toString();
		
		tradeName = resolveTradeCase(player.getName(), tradeName);
		
		Trade trade = getTrade(player.getName(), tradeName);
		ItemStack hasItem = trade.getHasItem();
		
		player.getInventory().addItem(new ItemStack(hasItem));
		
		plugin.getConfig().set("trades." + uuid + "." + tradeName, null);
		plugin.saveConfig();

		plugin.trades.get(uuid).remove(trade);
	}
	
	@SuppressWarnings("deprecation")
	public void buyTrade(Player player, String target, String tradeName) {
		
		String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();
		
		tradeName = resolveTradeCase(target, tradeName);
		
		Trade trade = getTrade(target, tradeName);
		ItemStack hasItem = trade.getHasItem();
		String wantsItem = trade.getWantsItem();
		int wantsAmount = trade.getWantsAmount();
		
		player.getInventory().removeItem(new ItemStack(Material.valueOf(wantsItem.toUpperCase()), wantsAmount));
		
		player.getInventory().addItem(new ItemStack(hasItem));
		
		if (plugin.getServer().getPlayer(target) != null) {
			
			Player targetPlayer = plugin.getServer().getPlayer(target);
			
			targetPlayer.getInventory().addItem(new ItemStack(Material.valueOf(wantsItem.toUpperCase()), wantsAmount));
			
			util.sendStyledMessage(targetPlayer, null, "GREEN", "T", "GOLD", "Your trade '" + tradeName + "' was claimed by '" + player.getName() + "'.");
			util.sendStyledMessage(targetPlayer, null, "GREEN", "T", "GOLD", "Gave " + wantsAmount + " of " + wantsItem);
			
			plugin.getConfig().set("trades." + uuid + "." + tradeName, null);
			plugin.saveConfig();

			plugin.trades.get(uuid).remove(trade);
			
		} else {
			plugin.getConfig().set("trades." + uuid + "." + tradeName + ".status", 2);
			plugin.saveConfig();

			trade.setStatus(2);
		}
	}
}
