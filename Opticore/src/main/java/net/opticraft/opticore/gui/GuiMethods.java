package net.opticraft.opticore.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;
import net.opticraft.opticore.warp.WarpInfo;
import net.opticraft.opticore.world.WorldInfo;

public class GuiMethods {

	public Main plugin;

	public Config config;
	public BungeecordMethods bungeecordMethods;

	public GuiMethods(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
	}

	@SuppressWarnings("deprecation")
	public ItemStack item(String item, String name, List<String> lore, boolean glow, int amount) {

		ItemStack itemStack;

		String[] itemPart = item.split(":");
		String itemMaterialString = itemPart[0];
		String itemDamageString = itemPart[1];

		if (itemMaterialString.startsWith("skull")) {
			itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

			SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

			meta.setOwner(itemDamageString);
			meta.setDisplayName(name);

			List<String> loreList = new ArrayList<String>();
			loreList.addAll(lore);
			meta.setLore(loreList);

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			if (glow == true) {
				itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}

			itemStack.setAmount(amount);

			itemStack.setItemMeta(meta);

		} else {

			Material itemMaterial = Material.valueOf(itemMaterialString.toUpperCase());
			int itemDamage = Integer.parseInt(itemDamageString);

			itemStack = new ItemStack(itemMaterial, 1, (short) itemDamage);

			ItemMeta meta = itemStack.getItemMeta();

			meta.setDisplayName(name);

			List<String> loreList = new ArrayList<String>();
			loreList.addAll(lore);
			meta.setLore(loreList);

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			if (glow == true) {
				itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}

			itemStack.setAmount(amount);

			itemStack.setItemMeta(meta);
		}

		return itemStack;
	}

	public void toolbarModule(Inventory inventory, 
			String toolbarToolbarItem, String toolbarToolbarName, List<String> toolbarToolbarLore, 
			String toolbarBackItem, String toolbarBackName, List<String> toolbarBackLore, 
			String toolbarSearchItem, String toolbarSearchName, List<String> toolbarSearchLore, 
			String toolbarPageItem, String toolbarPageName, List<String> toolbarPageLore, 
			String toolbarExitItem, String toolbarExitName, List<String> toolbarExitLore) {

		// Toolbar: Toolbar
		for (int i = 1; i < 10; i++) {
			itemModule(inventory, i, toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, null);
		}

		// Toolbar: Back
		int toolbarBackSlot = 1;
		itemModule(inventory, toolbarBackSlot, toolbarBackItem, toolbarBackName, toolbarBackLore, null);

		// Toolbar: Search
		int toolbarSearchSlot = 2;
		itemModule(inventory, toolbarSearchSlot, toolbarSearchItem, toolbarSearchName, toolbarSearchLore, null);

		// Toolbar: Page
		int toolbarPageSlot = 5;
		itemModule(inventory, toolbarPageSlot, toolbarPageItem, toolbarPageName, toolbarPageLore, null);

		// Toolbar: Exit
		int toolbarExitSlot = 9;
		itemModule(inventory, toolbarExitSlot, toolbarExitItem, toolbarExitName, toolbarExitLore, null);
	}

	public void itemModule(Inventory inventory, int itemSlot, String item, String itemName, List<String> itemLore, String server) {

		itemSlot = itemSlot - 1;
		itemName = ChatColor.translateAlternateColorCodes('&', itemName);

		List<String> itemLoreList = new ArrayList<String>();

		for (String itemLoreLine : itemLore) {

			if (itemLoreLine.contains("%player_count%") && server != null) {

				String playerCountString = "0 players online";

				if (plugin.playerCount.containsKey(server)) {

					int playerCount = bungeecordMethods.getServerPlayerCount(server);

					if (playerCount == 1) {
						playerCountString = "1 player online";
					} else {
						playerCountString = Integer.toString(playerCount) + " players online";
					}
				}
				itemLoreLine = itemLoreLine.replace("%player_count%", playerCountString);
			}

			if (itemLoreLine.contains("%player_list%") && server != null) {

				String playerListString = "None.";

				if (plugin.playerList.containsKey(server) && !plugin.playerList.get(server).isEmpty()) {

					playerListString = bungeecordMethods.getServerPlayerList(server);
				}
				itemLoreLine = itemLoreLine.replace("%player_list%", playerListString);
			}
			itemLoreList.add(ChatColor.translateAlternateColorCodes('&', itemLoreLine));
		}

		inventory.setItem(itemSlot, item(item, itemName, itemLoreList, false, 1));
	}

	public void openHomeGui(Player player) {

		int inventoryRows = config.getGuiHomeSettingsInventorySize() * 9;
		String inventoryName = config.getGuiHomeSettingsInventoryName();
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiHomeSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiHomeSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarBackLore = new ArrayList<String>();
		//toolbarBackLore.add("back");

		String toolbarPageItem = config.getGuiHomeSettingsToolbarPageItem();
		String toolbarPageName = ChatColor.WHITE + "Home";
		List<String> toolbarPageLore = new ArrayList<String>();
		//toolbarBackLore.add("home");

		String toolbarExitItem = config.getGuiHomeSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: servers
		int serversSlot = config.getGuiHomeContentsServersSlot();
		String serversItem = config.getGuiHomeContentsServersItem();
		String serversName = config.getGuiHomeContentsServersName();
		List<String> serversLore = config.getGuiHomeContentsServersLore();
		itemModule(inventory, serversSlot, serversItem, serversName, serversLore, null);

		// Item: players
		int playersSlot = config.getGuiHomeContentsPlayersSlot();
		String playersItem = config.getGuiHomeContentsPlayersItem();
		String playersName = config.getGuiHomeContentsPlayersName();
		List<String> playersLore = config.getGuiHomeContentsPlayersLore();
		itemModule(inventory, playersSlot, playersItem, playersName, playersLore, null);

		// Item: friends
		int friendsSlot = config.getGuiHomeContentsFriendsSlot();
		String friendsItem = config.getGuiHomeContentsFriendsItem();
		String friendsName = config.getGuiHomeContentsFriendsName();
		List<String> friendsLore = config.getGuiHomeContentsFriendsLore();
		itemModule(inventory, friendsSlot, friendsItem, friendsName, friendsLore, null);

		// Item: rewards
		int rewardsSlot = config.getGuiHomeContentsRewardsSlot();
		String rewardsItem = config.getGuiHomeContentsRewardsItem();
		String rewardsName = config.getGuiHomeContentsRewardsName();
		List<String> rewardsLore = config.getGuiHomeContentsRewardsLore();
		itemModule(inventory, rewardsSlot, rewardsItem, rewardsName, rewardsLore, null);

		// Item: settings
		int settingsSlot = config.getGuiHomeContentsSettingsSlot();
		String settingsItem = config.getGuiHomeContentsSettingsItem();
		String settingsName = config.getGuiHomeContentsSettingsName();
		List<String> settingsLore = config.getGuiHomeContentsSettingsLore();
		itemModule(inventory, settingsSlot, settingsItem, settingsName, settingsLore, null);

		// Item: staff
		int staffSlot = config.getGuiHomeContentsStaffSlot();
		String staffItem = config.getGuiHomeContentsStaffItem();
		String staffName = config.getGuiHomeContentsStaffName();
		List<String> staffLore = config.getGuiHomeContentsStaffLore();
		itemModule(inventory, staffSlot, staffItem, staffName, staffLore, null);

		// Item: worlds
		int worldsSlot = config.getGuiHomeContentsWorldsSlot();
		String worldsItem = config.getGuiHomeContentsWorldsItem();
		String worldsName = config.getGuiHomeContentsWorldsName();
		List<String> worldsLore = config.getGuiHomeContentsWorldsLore();
		itemModule(inventory, worldsSlot, worldsItem, worldsName, worldsLore, null);

		// Item: warps
		int warpsSlot = config.getGuiHomeContentsWarpsSlot();
		String warpsItem = config.getGuiHomeContentsWarpsItem();
		String warpsName = config.getGuiHomeContentsWarpsName();
		List<String> warpsLore = config.getGuiHomeContentsWarpsLore();
		itemModule(inventory, warpsSlot, warpsItem, warpsName, warpsLore, null);

		// Item: applications
		int applicationsSlot = config.getGuiHomeContentsApplicationsSlot();
		String applicationsItem = config.getGuiHomeContentsApplicationsItem();
		String applicationsName = config.getGuiHomeContentsApplicationsName();
		List<String> applicationsLore = config.getGuiHomeContentsApplicationsLore();
		itemModule(inventory, applicationsSlot, applicationsItem, applicationsName, applicationsLore, null);

		// Item: rules
		int rulesSlot = config.getGuiHomeContentsRulesSlot();
		String rulesItem = config.getGuiHomeContentsRulesItem();
		String rulesName = config.getGuiHomeContentsRulesName();
		List<String> rulesLore = config.getGuiHomeContentsRulesLore();
		itemModule(inventory, rulesSlot, rulesItem, rulesName, rulesLore, null);

		// Item: ranks
		int ranksSlot = config.getGuiHomeContentsRanksSlot();
		String ranksItem = config.getGuiHomeContentsRanksItem();
		String ranksName = config.getGuiHomeContentsRanksName();
		List<String> ranksLore = config.getGuiHomeContentsRanksLore();
		itemModule(inventory, ranksSlot, ranksItem, ranksName, ranksLore, null);

		player.openInventory(inventory);
	}

	public void openServersGui(Player player) {

		int inventoryRows = config.getGuiServersSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiServersSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiServersSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiServersSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = config.getGuiServersSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsServersName();
		List<String> toolbarPageLore = config.getGuiHomeContentsServersLore();

		String toolbarExitItem = config.getGuiServersSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: hub
		int hubSlot = config.getGuiServersContentsHubSlot();
		String hubItem = config.getGuiServersContentsHubItem();
		String hubName = config.getGuiServersContentsHubName();
		List<String> hubLore = config.getGuiServersContentsHubLore();
		itemModule(inventory, hubSlot, hubItem, hubName, hubLore, "hub");

		// Item: survival
		int survivalSlot = config.getGuiServersContentsSurvivalSlot();
		String survivalItem = config.getGuiServersContentsSurvivalItem();
		String survivalName = config.getGuiServersContentsSurvivalName();
		List<String> survivalLore = config.getGuiServersContentsSurvivalLore();
		itemModule(inventory, survivalSlot, survivalItem, survivalName, survivalLore, "survival");

		// Item: creative
		int creativeSlot = config.getGuiServersContentsCreativeSlot();
		String creativeItem = config.getGuiServersContentsCreativeItem();
		String creativeName = config.getGuiServersContentsCreativeName();
		List<String> creativeLore = config.getGuiServersContentsCreativeLore();
		itemModule(inventory, creativeSlot, creativeItem, creativeName, creativeLore, "creative");

		// Item: quest
		int questSlot = config.getGuiServersContentsQuestSlot();
		String questItem = config.getGuiServersContentsQuestItem();
		String questName = config.getGuiServersContentsQuestName();
		List<String> questLore = config.getGuiServersContentsQuestLore();
		itemModule(inventory, questSlot, questItem, questName, questLore, "quest");

		// Item: legacy
		int legacySlot = config.getGuiServersContentsLegacySlot();
		String legacyItem = config.getGuiServersContentsLegacyItem();
		String legacyName = config.getGuiServersContentsLegacyName();
		List<String> legacyLore = config.getGuiServersContentsLegacyLore();
		itemModule(inventory, legacySlot, legacyItem, legacyName, legacyLore, null);

		player.openInventory(inventory);
	}

	public void openPlayersGui(Player player, String search) {

		int inventoryRows = config.getGuiPlayersSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiPlayersSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiPlayersSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiPlayersSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarSearchItem = config.getGuiPlayersSettingsToolbarSearchItem();
		String toolbarSearchName = ChatColor.WHITE + "Search";
		List<String> toolbarSearchLore = new ArrayList<String>();
		toolbarSearchLore.add(ChatColor.GOLD + "Click to search username");

		String toolbarPageItem = config.getGuiPlayersSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsPlayersName();
		List<String> toolbarPageLore = config.getGuiHomeContentsPlayersLore();

		String toolbarExitItem = config.getGuiPlayersSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarSearchItem, toolbarSearchName, toolbarSearchLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		int slot = 10;
		for (Player online : plugin.getServer().getOnlinePlayers()) {

			String onlineName = online.getName();
			
			String itemItem = "skull:" + onlineName;
			String itemName = ChatColor.WHITE + onlineName;
			List<String> itemLore = new ArrayList<String>();

			if (search != null) {
				if (onlineName.toLowerCase().startsWith(search.toLowerCase())) {
					itemModule(inventory, slot, itemItem, itemName, itemLore, null);
					slot++;
				}
			} else {
				itemModule(inventory, slot, itemItem, itemName, itemLore, null);
				slot++;
			}
		}

		player.openInventory(inventory);
	}

	public void openFriendsGui(Player player, String targetName) {

		int inventoryRows = config.getGuiFriendsSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiFriendsSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiFriendsSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiFriendsSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarSearchItem = config.getGuiFriendsSettingsToolbarSearchItem();
		String toolbarSearchName = ChatColor.WHITE + "Search";
		List<String> toolbarSearchLore = new ArrayList<String>();
		toolbarSearchLore.add(ChatColor.GOLD + "Click to search username");

		String toolbarPageItem = config.getGuiFriendsSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsFriendsName();
		List<String> toolbarPageLore = config.getGuiHomeContentsFriendsLore();

		String toolbarExitItem = config.getGuiFriendsSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarSearchItem, toolbarSearchName, toolbarSearchLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		int i = 10;
		for (Player online : plugin.getServer().getOnlinePlayers()) {
			String onlineName = online.getName();
			int itemSlot = i;
			String itemItem = "skull:" + onlineName;
			String itemName = ChatColor.WHITE + onlineName;
			List<String> itemLore = new ArrayList<String>();
			//itemLore.add(onlineName);
			itemModule(inventory, itemSlot, itemItem, itemName, itemLore, null);
			i++;
		}

		player.openInventory(inventory);
	}

	public void openRewardsGui(Player player) {
		int inventoryRows = config.getGuiRewardsSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiRewardsSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiRewardsSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = config.getGuiRewardsSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsRewardsName();
		List<String> toolbarPageLore = config.getGuiHomeContentsRewardsLore();

		String toolbarExitItem = config.getGuiRewardsSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: points
		int pointsSlot = config.getGuiRewardsContentsPointsSlot();
		String pointsItem = config.getGuiRewardsContentsPointsItem();
		String pointsName = config.getGuiRewardsContentsPointsName();
		List<String> pointsLore = config.getGuiRewardsContentsPointsLore();
		itemModule(inventory, pointsSlot, pointsItem, pointsName, pointsLore, null);

		// Item: vote
		int voteSlot = config.getGuiRewardsContentsVoteSlot();
		String voteItem = config.getGuiRewardsContentsVoteItem();
		String voteName = config.getGuiRewardsContentsVoteName();
		List<String> voteLore = config.getGuiRewardsContentsVoteLore();
		itemModule(inventory, voteSlot, voteItem, voteName, voteLore, null);

		// Item: donate
		int donateSlot = config.getGuiRewardsContentsDonateSlot();
		String donateItem = config.getGuiRewardsContentsDonateItem();
		String donateName = config.getGuiRewardsContentsDonateName();
		List<String> donateLore = config.getGuiRewardsContentsDonateLore();
		itemModule(inventory, donateSlot, donateItem, donateName, donateLore, null);

		// Item: challenges
		int challengesSlot = config.getGuiRewardsContentsChallengesSlot();
		String challengesItem = config.getGuiRewardsContentsChallengesItem();
		String challengesName = config.getGuiRewardsContentsChallengesName();
		List<String> challengesLore = config.getGuiRewardsContentsChallengesLore();
		itemModule(inventory, challengesSlot, challengesItem, challengesName, challengesLore, null);

		// Item: daily
		int dailySlot = config.getGuiRewardsContentsDailySlot();
		String dailyItem = config.getGuiRewardsContentsDailyItem();
		String dailyName = config.getGuiRewardsContentsDailyName();
		List<String> dailyLore = config.getGuiRewardsContentsDailyLore();
		itemModule(inventory, dailySlot, dailyItem, dailyName, dailyLore, null);

		player.openInventory(inventory);
	}

	public void openPointsGui(Player player) {

	}

	public void openVoteGui(Player player) {

	}

	public void openDonateGui(Player player) {

	}

	public void openChallengesGui(Player player) {

	}

	public void openDailyGui(Player player) {

	}

	public void openSettingsGui(Player player) {

		String uuid = player.getUniqueId().toString();

		int inventoryRows = config.getGuiSettingsSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiSettingsSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiSettingsSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = config.getGuiSettingsSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsSettingsName();
		List<String> toolbarPageLore = config.getGuiHomeContentsSettingsLore();

		String toolbarExitItem = config.getGuiSettingsSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: Connect-Disconnect
		int connectDisconnectSlot = config.getGuiSettingsContentsConnectDisconnectSlot();
		String connectDisconnectItem = config.getGuiSettingsContentsConnectDisconnectItem();
		String connectDisconnectName = config.getGuiSettingsContentsConnectDisconnectName();
		List<String> connectDisconnectLore = config.getGuiSettingsContentsConnectDisconnectLore();
		itemModule(inventory, connectDisconnectSlot, connectDisconnectItem, connectDisconnectName, connectDisconnectLore, null);

		// Item: Connect-Disconnect-Control
		int connectDisconnectControlSlot = config.getGuiSettingsContentsConnectDisconnectControlSlot();

		String connectDisconnectControlItemOn = config.getGuiSettingsContentsConnectDisconnectControlItemOn();
		String connectDisconnectControlNameOn = config.getGuiSettingsContentsConnectDisconnectControlNameOn();
		List<String> connectDisconnectControlLoreOn = config.getGuiSettingsContentsConnectDisconnectControlLoreOn();
		String connectDisconnectControlItemOff = config.getGuiSettingsContentsConnectDisconnectControlItemOff();
		String connectDisconnectControlNameOff = config.getGuiSettingsContentsConnectDisconnectControlNameOff();
		List<String> connectDisconnectControlLoreOff = config.getGuiSettingsContentsConnectDisconnectControlLoreOff();

		if (plugin.players.get(uuid).getSettingsConnectDisconnect() == 0) {
			itemModule(inventory, connectDisconnectControlSlot, connectDisconnectControlItemOff, connectDisconnectControlNameOff, connectDisconnectControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsConnectDisconnect() == 1) {
			itemModule(inventory, connectDisconnectControlSlot, connectDisconnectControlItemOn, connectDisconnectControlNameOn, connectDisconnectControlLoreOn, null);
		}

		// Item: Server-Change
		int serverChangeSlot = config.getGuiSettingsContentsServerChangeSlot();
		String serverChangeItem = config.getGuiSettingsContentsServerChangeItem();
		String serverChangeName = config.getGuiSettingsContentsServerChangeName();
		List<String> serverChangeLore = config.getGuiSettingsContentsServerChangeLore();
		itemModule(inventory, serverChangeSlot, serverChangeItem, serverChangeName, serverChangeLore, null);

		// Item: Server-Change-Control
		int serverChangeControlSlot = config.getGuiSettingsContentsServerChangeControlSlot();

		String serverChangeControlItemOn = config.getGuiSettingsContentsServerChangeControlItemOn();
		String serverChangeControlNameOn = config.getGuiSettingsContentsServerChangeControlNameOn();
		List<String> serverChangeControlLoreOn = config.getGuiSettingsContentsServerChangeControlLoreOn();
		String serverChangeControlItemOff = config.getGuiSettingsContentsServerChangeControlItemOff();
		String serverChangeControlNameOff = config.getGuiSettingsContentsServerChangeControlNameOff();
		List<String> serverChangeControlLoreOff = config.getGuiSettingsContentsServerChangeControlLoreOff();

		if (plugin.players.get(uuid).getSettingsServerChange() == 0) {
			itemModule(inventory, serverChangeControlSlot, serverChangeControlItemOff, serverChangeControlNameOff, serverChangeControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsServerChange() == 1) {
			itemModule(inventory, serverChangeControlSlot, serverChangeControlItemOn, serverChangeControlNameOn, serverChangeControlLoreOn, null);
		}

		// Item: Player-Chat
		int playerChatSlot = config.getGuiSettingsContentsPlayerChatSlot();
		String playerChatItem = config.getGuiSettingsContentsPlayerChatItem();
		String playerChatName = config.getGuiSettingsContentsPlayerChatName();
		List<String> playerChatLore = config.getGuiSettingsContentsPlayerChatLore();
		itemModule(inventory, playerChatSlot, playerChatItem, playerChatName, playerChatLore, null);

		// Item: Player-Chat-Control
		int playerChatControlSlot = config.getGuiSettingsContentsPlayerChatControlSlot();

		String playerChatControlItemOn = config.getGuiSettingsContentsPlayerChatControlItemOn();
		String playerChatControlNameOn = config.getGuiSettingsContentsPlayerChatControlNameOn();
		List<String> playerChatControlLoreOn = config.getGuiSettingsContentsPlayerChatControlLoreOn();
		String playerChatControlItemOff = config.getGuiSettingsContentsPlayerChatControlItemOff();
		String playerChatControlNameOff = config.getGuiSettingsContentsPlayerChatControlNameOff();
		List<String> playerChatControlLoreOff = config.getGuiSettingsContentsPlayerChatControlLoreOff();

		if (plugin.players.get(uuid).getSettingsPlayerChat() == 0) {
			itemModule(inventory, playerChatControlSlot, playerChatControlItemOff, playerChatControlNameOff, playerChatControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsPlayerChat() == 1) {
			itemModule(inventory, playerChatControlSlot, playerChatControlItemOn, playerChatControlNameOn, playerChatControlLoreOn, null);
		}

		// Item: Server-Announcement
		int serverAnnouncementSlot = config.getGuiSettingsContentsServerAnnouncementSlot();
		String serverAnnouncementItem = config.getGuiSettingsContentsServerAnnouncementItem();
		String serverAnnouncementName = config.getGuiSettingsContentsServerAnnouncementName();
		List<String> serverAnnouncementLore = config.getGuiSettingsContentsServerAnnouncementLore();
		itemModule(inventory, serverAnnouncementSlot, serverAnnouncementItem, serverAnnouncementName, serverAnnouncementLore, null);

		// Item: Server-Announcement-Control
		int serverAnnouncementControlSlot = config.getGuiSettingsContentsServerAnnouncementControlSlot();

		String serverAnnouncementControlItemOn = config.getGuiSettingsContentsServerAnnouncementControlItemOn();
		String serverAnnouncementControlNameOn = config.getGuiSettingsContentsServerAnnouncementControlNameOn();
		List<String> serverAnnouncementControlLoreOn = config.getGuiSettingsContentsServerAnnouncementControlLoreOn();
		String serverAnnouncementControlItemOff = config.getGuiSettingsContentsServerAnnouncementControlItemOff();
		String serverAnnouncementControlNameOff = config.getGuiSettingsContentsServerAnnouncementControlNameOff();
		List<String> serverAnnouncementControlLoreOff = config.getGuiSettingsContentsServerAnnouncementControlLoreOff();

		if (plugin.players.get(uuid).getSettingsServerAnnouncement() == 0) {
			itemModule(inventory, serverAnnouncementControlSlot, serverAnnouncementControlItemOff, serverAnnouncementControlNameOff, serverAnnouncementControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsServerAnnouncement() == 1) {
			itemModule(inventory, serverAnnouncementControlSlot, serverAnnouncementControlItemOn, serverAnnouncementControlNameOn, serverAnnouncementControlLoreOn, null);
		}

		// Item: Friend-Request
		int friendRequestSlot = config.getGuiSettingsContentsFriendRequestSlot();
		String friendRequestItem = config.getGuiSettingsContentsFriendRequestItem();
		String friendRequestName = config.getGuiSettingsContentsFriendRequestName();
		List<String> friendRequestLore = config.getGuiSettingsContentsFriendRequestLore();
		itemModule(inventory, friendRequestSlot, friendRequestItem, friendRequestName, friendRequestLore, null);

		// Item: Friend-Request-Control
		int friendRequestControlSlot = config.getGuiSettingsContentsFriendRequestControlSlot();

		String friendRequestControlItemOn = config.getGuiSettingsContentsFriendRequestControlItemOn();
		String friendRequestControlNameOn = config.getGuiSettingsContentsFriendRequestControlNameOn();
		List<String> friendRequestControlLoreOn = config.getGuiSettingsContentsFriendRequestControlLoreOn();
		String friendRequestControlItemOff = config.getGuiSettingsContentsFriendRequestControlItemOff();
		String friendRequestControlNameOff = config.getGuiSettingsContentsFriendRequestControlNameOff();
		List<String> friendRequestControlLoreOff = config.getGuiSettingsContentsFriendRequestControlLoreOff();

		if (plugin.players.get(uuid).getSettingsFriendRequest() == 0) {
			itemModule(inventory, friendRequestControlSlot, friendRequestControlItemOff, friendRequestControlNameOff, friendRequestControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsFriendRequest() == 1) {
			itemModule(inventory, friendRequestControlSlot, friendRequestControlItemOn, friendRequestControlNameOn, friendRequestControlLoreOn, null);
		}

		// Item: Direct-Message
		int directMessageSlot = config.getGuiSettingsContentsDirectMessageSlot();
		String directMessageItem = config.getGuiSettingsContentsDirectMessageItem();
		String directMessageName = config.getGuiSettingsContentsDirectMessageName();
		List<String> directMessageLore = config.getGuiSettingsContentsDirectMessageLore();
		itemModule(inventory, directMessageSlot, directMessageItem, directMessageName, directMessageLore, null);

		// Item: Direct-Message-Control
		int directMessageControlSlot = config.getGuiSettingsContentsDirectMessageControlSlot();

		String directMessageControlItemOn = config.getGuiSettingsContentsDirectMessageControlItemOn();
		String directMessageControlNameOn = config.getGuiSettingsContentsDirectMessageControlNameOn();
		List<String> directMessageControlLoreOn = config.getGuiSettingsContentsDirectMessageControlLoreOn();
		String directMessageControlItemOff = config.getGuiSettingsContentsDirectMessageControlItemOff();
		String directMessageControlNameOff = config.getGuiSettingsContentsDirectMessageControlNameOff();
		List<String> directMessageControlLoreOff = config.getGuiSettingsContentsDirectMessageControlLoreOff();
		String directMessageControlItemFriend = config.getGuiSettingsContentsDirectMessageControlItemFriend();
		String directMessageControlNameFriend = config.getGuiSettingsContentsDirectMessageControlNameFriend();
		List<String> directMessageControlLoreFriend = config.getGuiSettingsContentsDirectMessageControlLoreFriend();

		if (plugin.players.get(uuid).getSettingsDirectMessage() == 0) {
			itemModule(inventory, directMessageControlSlot, directMessageControlItemOff, directMessageControlNameOff, directMessageControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsDirectMessage() == 1) {
			itemModule(inventory, directMessageControlSlot, directMessageControlItemOn, directMessageControlNameOn, directMessageControlLoreOn, null);
		} else if (plugin.players.get(uuid).getSettingsDirectMessage() == 2) {
			itemModule(inventory, directMessageControlSlot, directMessageControlItemFriend, directMessageControlNameFriend, directMessageControlLoreFriend, null);
		}

		// Item: Teleport-Request
		int teleportRequestSlot = config.getGuiSettingsContentsTeleportRequestSlot();
		String teleportRequestItem = config.getGuiSettingsContentsTeleportRequestItem();
		String teleportRequestName = config.getGuiSettingsContentsTeleportRequestName();
		List<String> teleportRequestLore = config.getGuiSettingsContentsTeleportRequestLore();
		itemModule(inventory, teleportRequestSlot, teleportRequestItem, teleportRequestName, teleportRequestLore, null);

		// Item: Teleport-Request-Control
		int teleportRequestControlSlot = config.getGuiSettingsContentsTeleportRequestControlSlot();

		String teleportRequestControlItemOn = config.getGuiSettingsContentsTeleportRequestControlItemOn();
		String teleportRequestControlNameOn = config.getGuiSettingsContentsTeleportRequestControlNameOn();
		List<String> teleportRequestControlLoreOn = config.getGuiSettingsContentsTeleportRequestControlLoreOn();
		String teleportRequestControlItemOff = config.getGuiSettingsContentsTeleportRequestControlItemOff();
		String teleportRequestControlNameOff = config.getGuiSettingsContentsTeleportRequestControlNameOff();
		List<String> teleportRequestControlLoreOff = config.getGuiSettingsContentsTeleportRequestControlLoreOff();
		String teleportRequestControlItemFriend = config.getGuiSettingsContentsTeleportRequestControlItemFriend();
		String teleportRequestControlNameFriend = config.getGuiSettingsContentsTeleportRequestControlNameFriend();
		List<String> teleportRequestControlLoreFriend = config.getGuiSettingsContentsTeleportRequestControlLoreFriend();

		if (plugin.players.get(uuid).getSettingsTeleportRequest() == 0) {
			itemModule(inventory, teleportRequestControlSlot, teleportRequestControlItemOff, teleportRequestControlNameOff, teleportRequestControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsTeleportRequest() == 1) {
			itemModule(inventory, teleportRequestControlSlot, teleportRequestControlItemOn, teleportRequestControlNameOn, teleportRequestControlLoreOn, null);
		} else if (plugin.players.get(uuid).getSettingsTeleportRequest() == 2) {
			itemModule(inventory, teleportRequestControlSlot, teleportRequestControlItemFriend, teleportRequestControlNameFriend, teleportRequestControlLoreFriend, null);
		}

		// Item: Spectate-Request
		int spectateRequestSlot = config.getGuiSettingsContentsSpectateRequestSlot();
		String spectateRequestItem = config.getGuiSettingsContentsSpectateRequestItem();
		String spectateRequestName = config.getGuiSettingsContentsSpectateRequestName();
		List<String> spectateRequestLore = config.getGuiSettingsContentsSpectateRequestLore();
		itemModule(inventory, spectateRequestSlot, spectateRequestItem, spectateRequestName, spectateRequestLore, null);

		// Item: Spectate-Request-Control
		int spectateRequestControlSlot = config.getGuiSettingsContentsSpectateRequestControlSlot();

		String spectateRequestControlItemOn = config.getGuiSettingsContentsSpectateRequestControlItemOn();
		String spectateRequestControlNameOn = config.getGuiSettingsContentsSpectateRequestControlNameOn();
		List<String> spectateRequestControlLoreOn = config.getGuiSettingsContentsSpectateRequestControlLoreOn();
		String spectateRequestControlItemOff = config.getGuiSettingsContentsSpectateRequestControlItemOff();
		String spectateRequestControlNameOff = config.getGuiSettingsContentsSpectateRequestControlNameOff();
		List<String> spectateRequestControlLoreOff = config.getGuiSettingsContentsSpectateRequestControlLoreOff();
		String spectateRequestControlItemFriend = config.getGuiSettingsContentsSpectateRequestControlItemFriend();
		String spectateRequestControlNameFriend = config.getGuiSettingsContentsSpectateRequestControlNameFriend();
		List<String> spectateRequestControlLoreFriend = config.getGuiSettingsContentsSpectateRequestControlLoreFriend();

		if (plugin.players.get(uuid).getSettingsSpectateRequest() == 0) {
			itemModule(inventory, spectateRequestControlSlot, spectateRequestControlItemOff, spectateRequestControlNameOff, spectateRequestControlLoreOff, null);
		} else if (plugin.players.get(uuid).getSettingsSpectateRequest() == 1) {
			itemModule(inventory, spectateRequestControlSlot, spectateRequestControlItemOn, spectateRequestControlNameOn, spectateRequestControlLoreOn, null);
		} else if (plugin.players.get(uuid).getSettingsSpectateRequest() == 2) {
			itemModule(inventory, spectateRequestControlSlot, spectateRequestControlItemFriend, spectateRequestControlNameFriend, spectateRequestControlLoreFriend, null);
		}

		player.openInventory(inventory);
	}

	public void openWorldsGui(Player player) {

		int inventoryRows = config.getGuiWorldsSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiWorldsSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiWorldsSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiWorldsSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = config.getGuiWorldsSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsWorldsName();
		List<String> toolbarPageLore = config.getGuiHomeContentsWorldsLore();

		String toolbarExitItem = config.getGuiWorldsSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: worlds
		int slot = 10;
		for (Map.Entry<String, WorldInfo> worlds : plugin.worlds.entrySet()) {

			//key = entry.getKey();
			//value = entry.getValue();

			String world = worlds.getKey();
			
			String type = plugin.worlds.get(world).getType();
			type = type.substring(0, 1).toUpperCase() + type.substring(1);
			
			String item = plugin.worlds.get(world).getItem();
			
			String name = ChatColor.WHITE + world;
			
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + type + " world");
			
			itemModule(inventory, slot, item, name, lore, null);
			
			slot++;
		}

		player.openInventory(inventory);
	}

	public void openWarpsGui(Player player) {

		int inventoryRows = config.getGuiWarpsSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiWarpsSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiWarpsSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiWarpsSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = config.getGuiWarpsSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsWarpsName();
		List<String> toolbarPageLore = config.getGuiHomeContentsWarpsLore();

		String toolbarExitItem = config.getGuiWarpsSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: warps
		int slot = 10;
		for (Map.Entry<String, WarpInfo> entry : plugin.warps.entrySet()) {

			//key = entry.getKey();
			//value = entry.getValue();

			String warp = entry.getKey();
			String item = plugin.warps.get(warp).getItem();
			String name = ChatColor.WHITE + warp;

			String world = plugin.getConfig().getString("warps." + warp + ".location.world");
			double x = plugin.getConfig().getDouble("warps." + warp + ".location.x");
			double y = plugin.getConfig().getDouble("warps." + warp + ".location.y");
			double z = plugin.getConfig().getDouble("warps." + warp + ".location.z");

			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + world);
			lore.add(ChatColor.GOLD + String.valueOf(x));
			lore.add(ChatColor.GOLD + String.valueOf(y));
			lore.add(ChatColor.GOLD + String.valueOf(z));

			itemModule(inventory, slot, item, name, lore, null);

			slot++;
		}

		player.openInventory(inventory);
	}

	public void openApplicationsGui(Player player) {

	}

	public void openRulesGui(Player player) {
		List<String> rules = config.getRules();
		for (String rule : rules) {
			player.sendMessage(ChatColor.translateAlternateColorCodes ('&', rule));
		}
	}

	public void openRanksGui(Player player) {
		List<String> ranks = config.getRanks();
		for (String rank : ranks) {
			player.sendMessage(ChatColor.translateAlternateColorCodes ('&', rank));
		}
	}

	public void openPlayerGui(Player player, String targetName) {

		int inventoryRows = config.getGuiStaffSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', "Player: " + targetName);
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiPlayerSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiPlayerSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = "skull:" + targetName;
		String toolbarPageName = ChatColor.WHITE + targetName;
		List<String> toolbarPageLore = new ArrayList<String>();
		//toolbarBackLore.add("targetName");

		String toolbarExitItem = config.getGuiPlayerSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: friend
		int friendSlot = config.getGuiPlayerContentsFriendSlot();
		String friendItem = config.getGuiPlayerContentsFriendItem();
		String friendName = config.getGuiPlayerContentsFriendName();
		List<String> friendLore = config.getGuiPlayerContentsFriendLore();
		itemModule(inventory, friendSlot, friendItem, friendName, friendLore, null);

		// Item: message
		int messageSlot = config.getGuiPlayerContentsMessageSlot();
		String messageItem = config.getGuiPlayerContentsMessageItem();
		String messageName = config.getGuiPlayerContentsMessageName();
		List<String> messageLore = config.getGuiPlayerContentsMessageLore();
		itemModule(inventory, messageSlot, messageItem, messageName, messageLore, null);

		// Item: teleport
		int teleportSlot = config.getGuiPlayerContentsTeleportSlot();
		String teleportItem = config.getGuiPlayerContentsTeleportItem();
		String teleportName = config.getGuiPlayerContentsTeleportName();
		List<String> teleportLore = config.getGuiPlayerContentsTeleportLore();
		itemModule(inventory, teleportSlot, teleportItem, teleportName, teleportLore, null);

		// Item: spectate
		int spectateSlot = config.getGuiPlayerContentsSpectateSlot();
		String spectateItem = config.getGuiPlayerContentsSpectateItem();
		String spectateName = config.getGuiPlayerContentsSpectateName();
		List<String> spectateLore = config.getGuiPlayerContentsSpectateLore();
		itemModule(inventory, spectateSlot, spectateItem, spectateName, spectateLore, null);

		// Item: report
		int reportSlot = config.getGuiPlayerContentsReportSlot();
		String reportItem = config.getGuiPlayerContentsReportItem();
		String reportName = config.getGuiPlayerContentsReportName();
		List<String> reportLore = config.getGuiPlayerContentsReportLore();
		itemModule(inventory, reportSlot, reportItem, reportName, reportLore, null);

		player.openInventory(inventory);

		player.updateInventory();
	}

	public void openStaffGui(Player player) {

		int inventoryRows = config.getGuiStaffSettingsInventorySize() * 9;
		String inventoryName = ChatColor.translateAlternateColorCodes('&', config.getGuiStaffSettingsInventoryName());
		Inventory inventory = plugin.getServer().createInventory(null, inventoryRows, inventoryName);

		// Toolbar

		String toolbarToolbarItem = config.getGuiStaffSettingsToolbarToolbarItem();
		String toolbarToolbarName = ChatColor.WHITE + "Toolbar";
		List<String> toolbarToolbarLore = new ArrayList<String>();
		//toolbarToolbarLore.add("toolbar");

		String toolbarBackItem = config.getGuiStaffSettingsToolbarBackItem();
		String toolbarBackName = ChatColor.WHITE + "Back";
		List<String> toolbarBackLore = new ArrayList<String>();
		toolbarBackLore.add(ChatColor.GOLD + "Click to go back");

		String toolbarPageItem = config.getGuiStaffSettingsToolbarPageItem();
		String toolbarPageName = config.getGuiHomeContentsStaffName();
		List<String> toolbarPageLore = config.getGuiHomeContentsStaffLore();

		String toolbarExitItem = config.getGuiStaffSettingsToolbarExitItem();
		String toolbarExitName = ChatColor.WHITE + "Exit";
		List<String> toolbarExitLore = new ArrayList<String>();
		toolbarExitLore.add(ChatColor.GOLD + "Click to exit");

		toolbarModule(inventory, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarBackItem, toolbarBackName, toolbarBackLore, 
				toolbarToolbarItem, toolbarToolbarName, toolbarToolbarLore, 
				toolbarPageItem, toolbarPageName, toolbarPageLore, 
				toolbarExitItem, toolbarExitName, toolbarExitLore);

		// Item: item

		player.openInventory(inventory);
	}

	public void openWarnGui(Player player, String targetName) {

	}

	public void openMuteGui(Player player, String targetName) {

	}

	public void openFreezeGui(Player player, String targetName) {

	}

	public void openKickGui(Player player, String targetName) {

	}

	public void openBanGui(Player player, String targetName) {

	}

	public void openTicketsGui(Player player, String targetName) {

	}

	public void openNotesGui(Player player, String targetName) {

	}
}
