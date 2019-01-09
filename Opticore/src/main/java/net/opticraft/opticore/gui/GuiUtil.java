package net.opticraft.opticore.gui;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.opticraft.opticore.settings.SettingUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;
import net.opticraft.opticore.warp.Warp;

public class GuiUtil {

	public Main plugin;

	public Config config;

	public BungeecordUtil bungeecordUtil;

	public HomeUtil homeUtil;

	public ServerUtil serverUtil;

	public SettingUtil settingUtil;

	public GuiUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.homeUtil = this.plugin.homeUtil;
		this.serverUtil = this.plugin.serverUtil;
		this.settingUtil = this.plugin.settingUtil;
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

	public void guiItem(Inventory inventory, int position, String material, String name, List<String> lore) {

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

			Inventory inventory = plugin.getServer().createInventory(new OpticraftInventory(), rows, title);

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

							guiItem(inventory, position, material, name, lore);

							position++;
						}
					}

				} else if (slot.equalsIgnoreCase("worldlist")) {

					int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");
					
					List<String> types = Arrays.asList("Lobby", "Event", "Freebuild", "Freebuild rank", "Plot rank", "Personal");
					
					for (String typeName : types) {
						
						for (Map.Entry<String, net.opticraft.opticore.world.World> worlds : plugin.worlds.entrySet()) {
							
							String world = worlds.getKey();
							
							if (plugin.worldUtil.isOwner(player, world) || plugin.worldUtil.isMember(player, world) || plugin.worldUtil.isGuest(player, world) || plugin.worldUtil.isSpectator(player, world)) {
								
								String type = plugin.worlds.get(world).getType();
								type = type.substring(0, 1).toUpperCase() + type.substring(1);
								
								if (type.equals(typeName)) {
									String material = plugin.worlds.get(world).getMaterial().replace("%player%", world);

									String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name").replace("%world%", world);

									List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

									int i = 0;
									for (String loreLine : lore) {
										lore.set(i, loreLine.replace("%type%", type));
										i++;
									}

									Collections.replaceAll(lore, "%type%", type);

									guiItem(inventory, position, material, name, lore);

									position++;
								}
							}
						}
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

						guiItem(inventory, position, material, name, lore);

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
							//boolean locked = plugin.players.get(targetPlayer.getName()).getHomes().get(home).getLocked();

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

							if (homeUtil.getLock(target, home)) {
								lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Locked");
							}

							guiItem(inventory, position, material, name, lore);

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
								//boolean locked = homeUtil.getConfig().getBoolean(uuid + ".homes." + home + ".locked");

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

								if (homeUtil.getLock(target, home)) {
									lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Locked");
								}

								guiItem(inventory, position, material, name, lore);

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

							if (loreLine.contains("%player%")) {
								lore.set(i, loreLine.replace("%player%", target));
							}

							if (loreLine.contains("%server%")) {
								String playerServer = serverUtil.getPlayerServer(target);
								if (playerServer != null) {
									lore.set(i, loreLine.replace("%server%", playerServer.substring(0, 1).toUpperCase() + playerServer.substring(1)));
								} else {
									lore.set(i, loreLine.replace("%server%", ChatColor.RED + "Offline"));
								}
							}
						}
						if (loreLine.contains("%points%")) {
							lore.set(i, loreLine.replace("%points%", Integer.toString(plugin.mysql.getUUIDColumnValue(player.getName(), "oc_points", "points"))));
						}
					}

					guiItem(inventory, position, material, name, lore);
				}
			}

			player.openInventory(inventory);
		}
	}
	
	public void sendListAsMessage(CommandSender sender, List<String> list) {
		for (String line : list) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', line));
		}
	}

	/**
	 * Create a Settings inventory and occupy it with custom items, then show it to the player.
	 * @param  player  the player to see the inventory
	 */
	public void openSettingsGui(Player player) {

		String title = plugin.gui.get("settings").getTitle();
		int rows = plugin.gui.get("settings").getRows() * 9;

		Inventory inventory = plugin.getServer().createInventory(new OpticraftInventory(), rows, title);

		// Slot: tb1
		int tb1Position = plugin.gui.get("settings").getSlots().get("tb1").getPosition();
		String tb1Material = plugin.gui.get("settings").getSlots().get("tb1").getMaterial();
		String tb1Name = plugin.gui.get("settings").getSlots().get("tb1").getName();
		List<String> tb1Lore = plugin.gui.get("settings").getSlots().get("tb1").getLore();
		guiItem(inventory, tb1Position, tb1Material, tb1Name, tb1Lore);

		// Slot: tb2
		int tb2Position = plugin.gui.get("settings").getSlots().get("tb2").getPosition();
		String tb2Material = plugin.gui.get("settings").getSlots().get("tb2").getMaterial();
		String tb2Name = plugin.gui.get("settings").getSlots().get("tb2").getName();
		List<String> tb2Lore = plugin.gui.get("settings").getSlots().get("tb2").getLore();
		guiItem(inventory, tb2Position, tb2Material, tb2Name, tb2Lore);

		// Slot: tb3
		int tb3Position = plugin.gui.get("settings").getSlots().get("tb3").getPosition();
		String tb3Material = plugin.gui.get("settings").getSlots().get("tb3").getMaterial();
		String tb3Name = plugin.gui.get("settings").getSlots().get("tb3").getName();
		List<String> tb3Lore = plugin.gui.get("settings").getSlots().get("tb3").getLore();
		guiItem(inventory, tb3Position, tb3Material, tb3Name, tb3Lore);

		// Slot: tb4
		int tb4Position = plugin.gui.get("settings").getSlots().get("tb4").getPosition();
		String tb4Material = plugin.gui.get("settings").getSlots().get("tb4").getMaterial();
		String tb4Name = plugin.gui.get("settings").getSlots().get("tb4").getName();
		List<String> tb4Lore = plugin.gui.get("settings").getSlots().get("tb4").getLore();
		guiItem(inventory, tb4Position, tb4Material, tb4Name, tb4Lore);

		// Slot: tb5
		int tb5Position = plugin.gui.get("settings").getSlots().get("tb5").getPosition();
		String tb5Material = plugin.gui.get("settings").getSlots().get("tb5").getMaterial();
		String tb5Name = plugin.gui.get("settings").getSlots().get("tb5").getName();
		List<String> tb5Lore = plugin.gui.get("settings").getSlots().get("tb5").getLore();
		guiItem(inventory, tb5Position, tb5Material, tb5Name, tb5Lore);

		// Slot: tb6
		int tb6Position = plugin.gui.get("settings").getSlots().get("tb6").getPosition();
		String tb6Material = plugin.gui.get("settings").getSlots().get("tb6").getMaterial();
		String tb6Name = plugin.gui.get("settings").getSlots().get("tb6").getName();
		List<String> tb6Lore = plugin.gui.get("settings").getSlots().get("tb6").getLore();
		guiItem(inventory, tb6Position, tb6Material, tb6Name, tb6Lore);

		// Slot: tb7
		int tb7Position = plugin.gui.get("settings").getSlots().get("tb7").getPosition();
		String tb7Material = plugin.gui.get("settings").getSlots().get("tb7").getMaterial();
		String tb7Name = plugin.gui.get("settings").getSlots().get("tb7").getName();
		List<String> tb7Lore = plugin.gui.get("settings").getSlots().get("tb7").getLore();
		guiItem(inventory, tb7Position, tb7Material, tb7Name, tb7Lore);

		// Slot: tb8
		int tb8Position = plugin.gui.get("settings").getSlots().get("tb8").getPosition();
		String tb8Material = plugin.gui.get("settings").getSlots().get("tb8").getMaterial();
		String tb8Name = plugin.gui.get("settings").getSlots().get("tb8").getName();
		List<String> tb8Lore = plugin.gui.get("settings").getSlots().get("tb8").getLore();
		guiItem(inventory, tb8Position, tb8Material, tb8Name, tb8Lore);

		// Slot: tb9
		int tb9Position = plugin.gui.get("settings").getSlots().get("tb9").getPosition();
		String tb9Material = plugin.gui.get("settings").getSlots().get("tb9").getMaterial();
		String tb9Name = plugin.gui.get("settings").getSlots().get("tb9").getName();
		List<String> tb9Lore = plugin.gui.get("settings").getSlots().get("tb9").getLore();
		guiItem(inventory, tb9Position, tb9Material, tb9Name, tb9Lore);

		// Slot: connect-disconnect
		int connectDisconnectPosition = plugin.gui.get("settings").getSlots().get("connect-disconnect").getPosition();
		String connectDisconnectMaterial = plugin.gui.get("settings").getSlots().get("connect-disconnect").getMaterial();
		String connectDisconnectName = plugin.gui.get("settings").getSlots().get("connect-disconnect").getName();
		List<String> connectDisconnectLore = plugin.gui.get("settings").getSlots().get("connect-disconnect").getLore();
		guiItem(inventory, connectDisconnectPosition, connectDisconnectMaterial, connectDisconnectName, connectDisconnectLore);

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
			guiItem(inventory, connectDisconnectOffPosition, connectDisconnectOffMaterial, connectDisconnectOffName, connectDisconnectOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("connect_disconnect").getValue() == 1) {
			guiItem(inventory, connectDisconnectOnPosition, connectDisconnectOnMaterial, connectDisconnectOnName, connectDisconnectOnLore);
		}

		// Slot: server-change
		int serverChangePosition = plugin.gui.get("settings").getSlots().get("server-change").getPosition();
		String serverChangeMaterial = plugin.gui.get("settings").getSlots().get("server-change").getMaterial();
		String serverChangeName = plugin.gui.get("settings").getSlots().get("server-change").getName();
		List<String> serverChangeLore = plugin.gui.get("settings").getSlots().get("server-change").getLore();
		guiItem(inventory, serverChangePosition, serverChangeMaterial, serverChangeName, serverChangeLore);

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
			guiItem(inventory, serverChangeOffPosition, serverChangeOffMaterial, serverChangeOffName, serverChangeOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("server_change").getValue() == 1) {
			guiItem(inventory, serverChangeOnPosition, serverChangeOnMaterial, serverChangeOnName, serverChangeOnLore);
		}

		// Slot: player-chat
		int playerChatPosition = plugin.gui.get("settings").getSlots().get("player-chat").getPosition();
		String playerChatMaterial = plugin.gui.get("settings").getSlots().get("player-chat").getMaterial();
		String playerChatName = plugin.gui.get("settings").getSlots().get("player-chat").getName();
		List<String> playerChatLore = plugin.gui.get("settings").getSlots().get("player-chat").getLore();
		guiItem(inventory, playerChatPosition, playerChatMaterial, playerChatName, playerChatLore);

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
			guiItem(inventory, playerChatOffPosition, playerChatOffMaterial, playerChatOffName, playerChatOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("player_chat").getValue() == 1) {
			guiItem(inventory, playerChatOnPosition, playerChatOnMaterial, playerChatOnName, playerChatOnLore);
		}

		// Slot: server-announcement
		int serverAnnouncementPosition = plugin.gui.get("settings").getSlots().get("server-announcement").getPosition();
		String serverAnnouncementMaterial = plugin.gui.get("settings").getSlots().get("server-announcement").getMaterial();
		String serverAnnouncementName = plugin.gui.get("settings").getSlots().get("server-announcement").getName();
		List<String> serverAnnouncementLore = plugin.gui.get("settings").getSlots().get("server-announcement").getLore();
		guiItem(inventory, serverAnnouncementPosition, serverAnnouncementMaterial, serverAnnouncementName, serverAnnouncementLore);

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
			guiItem(inventory, serverAnnouncementOffPosition, serverAnnouncementOffMaterial, serverAnnouncementOffName, serverAnnouncementOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("server_announcement").getValue() == 1) {
			guiItem(inventory, serverAnnouncementOnPosition, serverAnnouncementOnMaterial, serverAnnouncementOnName, serverAnnouncementOnLore);
		}

		// Slot: reward-reminder
		int rewardReminderPosition = plugin.gui.get("settings").getSlots().get("reward-reminder").getPosition();
		String rewardReminderMaterial = plugin.gui.get("settings").getSlots().get("reward-reminder").getMaterial();
		String rewardReminderName = plugin.gui.get("settings").getSlots().get("reward-reminder").getName();
		List<String> rewardReminderLore = plugin.gui.get("settings").getSlots().get("reward-reminder").getLore();
		guiItem(inventory, rewardReminderPosition, rewardReminderMaterial, rewardReminderName, rewardReminderLore);

		// Slot: reward-teminder-off
		int rewardReminderOffPosition = plugin.gui.get("settings").getSlots().get("reward-reminder-off").getPosition();
		String rewardReminderOffMaterial = plugin.gui.get("settings").getSlots().get("reward-reminder-off").getMaterial();
		String rewardReminderOffName = plugin.gui.get("settings").getSlots().get("reward-reminder-off").getName();
		List<String> rewardReminderOffLore = plugin.gui.get("settings").getSlots().get("reward-reminder-off").getLore();

		// Slot: reward-teminder-on
		int rewardReminderOnPosition = plugin.gui.get("settings").getSlots().get("reward-reminder-on").getPosition();
		String rewardReminderOnMaterial = plugin.gui.get("settings").getSlots().get("reward-reminder-on").getMaterial();
		String rewardReminderOnName = plugin.gui.get("settings").getSlots().get("reward-reminder-on").getName();
		List<String> rewardReminderOnLore = plugin.gui.get("settings").getSlots().get("reward-reminder-on").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("reward_reminder").getValue() == 0) {
			guiItem(inventory, rewardReminderOffPosition, rewardReminderOffMaterial, rewardReminderOffName, rewardReminderOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("reward_reminder").getValue() == 1) {
			guiItem(inventory, rewardReminderOnPosition, rewardReminderOnMaterial, rewardReminderOnName, rewardReminderOnLore);
		}

		// Slot: friend-request
		int friendRequestPosition = plugin.gui.get("settings").getSlots().get("friend-request").getPosition();
		String friendRequestMaterial = plugin.gui.get("settings").getSlots().get("friend-request").getMaterial();
		String friendRequestName = plugin.gui.get("settings").getSlots().get("friend-request").getName();
		List<String> friendRequestLore = plugin.gui.get("settings").getSlots().get("friend-request").getLore();
		guiItem(inventory, friendRequestPosition, friendRequestMaterial, friendRequestName, friendRequestLore);

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
			guiItem(inventory, friendRequestOffPosition, friendRequestOffMaterial, friendRequestOffName, friendRequestOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("friend_request").getValue() == 1) {
			guiItem(inventory, friendRequestOnPosition, friendRequestOnMaterial, friendRequestOnName, friendRequestOnLore);
		}

		// Slot: direct-message
		int directMessagePosition = plugin.gui.get("settings").getSlots().get("direct-message").getPosition();
		String directMessageMaterial = plugin.gui.get("settings").getSlots().get("direct-message").getMaterial();
		String directMessageName = plugin.gui.get("settings").getSlots().get("direct-message").getName();
		List<String> directMessageLore = plugin.gui.get("settings").getSlots().get("direct-message").getLore();
		guiItem(inventory, directMessagePosition, directMessageMaterial, directMessageName, directMessageLore);

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
			guiItem(inventory, directMessageOffPosition, directMessageOffMaterial, directMessageOffName, directMessageOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message").getValue() == 1) {
			guiItem(inventory, directMessageOnPosition, directMessageOnMaterial, directMessageOnName, directMessageOnLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message").getValue() == 2) {
			guiItem(inventory, directMessageFriendPosition, directMessageFriendMaterial, directMessageFriendName, directMessageFriendLore);
		}

		// Slot: direct-message-color
		int directMessageColorPosition = plugin.gui.get("settings").getSlots().get("direct-message-color").getPosition();
		String directMessageColorMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color").getMaterial();
		String directMessageColorName = plugin.gui.get("settings").getSlots().get("direct-message-color").getName();
		List<String> directMessageColorLore = plugin.gui.get("settings").getSlots().get("direct-message-color").getLore();
		guiItem(inventory, directMessageColorPosition, directMessageColorMaterial, directMessageColorName, directMessageColorLore);

		// Slot: direct-message-color-black
		int directMessageColorBlackPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-black").getPosition();
		String directMessageColorBlackMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-black").getMaterial();
		String directMessageColorBlackName = plugin.gui.get("settings").getSlots().get("direct-message-color-black").getName();
		List<String> directMessageColorBlackLore = plugin.gui.get("settings").getSlots().get("direct-message-color-black").getLore();

		// Slot: direct-message-color-darkblue
		int directMessageColorDarkBluePosition = plugin.gui.get("settings").getSlots().get("direct-message-color-darkblue").getPosition();
		String directMessageColorDarkBlueMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-darkblue").getMaterial();
		String directMessageColorDarkBlueName = plugin.gui.get("settings").getSlots().get("direct-message-color-darkblue").getName();
		List<String> directMessageColorDarkBlueLore = plugin.gui.get("settings").getSlots().get("direct-message-color-darkblue").getLore();

		// Slot: direct-message-color-darkgreen
		int directMessageColorDarkGreenPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgreen").getPosition();
		String directMessageColorDarkGreenMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgreen").getMaterial();
		String directMessageColorDarkGreenName = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgreen").getName();
		List<String> directMessageColorDarkGreenLore = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgreen").getLore();

		// Slot: direct-message-color-darkaqua
		int directMessageColorDarkAquaPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-darkaqua").getPosition();
		String directMessageColorDarkAquaMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-darkaqua").getMaterial();
		String directMessageColorDarkAquaName = plugin.gui.get("settings").getSlots().get("direct-message-color-darkaqua").getName();
		List<String> directMessageColorDarkAquaLore = plugin.gui.get("settings").getSlots().get("direct-message-color-darkaqua").getLore();

		// Slot: direct-message-color-darkred
		int directMessageColorDarkRedPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-darkred").getPosition();
		String directMessageColorDarkRedMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-darkred").getMaterial();
		String directMessageColorDarkRedName = plugin.gui.get("settings").getSlots().get("direct-message-color-darkred").getName();
		List<String> directMessageColorDarkRedLore = plugin.gui.get("settings").getSlots().get("direct-message-color-darkred").getLore();

		// Slot: direct-message-color-darkpurple
		int directMessageColorDarkPurplePosition = plugin.gui.get("settings").getSlots().get("direct-message-color-darkpurple").getPosition();
		String directMessageColorDarkPurpleMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-darkpurple").getMaterial();
		String directMessageColorDarkPurpleName = plugin.gui.get("settings").getSlots().get("direct-message-color-darkpurple").getName();
		List<String> directMessageColorDarkPurpleLore = plugin.gui.get("settings").getSlots().get("direct-message-color-darkpurple").getLore();

		// Slot: direct-message-color-gold
		int directMessageColorGoldPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-gold").getPosition();
		String directMessageColorGoldMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-gold").getMaterial();
		String directMessageColorGoldName = plugin.gui.get("settings").getSlots().get("direct-message-color-gold").getName();
		List<String> directMessageColorGoldLore = plugin.gui.get("settings").getSlots().get("direct-message-color-gold").getLore();

		// Slot: direct-message-color-gray
		int directMessageColorGrayPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-gray").getPosition();
		String directMessageColorGrayMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-gray").getMaterial();
		String directMessageColorGrayName = plugin.gui.get("settings").getSlots().get("direct-message-color-gray").getName();
		List<String> directMessageColorGrayLore = plugin.gui.get("settings").getSlots().get("direct-message-color-gray").getLore();

		// Slot: direct-message-color-darkgray
		int directMessageColorDarkGrayPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgray").getPosition();
		String directMessageColorDarkGrayMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgray").getMaterial();
		String directMessageColorDarkGrayName = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgray").getName();
		List<String> directMessageColorDarkGrayLore = plugin.gui.get("settings").getSlots().get("direct-message-color-darkgray").getLore();

		// Slot: direct-message-color-blue
		int directMessageColorBluePosition = plugin.gui.get("settings").getSlots().get("direct-message-color-blue").getPosition();
		String directMessageColorBlueMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-blue").getMaterial();
		String directMessageColorBlueName = plugin.gui.get("settings").getSlots().get("direct-message-color-blue").getName();
		List<String> directMessageColorBlueLore = plugin.gui.get("settings").getSlots().get("direct-message-color-blue").getLore();

		// Slot: direct-message-color-green
		int directMessageColorGreenPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-green").getPosition();
		String directMessageColorGreenMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-green").getMaterial();
		String directMessageColorGreenName = plugin.gui.get("settings").getSlots().get("direct-message-color-green").getName();
		List<String> directMessageColorGreenLore = plugin.gui.get("settings").getSlots().get("direct-message-color-green").getLore();

		// Slot: direct-message-color-aqua
		int directMessageColorAquaPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-aqua").getPosition();
		String directMessageColorAquaMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-aqua").getMaterial();
		String directMessageColorAquaName = plugin.gui.get("settings").getSlots().get("direct-message-color-aqua").getName();
		List<String> directMessageColorAquaLore = plugin.gui.get("settings").getSlots().get("direct-message-color-aqua").getLore();

		// Slot: direct-message-color-red
		int directMessageColorRedPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-red").getPosition();
		String directMessageColorRedMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-red").getMaterial();
		String directMessageColorRedName = plugin.gui.get("settings").getSlots().get("direct-message-color-red").getName();
		List<String> directMessageColorRedLore = plugin.gui.get("settings").getSlots().get("direct-message-color-red").getLore();

		// Slot: direct-message-color-lightpurple
		int directMessageColorLightPurplePosition = plugin.gui.get("settings").getSlots().get("direct-message-color-lightpurple").getPosition();
		String directMessageColorLightPurpleMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-lightpurple").getMaterial();
		String directMessageColorLightPurpleName = plugin.gui.get("settings").getSlots().get("direct-message-color-lightpurple").getName();
		List<String> directMessageColorLightPurpleLore = plugin.gui.get("settings").getSlots().get("direct-message-color-lightpurple").getLore();

		// Slot: direct-message-color-yellow
		int directMessageColorYellowPosition = plugin.gui.get("settings").getSlots().get("direct-message-color-yellow").getPosition();
		String directMessageColorYellowMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-yellow").getMaterial();
		String directMessageColorYellowName = plugin.gui.get("settings").getSlots().get("direct-message-color-yellow").getName();
		List<String> directMessageColorYellowLore = plugin.gui.get("settings").getSlots().get("direct-message-color-yellow").getLore();

		// Slot: direct-message-color-white
		int directMessageColorWhitePosition = plugin.gui.get("settings").getSlots().get("direct-message-color-white").getPosition();
		String directMessageColorWhiteMaterial = plugin.gui.get("settings").getSlots().get("direct-message-color-white").getMaterial();
		String directMessageColorWhiteName = plugin.gui.get("settings").getSlots().get("direct-message-color-white").getName();
		List<String> directMessageColorWhiteLore = plugin.gui.get("settings").getSlots().get("direct-message-color-white").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 0) {
			guiItem(inventory, directMessageColorBlackPosition, directMessageColorBlackMaterial, directMessageColorBlackName, directMessageColorBlackLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 1) {
			guiItem(inventory, directMessageColorDarkBluePosition, directMessageColorDarkBlueMaterial, directMessageColorDarkBlueName, directMessageColorDarkBlueLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 2) {
			guiItem(inventory, directMessageColorDarkGreenPosition, directMessageColorDarkGreenMaterial, directMessageColorDarkGreenName, directMessageColorDarkGreenLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 3) {
			guiItem(inventory, directMessageColorDarkAquaPosition, directMessageColorDarkAquaMaterial, directMessageColorDarkAquaName, directMessageColorDarkAquaLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 4) {
			guiItem(inventory, directMessageColorDarkRedPosition, directMessageColorDarkRedMaterial, directMessageColorDarkRedName, directMessageColorDarkRedLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 5) {
			guiItem(inventory, directMessageColorDarkPurplePosition, directMessageColorDarkPurpleMaterial, directMessageColorDarkPurpleName, directMessageColorDarkPurpleLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 6) {
			guiItem(inventory, directMessageColorGoldPosition, directMessageColorGoldMaterial, directMessageColorGoldName, directMessageColorGoldLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 7) {
			guiItem(inventory, directMessageColorGrayPosition, directMessageColorGrayMaterial, directMessageColorGrayName, directMessageColorGrayLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 8) {
			guiItem(inventory, directMessageColorDarkGrayPosition, directMessageColorDarkGrayMaterial, directMessageColorDarkGrayName, directMessageColorDarkGrayLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 9) {
			guiItem(inventory, directMessageColorBluePosition, directMessageColorBlueMaterial, directMessageColorBlueName, directMessageColorBlueLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 10) {
			guiItem(inventory, directMessageColorGreenPosition, directMessageColorGreenMaterial, directMessageColorGreenName, directMessageColorGreenLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 11) {
			guiItem(inventory, directMessageColorAquaPosition, directMessageColorAquaMaterial, directMessageColorAquaName, directMessageColorAquaLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 12) {
			guiItem(inventory, directMessageColorRedPosition, directMessageColorRedMaterial, directMessageColorRedName, directMessageColorRedLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 13) {
			guiItem(inventory, directMessageColorLightPurplePosition, directMessageColorLightPurpleMaterial, directMessageColorLightPurpleName, directMessageColorLightPurpleLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 14) {
			guiItem(inventory, directMessageColorYellowPosition, directMessageColorYellowMaterial, directMessageColorYellowName, directMessageColorYellowLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("direct_message_color").getValue() == 15) {
			guiItem(inventory, directMessageColorWhitePosition, directMessageColorWhiteMaterial, directMessageColorWhiteName, directMessageColorWhiteLore);
		}

		// Slot: teleport-request
		int teleportRequestPosition = plugin.gui.get("settings").getSlots().get("teleport-request").getPosition();
		String teleportRequestMaterial = plugin.gui.get("settings").getSlots().get("teleport-request").getMaterial();
		String teleportRequestName = plugin.gui.get("settings").getSlots().get("teleport-request").getName();
		List<String> teleportRequestLore = plugin.gui.get("settings").getSlots().get("teleport-request").getLore();
		guiItem(inventory, teleportRequestPosition, teleportRequestMaterial, teleportRequestName, teleportRequestLore);

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
			guiItem(inventory, teleportRequestOffPosition, teleportRequestOffMaterial, teleportRequestOffName, teleportRequestOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("teleport_request").getValue() == 1) {
			guiItem(inventory, teleportRequestOnPosition, teleportRequestOnMaterial, teleportRequestOnName, teleportRequestOnLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("teleport_request").getValue() == 2) {
			guiItem(inventory, teleportRequestFriendPosition, teleportRequestFriendMaterial, teleportRequestFriendName, teleportRequestFriendLore);
		}

		// Slot: home-privacy
		int homePrivacyPosition = plugin.gui.get("settings").getSlots().get("home-privacy").getPosition();
		String homePrivacyMaterial = plugin.gui.get("settings").getSlots().get("home-privacy").getMaterial();
		String homePrivacyName = plugin.gui.get("settings").getSlots().get("home-privacy").getName();
		List<String> homePrivacyLore = plugin.gui.get("settings").getSlots().get("home-privacy").getLore();
		guiItem(inventory, homePrivacyPosition, homePrivacyMaterial, homePrivacyName, homePrivacyLore);

		// Slot: home-privacy-off
		int homePrivacyOffPosition = plugin.gui.get("settings").getSlots().get("home-privacy-off").getPosition();
		String homePrivacyOffMaterial = plugin.gui.get("settings").getSlots().get("home-privacy-off").getMaterial();
		String homePrivacyOffName = plugin.gui.get("settings").getSlots().get("home-privacy-off").getName();
		List<String> homePrivacyOffLore = plugin.gui.get("settings").getSlots().get("home-privacy-off").getLore();

		// Slot: home-privacy-on
		int homePrivacyOnPosition = plugin.gui.get("settings").getSlots().get("home-privacy-on").getPosition();
		String homePrivacyOnMaterial = plugin.gui.get("settings").getSlots().get("home-privacy-on").getMaterial();
		String homePrivacyOnName = plugin.gui.get("settings").getSlots().get("home-privacy-on").getName();
		List<String> homePrivacyOnLore = plugin.gui.get("settings").getSlots().get("home-privacy-on").getLore();

		// Slot: home-privacy-friend
		int homePrivacyFriendPosition = plugin.gui.get("settings").getSlots().get("home-privacy-friend").getPosition();
		String homePrivacyFriendMaterial = plugin.gui.get("settings").getSlots().get("home-privacy-friend").getMaterial();
		String homePrivacyFriendName = plugin.gui.get("settings").getSlots().get("home-privacy-friend").getName();
		List<String> homePrivacyFriendLore = plugin.gui.get("settings").getSlots().get("home-privacy-friend").getLore();

		if (plugin.players.get(player.getName()).getSettings().get("home_privacy").getValue() == 0) {
			guiItem(inventory, homePrivacyOffPosition, homePrivacyOffMaterial, homePrivacyOffName, homePrivacyOffLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("home_privacy").getValue() == 1) {
			guiItem(inventory, homePrivacyOnPosition, homePrivacyOnMaterial, homePrivacyOnName, homePrivacyOnLore);
		} else if (plugin.players.get(player.getName()).getSettings().get("home_privacy").getValue() == 2) {
			guiItem(inventory, homePrivacyFriendPosition, homePrivacyFriendMaterial, homePrivacyFriendName, homePrivacyFriendLore);
		}

		player.openInventory(inventory);
	}
}
