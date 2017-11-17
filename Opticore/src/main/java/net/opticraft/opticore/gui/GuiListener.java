package net.opticraft.opticore.gui;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;
import net.opticraft.opticore.warp.WarpMethods;
import net.opticraft.opticore.world.WorldMethods;

public class GuiListener implements Listener {

	public Main plugin;

	public Config config;
	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;
	public WarpMethods warpMethods;
	public WorldMethods worldMethods;

	public GuiListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
		this.warpMethods = this.plugin.warpMethods;
		this.worldMethods = this.plugin.worldMethods;
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();

		if (plugin.playerReport.contains(playerName)) {
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (online.hasPermission("opticore.staff")) {
					online.sendMessage(ChatColor.LIGHT_PURPLE + playerName + " has submitted a report: " + message);
				}
			}
			plugin.playerReport.remove(playerName);
		}

		if (plugin.playerMessage.containsKey(playerName)) {
			String targetName = plugin.playerMessage.get(playerName);
			Player target = plugin.getServer().getPlayer(targetName);
			if (target != null) {
				target.sendMessage(ChatColor.LIGHT_PURPLE + "From " + playerName + ": " + message);
			} else {
				bungeecordMethods.sendMessageToPlayer(targetName, message);
			}
			plugin.playerMessage.remove(playerName);
		}

		if (plugin.guiSearch.containsKey(playerName)) {

			event.setCancelled(true);

			if (!message.contains(" ") && message.length() >= 1 && message.length() <= 16) {

				String search = message;

				if (plugin.guiSearch.get(playerName).equals("players")) {
					guiMethods.openPlayersGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("friends")) {
					guiMethods.openFriendsGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("warn")) {
					guiMethods.openWarnGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("mute")) {
					guiMethods.openMuteGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("freeze")) {
					guiMethods.openFreezeGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("kick")) {
					guiMethods.openKickGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("ban")) {
					guiMethods.openBanGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("tickets")) {
					guiMethods.openTicketsGui(player, search);

				} else if (plugin.guiSearch.get(playerName).equals("notes")) {
					guiMethods.openNotesGui(player, search);
				}

				plugin.guiSearch.remove(playerName);

			} else {
				player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must enter a username between 1 and 16 characters.");
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		final Player player = (Player) event.getWhoClicked();
		String uuid = player.getUniqueId().toString();
		ItemStack item = event.getCurrentItem();

		Inventory inventory = event.getInventory();
		String inventoryName = inventory.getName();

		if (event.getRawSlot() < inventory.getSize()) {
			if (item != null && (!item.getType().equals(Material.AIR))) {

				String itemName = item.getItemMeta().getDisplayName();

				// Home Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsServersName()))) {
						guiMethods.openServersGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsPlayersName()))) {
						guiMethods.openPlayersGui(player, null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsFriendsName()))) {
						guiMethods.openFriendsGui(player, null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsRewardsName()))) {
						guiMethods.openRewardsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsSettingsName()))) {
						guiMethods.openSettingsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsStaffName()))) {
						guiMethods.openStaffGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsWorldsName()))) {
						guiMethods.openWorldsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsWarpsName()))) {
						guiMethods.openWarpsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsApplicationsName()))) {
						guiMethods.openApplicationsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsRulesName()))) {
						player.closeInventory();
						guiMethods.openRulesGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiHomeContentsRanksName()))) {
						player.closeInventory();
						guiMethods.openRanksGui(player);
					}
				}

				// Servers Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiServersSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getServerName()))) {
						if (player.hasPermission("opticore.server." + config.getServerName().toLowerCase())) {
							player.closeInventory();
							player.sendMessage(ChatColor.RED + "You are already connected to the " + config.getServerName() + " server.");
							return;
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the " + config.getServerName() + " server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiServersContentsHubName()))) {
						if (player.hasPermission("opticore.server.hub")) {
							player.closeInventory();
							bungeecordMethods.sendPlayerToServer(player, "hub");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Hub server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiServersContentsSurvivalName()))) {
						if (player.hasPermission("opticore.server.survival")) {
							player.closeInventory();
							bungeecordMethods.sendPlayerToServer(player, "survival");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Survival server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiServersContentsCreativeName()))) {
						if (player.hasPermission("opticore.server.creative")) {
							player.closeInventory();
							bungeecordMethods.sendPlayerToServer(player, "creative");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Creative server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiServersContentsQuestName()))) {
						if (player.hasPermission("opticore.server.quest")) {
							player.closeInventory();
							bungeecordMethods.sendPlayerToServer(player, "quest");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Quest server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiServersContentsLegacyName()))) {
						if (player.hasPermission("opticore.server.legacy")) {
							player.closeInventory();
							//methods.sendPlayerToServer(player, "legacy");
							player.sendMessage(ChatColor.RED + "Sorry, this server is not currently linked to the network.");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Legacy server.");
						}
					}
				}

				// Players Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiPlayersSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Search")) {
						player.closeInventory();
						plugin.guiSearch.put(player.getName(), "players");
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "Enter a username to search:");
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					//check player skull click
				}

				// Friends Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiFriendsSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Search")) {
						player.closeInventory();
						plugin.guiSearch.put(player.getName(), "friends");
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "Enter a username to search:");
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					//check player skull click
				}

				// Rewards Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsContentsPointsName()))) {
						//guiMethods.openPointsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsContentsVoteName()))) {
						//guiMethods.openVoteGui(player);
						player.closeInventory();
						player.sendMessage(ChatColor.GOLD + "Visit www.opticraft.net/vote and enter your username upon voting.");
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsContentsDonateName()))) {
						//guiMethods.openDonateGui(player);
						player.closeInventory();
						player.sendMessage(ChatColor.GOLD + "Visit www.opticraft.net/donate and enter your username upon donating.");
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsContentsChallengesName()))) {
						//guiMethods.openChallengesGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiRewardsContentsDailyName()))) {
						//guiMethods.openDailyGui(player);
					}
				}

				// Settings Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsConnectDisconnectControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsConnectDisconnectControlNameOn()))) {
							plugin.players.get(uuid).setSettingsConnectDisconnect(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsConnectDisconnectControlNameOff()))) {
							plugin.players.get(uuid).setSettingsConnectDisconnect(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsServerChangeControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsServerChangeControlNameOn()))) {
							plugin.players.get(uuid).setSettingsServerChange(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsServerChangeControlNameOff()))) {
							plugin.players.get(uuid).setSettingsServerChange(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsPlayerChatControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsPlayerChatControlNameOn()))) {
							plugin.players.get(uuid).setSettingsPlayerChat(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsPlayerChatControlNameOff()))) {
							plugin.players.get(uuid).setSettingsPlayerChat(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsServerAnnouncementControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsServerAnnouncementControlNameOn()))) {
							plugin.players.get(uuid).setSettingsServerAnnouncement(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsServerAnnouncementControlNameOff()))) {
							plugin.players.get(uuid).setSettingsServerAnnouncement(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsFriendRequestControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsFriendRequestControlNameOn()))) {
							plugin.players.get(uuid).setSettingsFriendRequest(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsFriendRequestControlNameOff()))) {
							plugin.players.get(uuid).setSettingsFriendRequest(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsDirectMessageControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsDirectMessageControlNameOn()))) {
							plugin.players.get(uuid).setSettingsDirectMessage(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsDirectMessageControlNameOff()))) {
							plugin.players.get(uuid).setSettingsDirectMessage(2);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsDirectMessageControlNameFriend()))) {
							plugin.players.get(uuid).setSettingsDirectMessage(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsTeleportRequestControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsTeleportRequestControlNameOn()))) {
							plugin.players.get(uuid).setSettingsTeleportRequest(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsTeleportRequestControlNameOff()))) {
							plugin.players.get(uuid).setSettingsTeleportRequest(2);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsTeleportRequestControlNameFriend()))) {
							plugin.players.get(uuid).setSettingsTeleportRequest(1);
						}
						guiMethods.openSettingsGui(player);
					}

					if (event.getRawSlot() + 1 == config.getGuiSettingsContentsSpectateRequestControlSlot()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsSpectateRequestControlNameOn()))) {
							plugin.players.get(uuid).setSettingsSpectateRequest(0);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsSpectateRequestControlNameOff()))) {
							plugin.players.get(uuid).setSettingsSpectateRequest(2);
						} else if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiSettingsContentsSpectateRequestControlNameFriend()))) {
							plugin.players.get(uuid).setSettingsSpectateRequest(1);
						}
						guiMethods.openSettingsGui(player);
					}
				}

				// Worlds Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiWorldsSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					String world = ChatColor.stripColor(itemName);

					// Teleport player to clicked world
					if (plugin.worlds.containsKey(world)) {
						if (player.hasPermission("opticore.world.join." + plugin.worlds.get(world).getPermission())) {
							worldMethods.teleportPlayerToWorld(player, world);
						} else {
							player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
									ChatColor.GOLD + "You do not have permission to access the '" + world + "' world.");
						}
					}
				}

				// Warps Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiWarpsSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					String warp = ChatColor.stripColor(itemName);

					// Teleport player to clicked warp
					if (plugin.warps.containsKey(warp)) {
						if (player.hasPermission("opticore.warp." + warp.toLowerCase())) {
							warpMethods.teleportPlayerToWarp(player, warp);
						} else {
							player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
									ChatColor.GOLD + "You do not have permission to access the '" + warp + "' warp.");
						}
					}
				}

				// Applications Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiApplicationsSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}
				}

				// Player Gui
				if (inventoryName.startsWith("Player:")) {
					event.setCancelled(true);

					String[] parts = inventoryName.split(": ");
					String targetName = parts[1];
					Player target = plugin.getServer().getPlayer(targetName);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiPlayerContentsFriendName()))) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiPlayerContentsMessageName()))) {
						plugin.playerMessage.put(player.getName(), targetName);
						player.sendMessage(ChatColor.GREEN + "Enter your message to " + targetName + ":");
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiPlayerContentsTeleportName()))) {
						player.closeInventory();
						/*
						if (target != null) {
							Location location = target.getLocation();
							player.teleport(location);
						} else {
							//check if target is on another server
							String server = null;

							String hubPlayerList = bungeecordMethods.getServerPlayerList("hub");
							if (hubPlayerList.contains(targetName)) {
								server = "hub";
							}

							String survivalPlayerList = bungeecordMethods.getServerPlayerList("survival");
							if (survivalPlayerList.contains(targetName)) {
								server = "survival";
							}

							String creativePlayerList = bungeecordMethods.getServerPlayerList("creative");
							if (creativePlayerList.contains(targetName)) {
								server = "creative";
							}

							String questPlayerList = bungeecordMethods.getServerPlayerList("quest");
							if (questPlayerList.contains(targetName)) {
								server = "quest";
							}

							if (server != null) {
								bungeecordMethods.sendPlayerToServer(player, server);
								bungeecordMethods.sendTeleportInfo(player, targetName, server);
							}
						}
						 */
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiPlayerContentsSpectateName()))) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiPlayerContentsReportName()))) {
						//plugin.playerReport.add(player.getName());
						//player.sendMessage(ChatColor.GREEN + "You are about to report " + targetName + ". Enter a short message:");
						player.closeInventory();
					}
				}

				// Staff Gui
				if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', config.getGuiStaffSettingsInventoryName()))) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.WHITE + "Back")) {
						guiMethods.openHomeGui(player);
					}

					if (itemName.equals(ChatColor.WHITE + "Exit")) {
						player.closeInventory();
					}
				}
			}
		}
	}
}
