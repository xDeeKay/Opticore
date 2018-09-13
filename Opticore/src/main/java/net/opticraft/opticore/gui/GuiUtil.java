package net.opticraft.opticore.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.home.Home;
import net.opticraft.opticore.home.HomeUtil;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.settings.SettingsUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;
import net.opticraft.opticore.warp.Warp;

public class GuiUtil {

	public Main plugin;

	public Config config;

	public BungeecordUtil bungeecordUtil;

	public HomeUtil homeUtil;

	public ServerUtil serverUtil;
	
	public SettingsUtil settingsUtil;

	public GuiUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.homeUtil = this.plugin.homeUtil;
		this.serverUtil = this.plugin.serverUtil;
		this.settingsUtil = this.plugin.settingsUtil;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("gui")) {

			Set<String> guiSet = plugin.getConfig().getConfigurationSection("gui").getKeys(false);

			if (!guiSet.isEmpty()) {

				for (String gui : guiSet) {

					String title = plugin.getConfig().getString("gui." + gui + ".settings.title");
					int rows = plugin.getConfig().getInt("gui." + gui + ".settings.rows");

					Set<String> slotsSet = plugin.getConfig().getConfigurationSection("gui." + gui + ".slots").getKeys(false);

					HashMap<String, Slot> slots = new HashMap<String, Slot>();

					for (String slot : slotsSet) {

						int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");
						String material = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".material");
						String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name");
						List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");
						List<String> commands = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".commands");

						slots.put(slot, new Slot(position, material, name, lore, commands));
					}

					plugin.gui.put(gui, new Gui(title, rows, slots));
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public ItemStack item(String material, String name, List<String> lore, boolean glow, int amount) {
		
		ItemStack item;
		
		if (material.toLowerCase().startsWith("player_head:")) {
			
			String[] materialParts = material.split(":");
			String owner = materialParts[1];

			item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);

			SkullMeta meta = (SkullMeta) item.getItemMeta();

			meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
			
			meta.setDisplayName(name);

			List<String> loreList = new ArrayList<String>();
			loreList.addAll(lore);
			meta.setLore(loreList);

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

			if (glow == true) {
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			}

			item.setAmount(amount);

			item.setItemMeta(meta);

		} else {

			Material itemMaterial = Material.valueOf(material.toUpperCase());
			
			item = new ItemStack(itemMaterial, 1);
			
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(name);

			List<String> loreList = new ArrayList<String>();
			loreList.addAll(lore);
			meta.setLore(loreList);

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

			if (glow == true) {
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			}

			item.setAmount(amount);

			item.setItemMeta(meta);
		}

		return item;
	}

	public void itemModule(Inventory inventory, int position, String material, String name, List<String> lore) {

		position = position - 1;
		name = ChatColor.translateAlternateColorCodes('&', name);

		List<String> loreList = new ArrayList<String>();

		for (String loreLine : lore) {

			if (loreLine.contains("%player_count%")) {

				String playerCountString = "0 players online";

				String server = ChatColor.stripColor(name.toLowerCase());

				int playerCount = plugin.servers.get(server).getPlayers().size();

				System.out.println(server + ":" + playerCount + ":" + plugin.servers.get(server).getPlayers());

				if (playerCount == 1) {
					playerCountString = "1 player online";
				} else {
					playerCountString = Integer.toString(playerCount) + " players online";
				}

				loreLine = loreLine.replace("%player_count%", playerCountString);
			}

			if (loreLine.contains("%player_list%")) {

				String playerListString = "None.";

				String server = ChatColor.stripColor(name.toLowerCase());

				List<String> playerList = plugin.servers.get(server).getPlayers();

				if (!playerList.isEmpty()) {
					playerListString = String.join(", ", playerList);
				}

				loreLine = loreLine.replace("%player_list%", playerListString);
			}

			loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
		}

		inventory.setItem(position, item(material, name, loreList, false, 1));
	}

	public void openGui(Player player, String gui, String target) {

		if (plugin.gui.containsKey(gui)) {

			String title = plugin.gui.get(gui).getTitle();
			if (title.contains("%player%")) {
				title = title.replace("%player%", target);
			}
			int rows = plugin.gui.get(gui).getRows() * 9;

			Inventory inventory = plugin.getServer().createInventory(new GuiInventoryHolder(), rows, title);

			// Slots

			HashMap<String, Slot> slots = plugin.gui.get(gui).getSlots();

			for (String slot : slots.keySet()) {

				if (slot.equalsIgnoreCase("playerlist") || slot.equalsIgnoreCase("friendlist")) {

					int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");

					for (String server : plugin.servers.keySet()) {

						for (String online : plugin.servers.get(server).getPlayers()) {

							String onlineName = online;

							String material = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".material").replace("%player%", onlineName);
							String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name").replace("%player%", onlineName);
							
							List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

							int i = 0;
							for (String loreLine : lore) {
								if (loreLine.contains("%player%")) {
									lore.set(i, loreLine.replace("%player%", onlineName));
								}
								if (loreLine.contains("%server%")) {
									String playerServer = serverUtil.getPlayerServer(onlineName);
									if (playerServer != null) {
										lore.set(i, loreLine.replace("%server%", playerServer.substring(0, 1).toUpperCase() + playerServer.substring(1)));
									} else {
										lore.set(i, loreLine.replace("%server%", ChatColor.RED + "Offline"));
									}
								}
								i++;
							}

							itemModule(inventory, position, material, name, lore);

							position++;
						}
					}

				} else if (slot.equalsIgnoreCase("worldlist")) {

					int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");

					for (Map.Entry<String, net.opticraft.opticore.world.World> worlds : plugin.worlds.entrySet()) {

						String world = worlds.getKey();

						String type = plugin.worlds.get(world).getType();
						type = type.substring(0, 1).toUpperCase() + type.substring(1);

						String material = plugin.worlds.get(world).getMaterial();

						String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name").replace("%world%", world);

						List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

						int i = 0;
						for (String loreLine : lore) {
							lore.set(i, loreLine.replace("%type%", type));
							i++;
						}

						Collections.replaceAll(lore, "%type%", type);

						itemModule(inventory, position, material, name, lore);

						position++;
					}
				} else if (slot.equalsIgnoreCase("warplist")) {

					int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");

					for (Map.Entry<String, Warp> warps : plugin.warps.entrySet()) {

						String warp = warps.getKey();

						String material = plugin.warps.get(warp).getMaterial();

						String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name").replace("%warp%", warp);

						String world = plugin.warps.get(warp).getWorld();
						double x = plugin.warps.get(warp).getX();
						double y = plugin.warps.get(warp).getY();
						double z = plugin.warps.get(warp).getZ();

						List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

						int i = 0;
						for (String loreLine : lore) {
							lore.set(i, loreLine.replace("%world%", world).replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%z%", String.valueOf(z)));
							i++;
						}

						itemModule(inventory, position, material, name, lore);

						position++;
					}
				} else if (slot.equalsIgnoreCase("homelist")) {

					int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");

					if (plugin.getServer().getPlayerExact(target) != null) {

						Player targetPlayer = plugin.getServer().getPlayer(target);

						Set<Entry<String, Home>> homes = plugin.players.get(targetPlayer.getName()).getHomes().entrySet();
						for (Map.Entry<String, Home> entry : homes) {

							String home = entry.getKey();

							String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name").replace("%home%", home);

							String material = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getMaterial();
							boolean locked = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getLocked();

							String world = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getWorld();
							double x = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getX();
							double y = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getY();
							double z = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getZ();

							List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

							int i = 0;
							for (String loreLine : lore) {
								lore.set(i, loreLine.replace("%world%", world).replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%z%", String.valueOf(z)));
								i++;
							}

							if (locked) {
								lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Locked");
							}

							itemModule(inventory, position, material, name, lore);

							position++;
						}
					} else {

						@SuppressWarnings("deprecation")
						String uuid = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

						if (homeUtil.getConfig().contains(uuid)) {

							Set<String> homes = homeUtil.getConfig().getConfigurationSection(uuid + ".homes").getKeys(false);
							for (String home : homes) {

								String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name").replace("%home%", home);

								String material = homeUtil.getConfig().getString(uuid + ".homes." + home + ".material");
								boolean locked = homeUtil.getConfig().getBoolean(uuid + ".homes." + home + ".locked");

								String world = homeUtil.getConfig().getString(uuid + ".homes." + home + ".location.world");
								double x = homeUtil.getConfig().getDouble(uuid + ".homes." + home + ".location.x");
								double y = homeUtil.getConfig().getDouble(uuid + ".homes." + home + ".location.y");
								double z = homeUtil.getConfig().getDouble(uuid + ".homes." + home + ".location.z");

								List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

								int i = 0;
								for (String loreLine : lore) {
									lore.set(i, loreLine.replace("%world%", world).replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%z%", String.valueOf(z)));
									i++;
								}

								if (locked) {
									lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Locked");
								}

								itemModule(inventory, position, material, name, lore);

								position++;
							}
						}
					}
				} else {

					int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");
					
					String material = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".material");
					if (material.contains("%player%")) {
						material = material.replace("%player%", target);
					}
					
					String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name");
					if (name.contains("%player%")) {
						name = name.replace("%player%", target);
					}
					
					List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

					int i = 0;
					for (String loreLine : lore) {
						if (target != null) {
							if (loreLine.contains("%player%") && target != null) {
								lore.set(i, loreLine.replace("%player%", target));
							}
							
							if (loreLine.contains("%server%") && target != null) {
								String playerServer = serverUtil.getPlayerServer(target);
								if (playerServer != null) {
									lore.set(i, loreLine.replace("%server%", playerServer.substring(0, 1).toUpperCase() + playerServer.substring(1)));
								} else {
									lore.set(i, loreLine.replace("%server%", ChatColor.RED + "Offline"));
								}
							}
						}
					}

					itemModule(inventory, position, material, name, lore);
				}
			}

			player.openInventory(inventory);
		}
	}

	public void rules(CommandSender sender) {
		List<String> rules = config.getRules();
		for (String rule : rules) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rule));
		}
	}

	public void ranks(CommandSender sender) {
		List<String> ranks = config.getRanks();
		for (String rank : ranks) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rank));
		}
	}

	public void openSettingsGui(Player player) {

		String title = plugin.gui.get("settings").getTitle();
		int rows = plugin.gui.get("settings").getRows() * 9;

		Inventory inventory = plugin.getServer().createInventory(new GuiInventoryHolder(), rows, title);

		// Slot: tb1
		int tb1Position = plugin.gui.get("settings").getSlots().get("tb1").getPosition();
		String tb1Material = plugin.gui.get("settings").getSlots().get("tb1").getMaterial();
		String tb1Name = plugin.gui.get("settings").getSlots().get("tb1").getName();
		List<String> tb1Lore = plugin.gui.get("settings").getSlots().get("tb1").getLore();
		itemModule(inventory, tb1Position, tb1Material, tb1Name, tb1Lore);

		// Slot: tb2
		int tb2Position = plugin.gui.get("settings").getSlots().get("tb2").getPosition();
		String tb2Material = plugin.gui.get("settings").getSlots().get("tb2").getMaterial();
		String tb2Name = plugin.gui.get("settings").getSlots().get("tb2").getName();
		List<String> tb2Lore = plugin.gui.get("settings").getSlots().get("tb2").getLore();
		itemModule(inventory, tb2Position, tb2Material, tb2Name, tb2Lore);

		// Slot: tb3
		int tb3Position = plugin.gui.get("settings").getSlots().get("tb3").getPosition();
		String tb3Material = plugin.gui.get("settings").getSlots().get("tb3").getMaterial();
		String tb3Name = plugin.gui.get("settings").getSlots().get("tb3").getName();
		List<String> tb3Lore = plugin.gui.get("settings").getSlots().get("tb3").getLore();
		itemModule(inventory, tb3Position, tb3Material, tb3Name, tb3Lore);

		// Slot: tb4
		int tb4Position = plugin.gui.get("settings").getSlots().get("tb4").getPosition();
		String tb4Material = plugin.gui.get("settings").getSlots().get("tb4").getMaterial();
		String tb4Name = plugin.gui.get("settings").getSlots().get("tb4").getName();
		List<String> tb4Lore = plugin.gui.get("settings").getSlots().get("tb4").getLore();
		itemModule(inventory, tb4Position, tb4Material, tb4Name, tb4Lore);

		// Slot: tb5
		int tb5Position = plugin.gui.get("settings").getSlots().get("tb5").getPosition();
		String tb5Material = plugin.gui.get("settings").getSlots().get("tb5").getMaterial();
		String tb5Name = plugin.gui.get("settings").getSlots().get("tb5").getName();
		List<String> tb5Lore = plugin.gui.get("settings").getSlots().get("tb5").getLore();
		itemModule(inventory, tb5Position, tb5Material, tb5Name, tb5Lore);

		// Slot: tb6
		int tb6Position = plugin.gui.get("settings").getSlots().get("tb6").getPosition();
		String tb6Material = plugin.gui.get("settings").getSlots().get("tb6").getMaterial();
		String tb6Name = plugin.gui.get("settings").getSlots().get("tb6").getName();
		List<String> tb6Lore = plugin.gui.get("settings").getSlots().get("tb6").getLore();
		itemModule(inventory, tb6Position, tb6Material, tb6Name, tb6Lore);

		// Slot: tb7
		int tb7Position = plugin.gui.get("settings").getSlots().get("tb7").getPosition();
		String tb7Material = plugin.gui.get("settings").getSlots().get("tb7").getMaterial();
		String tb7Name = plugin.gui.get("settings").getSlots().get("tb7").getName();
		List<String> tb7Lore = plugin.gui.get("settings").getSlots().get("tb7").getLore();
		itemModule(inventory, tb7Position, tb7Material, tb7Name, tb7Lore);

		// Slot: tb8
		int tb8Position = plugin.gui.get("settings").getSlots().get("tb8").getPosition();
		String tb8Material = plugin.gui.get("settings").getSlots().get("tb8").getMaterial();
		String tb8Name = plugin.gui.get("settings").getSlots().get("tb8").getName();
		List<String> tb8Lore = plugin.gui.get("settings").getSlots().get("tb8").getLore();
		itemModule(inventory, tb8Position, tb8Material, tb8Name, tb8Lore);

		// Slot: tb9
		int tb9Position = plugin.gui.get("settings").getSlots().get("tb9").getPosition();
		String tb9Material = plugin.gui.get("settings").getSlots().get("tb9").getMaterial();
		String tb9Name = plugin.gui.get("settings").getSlots().get("tb9").getName();
		List<String> tb9Lore = plugin.gui.get("settings").getSlots().get("tb9").getLore();
		itemModule(inventory, tb9Position, tb9Material, tb9Name, tb9Lore);

		// Slot: connect-disconnect
		int connectDisconnectPosition = plugin.gui.get("settings").getSlots().get("connect-disconnect").getPosition();
		String connectDisconnectMaterial = plugin.gui.get("settings").getSlots().get("connect-disconnect").getMaterial();
		String connectDisconnectName = plugin.gui.get("settings").getSlots().get("connect-disconnect").getName();
		List<String> connectDisconnectLore = plugin.gui.get("settings").getSlots().get("connect-disconnect").getLore();
		itemModule(inventory, connectDisconnectPosition, connectDisconnectMaterial, connectDisconnectName, connectDisconnectLore);

		// Slot: connect-disconnect-off
		int connectDisconnectOffPosition = plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getPosition();
		String connectDisconnectOffMaterial = plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getMaterial();
		String connectDisconnectOffName = plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getName();
		List<String> connectDisconnectOffLore = plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getLore();

		// Slot: connect-disconnect-on
		int connectDisconnectOnPosition = plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getPosition();
		String connectDisconnectOnMaterial = plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getMaterial();
		String connectDisconnectOnName = plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getName();
		List<String> connectDisconnectOnLore = plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("connect_disconnect").getValue() == 0) {
			itemModule(inventory, connectDisconnectOffPosition, connectDisconnectOffMaterial, connectDisconnectOffName, connectDisconnectOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
			itemModule(inventory, connectDisconnectOnPosition, connectDisconnectOnMaterial, connectDisconnectOnName, connectDisconnectOnLore);
		}

		// Slot: server-change
		int serverChangePosition = plugin.gui.get("settings").getSlots().get("server-change").getPosition();
		String serverChangeMaterial = plugin.gui.get("settings").getSlots().get("server-change").getMaterial();
		String serverChangeName = plugin.gui.get("settings").getSlots().get("server-change").getName();
		List<String> serverChangeLore = plugin.gui.get("settings").getSlots().get("server-change").getLore();
		itemModule(inventory, serverChangePosition, serverChangeMaterial, serverChangeName, serverChangeLore);

		// Slot: server-change-off
		int serverChangeOffPosition = plugin.gui.get("settings").getSlots().get("server-change-off").getPosition();
		String serverChangeOffMaterial = plugin.gui.get("settings").getSlots().get("server-change-off").getMaterial();
		String serverChangeOffName = plugin.gui.get("settings").getSlots().get("server-change-off").getName();
		List<String> serverChangeOffLore = plugin.gui.get("settings").getSlots().get("server-change-off").getLore();

		// Slot: server-change-on
		int serverChangeOnPosition = plugin.gui.get("settings").getSlots().get("server-change-on").getPosition();
		String serverChangeOnMaterial = plugin.gui.get("settings").getSlots().get("server-change-on").getMaterial();
		String serverChangeOnName = plugin.gui.get("settings").getSlots().get("server-change-on").getName();
		List<String> serverChangeOnLore = plugin.gui.get("settings").getSlots().get("server-change-on").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("server_change").getValue() == 0) {
			itemModule(inventory, serverChangeOffPosition, serverChangeOffMaterial, serverChangeOffName, serverChangeOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("server_change").getValue() == 1) {
			itemModule(inventory, serverChangeOnPosition, serverChangeOnMaterial, serverChangeOnName, serverChangeOnLore);
		}

		// Slot: player-chat
		int playerChatPosition = plugin.gui.get("settings").getSlots().get("player-chat").getPosition();
		String playerChatMaterial = plugin.gui.get("settings").getSlots().get("player-chat").getMaterial();
		String playerChatName = plugin.gui.get("settings").getSlots().get("player-chat").getName();
		List<String> playerChatLore = plugin.gui.get("settings").getSlots().get("player-chat").getLore();
		itemModule(inventory, playerChatPosition, playerChatMaterial, playerChatName, playerChatLore);

		// Slot: player-chat-off
		int playerChatOffPosition = plugin.gui.get("settings").getSlots().get("player-chat-off").getPosition();
		String playerChatOffMaterial = plugin.gui.get("settings").getSlots().get("player-chat-off").getMaterial();
		String playerChatOffName = plugin.gui.get("settings").getSlots().get("player-chat-off").getName();
		List<String> playerChatOffLore = plugin.gui.get("settings").getSlots().get("player-chat-off").getLore();

		// Slot: player-chat-on
		int playerChatOnPosition = plugin.gui.get("settings").getSlots().get("player-chat-on").getPosition();
		String playerChatOnMaterial = plugin.gui.get("settings").getSlots().get("player-chat-on").getMaterial();
		String playerChatOnName = plugin.gui.get("settings").getSlots().get("player-chat-on").getName();
		List<String> playerChatOnLore = plugin.gui.get("settings").getSlots().get("player-chat-on").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("player_chat").getValue() == 0) {
			itemModule(inventory, playerChatOffPosition, playerChatOffMaterial, playerChatOffName, playerChatOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("player_chat").getValue() == 1) {
			itemModule(inventory, playerChatOnPosition, playerChatOnMaterial, playerChatOnName, playerChatOnLore);
		}

		// Slot: server-announcement
		int serverAnnouncementPosition = plugin.gui.get("settings").getSlots().get("server-announcement").getPosition();
		String serverAnnouncementMaterial = plugin.gui.get("settings").getSlots().get("server-announcement").getMaterial();
		String serverAnnouncementName = plugin.gui.get("settings").getSlots().get("server-announcement").getName();
		List<String> serverAnnouncementLore = plugin.gui.get("settings").getSlots().get("server-announcement").getLore();
		itemModule(inventory, serverAnnouncementPosition, serverAnnouncementMaterial, serverAnnouncementName, serverAnnouncementLore);

		// Slot: server-announcement-off
		int serverAnnouncementOffPosition = plugin.gui.get("settings").getSlots().get("server-announcement-off").getPosition();
		String serverAnnouncementOffMaterial = plugin.gui.get("settings").getSlots().get("server-announcement-off").getMaterial();
		String serverAnnouncementOffName = plugin.gui.get("settings").getSlots().get("server-announcement-off").getName();
		List<String> serverAnnouncementOffLore = plugin.gui.get("settings").getSlots().get("server-announcement-off").getLore();

		// Slot: server-announcement-on
		int serverAnnouncementOnPosition = plugin.gui.get("settings").getSlots().get("server-announcement-on").getPosition();
		String serverAnnouncementOnMaterial = plugin.gui.get("settings").getSlots().get("server-announcement-on").getMaterial();
		String serverAnnouncementOnName = plugin.gui.get("settings").getSlots().get("server-announcement-on").getName();
		List<String> serverAnnouncementOnLore = plugin.gui.get("settings").getSlots().get("server-announcement-on").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("server_announcement").getValue() == 0) {
			itemModule(inventory, serverAnnouncementOffPosition, serverAnnouncementOffMaterial, serverAnnouncementOffName, serverAnnouncementOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("server_announcement").getValue() == 1) {
			itemModule(inventory, serverAnnouncementOnPosition, serverAnnouncementOnMaterial, serverAnnouncementOnName, serverAnnouncementOnLore);
		}

		// Slot: friend-request
		int friendRequestPosition = plugin.gui.get("settings").getSlots().get("friend-request").getPosition();
		String friendRequestMaterial = plugin.gui.get("settings").getSlots().get("friend-request").getMaterial();
		String friendRequestName = plugin.gui.get("settings").getSlots().get("friend-request").getName();
		List<String> friendRequestLore = plugin.gui.get("settings").getSlots().get("friend-request").getLore();
		itemModule(inventory, friendRequestPosition, friendRequestMaterial, friendRequestName, friendRequestLore);

		// Slot: friend-request-off
		int friendRequestOffPosition = plugin.gui.get("settings").getSlots().get("friend-request-off").getPosition();
		String friendRequestOffMaterial = plugin.gui.get("settings").getSlots().get("friend-request-off").getMaterial();
		String friendRequestOffName = plugin.gui.get("settings").getSlots().get("friend-request-off").getName();
		List<String> friendRequestOffLore = plugin.gui.get("settings").getSlots().get("friend-request-off").getLore();

		// Slot: friend-request-on
		int friendRequestOnPosition = plugin.gui.get("settings").getSlots().get("friend-request-on").getPosition();
		String friendRequestOnMaterial = plugin.gui.get("settings").getSlots().get("friend-request-on").getMaterial();
		String friendRequestOnName = plugin.gui.get("settings").getSlots().get("friend-request-on").getName();
		List<String> friendRequestOnLore = plugin.gui.get("settings").getSlots().get("friend-request-on").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("friend_request").getValue() == 0) {
			itemModule(inventory, friendRequestOffPosition, friendRequestOffMaterial, friendRequestOffName, friendRequestOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("friend_request").getValue() == 1) {
			itemModule(inventory, friendRequestOnPosition, friendRequestOnMaterial, friendRequestOnName, friendRequestOnLore);
		}

		// Slot: direct-message
		int directMessagePosition = plugin.gui.get("settings").getSlots().get("direct-message").getPosition();
		String directMessageMaterial = plugin.gui.get("settings").getSlots().get("direct-message").getMaterial();
		String directMessageName = plugin.gui.get("settings").getSlots().get("direct-message").getName();
		List<String> directMessageLore = plugin.gui.get("settings").getSlots().get("direct-message").getLore();
		itemModule(inventory, directMessagePosition, directMessageMaterial, directMessageName, directMessageLore);

		// Slot: direct-message-off
		int directMessageOffPosition = plugin.gui.get("settings").getSlots().get("direct-message-off").getPosition();
		String directMessageOffMaterial = plugin.gui.get("settings").getSlots().get("direct-message-off").getMaterial();
		String directMessageOffName = plugin.gui.get("settings").getSlots().get("direct-message-off").getName();
		List<String> directMessageOffLore = plugin.gui.get("settings").getSlots().get("direct-message-off").getLore();

		// Slot: direct-message-on
		int directMessageOnPosition = plugin.gui.get("settings").getSlots().get("direct-message-on").getPosition();
		String directMessageOnMaterial = plugin.gui.get("settings").getSlots().get("direct-message-on").getMaterial();
		String directMessageOnName = plugin.gui.get("settings").getSlots().get("direct-message-on").getName();
		List<String> directMessageOnLore = plugin.gui.get("settings").getSlots().get("direct-message-on").getLore();

		// Slot: direct-message-friend
		int directMessageFriendPosition = plugin.gui.get("settings").getSlots().get("direct-message-friend").getPosition();
		String directMessageFriendMaterial = plugin.gui.get("settings").getSlots().get("direct-message-friend").getMaterial();
		String directMessageFriendName = plugin.gui.get("settings").getSlots().get("direct-message-friend").getName();
		List<String> directMessageFriendLore = plugin.gui.get("settings").getSlots().get("direct-message-friend").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("direct_message").getValue() == 0) {
			itemModule(inventory, directMessageOffPosition, directMessageOffMaterial, directMessageOffName, directMessageOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message").getValue() == 1) {
			itemModule(inventory, directMessageOnPosition, directMessageOnMaterial, directMessageOnName, directMessageOnLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message").getValue() == 2) {
			itemModule(inventory, directMessageFriendPosition, directMessageFriendMaterial, directMessageFriendName, directMessageFriendLore);
		}

		// Slot: teleport-request
		int teleportRequestPosition = plugin.gui.get("settings").getSlots().get("teleport-request").getPosition();
		String teleportRequestMaterial = plugin.gui.get("settings").getSlots().get("teleport-request").getMaterial();
		String teleportRequestName = plugin.gui.get("settings").getSlots().get("teleport-request").getName();
		List<String> teleportRequestLore = plugin.gui.get("settings").getSlots().get("teleport-request").getLore();
		itemModule(inventory, teleportRequestPosition, teleportRequestMaterial, teleportRequestName, teleportRequestLore);

		// Slot: teleport-request-off
		int teleportRequestOffPosition = plugin.gui.get("settings").getSlots().get("teleport-request-off").getPosition();
		String teleportRequestOffMaterial = plugin.gui.get("settings").getSlots().get("teleport-request-off").getMaterial();
		String teleportRequestOffName = plugin.gui.get("settings").getSlots().get("teleport-request-off").getName();
		List<String> teleportRequestOffLore = plugin.gui.get("settings").getSlots().get("teleport-request-off").getLore();

		// Slot: teleport-request-on
		int teleportRequestOnPosition = plugin.gui.get("settings").getSlots().get("teleport-request-on").getPosition();
		String teleportRequestOnMaterial = plugin.gui.get("settings").getSlots().get("teleport-request-on").getMaterial();
		String teleportRequestOnName = plugin.gui.get("settings").getSlots().get("teleport-request-on").getName();
		List<String> teleportRequestOnLore = plugin.gui.get("settings").getSlots().get("teleport-request-on").getLore();

		// Slot: teleport-request-friend
		int teleportRequestFriendPosition = plugin.gui.get("settings").getSlots().get("teleport-request-friend").getPosition();
		String teleportRequestFriendMaterial = plugin.gui.get("settings").getSlots().get("teleport-request-friend").getMaterial();
		String teleportRequestFriendName = plugin.gui.get("settings").getSlots().get("teleport-request-friend").getName();
		List<String> teleportRequestFriendLore = plugin.gui.get("settings").getSlots().get("teleport-request-friend").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("teleport_request").getValue() == 0) {
			itemModule(inventory, teleportRequestOffPosition, teleportRequestOffMaterial, teleportRequestOffName, teleportRequestOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("teleport_request").getValue() == 1) {
			itemModule(inventory, teleportRequestOnPosition, teleportRequestOnMaterial, teleportRequestOnName, teleportRequestOnLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("teleport_request").getValue() == 2) {
			itemModule(inventory, teleportRequestFriendPosition, teleportRequestFriendMaterial, teleportRequestFriendName, teleportRequestFriendLore);
		}

		// Slot: spectate-request
		int spectateRequestPosition = plugin.gui.get("settings").getSlots().get("spectate-request").getPosition();
		String spectateRequestMaterial = plugin.gui.get("settings").getSlots().get("spectate-request").getMaterial();
		String spectateRequestName = plugin.gui.get("settings").getSlots().get("spectate-request").getName();
		List<String> spectateRequestLore = plugin.gui.get("settings").getSlots().get("spectate-request").getLore();
		itemModule(inventory, spectateRequestPosition, spectateRequestMaterial, spectateRequestName, spectateRequestLore);

		// Slot: spectate-request-off
		int spectateRequestOffPosition = plugin.gui.get("settings").getSlots().get("spectate-request-off").getPosition();
		String spectateRequestOffMaterial = plugin.gui.get("settings").getSlots().get("spectate-request-off").getMaterial();
		String spectateRequestOffName = plugin.gui.get("settings").getSlots().get("spectate-request-off").getName();
		List<String> spectateRequestOffLore = plugin.gui.get("settings").getSlots().get("spectate-request-off").getLore();

		// Slot: spectate-request-on
		int spectateRequestOnPosition = plugin.gui.get("settings").getSlots().get("spectate-request-on").getPosition();
		String spectateRequestOnMaterial = plugin.gui.get("settings").getSlots().get("spectate-request-on").getMaterial();
		String spectateRequestOnName = plugin.gui.get("settings").getSlots().get("spectate-request-on").getName();
		List<String> spectateRequestOnLore = plugin.gui.get("settings").getSlots().get("spectate-request-on").getLore();

		// Slot: spectate-request-friend
		int spectateRequestFriendPosition = plugin.gui.get("settings").getSlots().get("spectate-request-friend").getPosition();
		String spectateRequestFriendMaterial = plugin.gui.get("settings").getSlots().get("spectate-request-friend").getMaterial();
		String spectateRequestFriendName = plugin.gui.get("settings").getSlots().get("spectate-request-friend").getName();
		List<String> spectateRequestFriendLore = plugin.gui.get("settings").getSlots().get("spectate-request-friend").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("spectate_request").getValue() == 0) {
			itemModule(inventory, spectateRequestOffPosition, spectateRequestOffMaterial, spectateRequestOffName, spectateRequestOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("spectate_request").getValue() == 1) {
			itemModule(inventory, spectateRequestOnPosition, spectateRequestOnMaterial, spectateRequestOnName, spectateRequestOnLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("spectate_request").getValue() == 2) {
			itemModule(inventory, spectateRequestFriendPosition, spectateRequestFriendMaterial, spectateRequestFriendName, spectateRequestFriendLore);
		}

		player.openInventory(inventory);
	}
}
