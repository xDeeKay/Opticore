package net.opticraft.opticore.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.home.HomeUtil;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;
import net.opticraft.opticore.warp.WarpUtil;
import net.opticraft.opticore.world.WorldUtil;

public class GuiListener implements Listener {

	public Main plugin;

	public Config config;
	public MySQL mysql;
	public BungeecordUtil bungeecordUtil;
	public GuiUtil guiUtil;
	public WarpUtil warpUtil;
	public WorldUtil worldUtil;
	public HomeUtil homeUtil;
	public TeleportUtil teleportUtil;
	public Util util;

	public GuiListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.mysql = this.plugin.mysql;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.warpUtil = this.plugin.warpUtil;
		this.worldUtil = this.plugin.worldUtil;
		this.homeUtil = this.plugin.homeUtil;
		this.teleportUtil = this.plugin.teleportUtil;
		this.util = this.plugin.util;
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();

		// Player is entering a message to submit a report
		if (plugin.playerReport.contains(playerName)) {
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (online.hasPermission("opticore.staff")) {
					online.sendMessage(ChatColor.LIGHT_PURPLE + playerName + " has submitted a report: " + message);
				}
			}
			plugin.playerReport.remove(playerName);
		}

		// Player is entering a message to send to a player
		if (plugin.playerMessage.containsKey(playerName)) {

			String target = plugin.playerMessage.get(playerName);

			if (target != null) {

				Player targetPlayer = plugin.getServer().getPlayer(target);

				util.sendStyledMessage(null, player, "LIGHT_PURPLE", "M", "WHITE", "You > " + targetPlayer.getName() + ": " + ChatColor.GRAY + message);
				util.sendStyledMessage(null, targetPlayer, "LIGHT_PURPLE", "M", "WHITE", player.getName() + " > You: " + ChatColor.GRAY + message);

				plugin.players.get(player.getName()).setLastMessageFrom(targetPlayer.getName());
				plugin.players.get(targetPlayer.getName()).setLastMessageFrom(player.getName());

			} else {

				String server = bungeecordUtil.getPlayerServer(target);

				if (server != null) {
					// Target is on another server

					bungeecordUtil.sendMessageToPlayer(player.getName(), target, server, message);
					util.sendStyledMessage(null, player, "LIGHT_PURPLE", "M", "WHITE", "You > " + target + ": " + ChatColor.GRAY + message);

					plugin.players.get(player.getName()).setLastMessageFrom(target);

				} else {
					// Target is offline
					util.sendStyledMessage(null, player, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
				}
			}
			plugin.playerMessage.remove(playerName);
		}

		if (plugin.guiSearch.containsKey(playerName)) {

			event.setCancelled(true);

			if (!message.contains(" ") && message.length() >= 1 && message.length() <= 16) {

				//String search = message;

				if (plugin.guiSearch.get(playerName).equals("players")) {
					guiUtil.openGui(player, "players", null);

				} else if (plugin.guiSearch.get(playerName).equals("friends")) {
					guiUtil.openGui(player, "friends", null);

				} else if (plugin.guiSearch.get(playerName).equals("warn")) {
					//guiUtil.openGui(player, "warn", search);

				} else if (plugin.guiSearch.get(playerName).equals("mute")) {
					//guiUtil.openGui(player, "mute", search);

				} else if (plugin.guiSearch.get(playerName).equals("freeze")) {
					//guiUtil.openGui(player, "freeze", search);

				} else if (plugin.guiSearch.get(playerName).equals("kick")) {
					//guiUtil.openGui(player, "kick", search);

				} else if (plugin.guiSearch.get(playerName).equals("ban")) {
					//guiUtil.openGui(player, "ban", search);

				} else if (plugin.guiSearch.get(playerName).equals("tickets")) {
					//guiUtil.openGui(player, "tickets", search);

				} else if (plugin.guiSearch.get(playerName).equals("notes")) {
					//guiUtil.openGui(player, "notes", search);
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

		Inventory inventory = event.getInventory();
		String inventoryName = ChatColor.translateAlternateColorCodes('&', inventory.getName());
		System.out.println("inventoryName:[" + inventoryName + "]");

		ItemStack item = event.getCurrentItem();

		if (inventory.getHolder() instanceof GuiInventoryHolder) {
			event.setCancelled(true);
			
			System.out.println("Is custom gui");

			if (event.getRawSlot() < inventory.getSize() && item != null && !item.getType().equals(Material.AIR)) {

				String itemName = item.getItemMeta().getDisplayName();
				System.out.println("itemName:[" + itemName + "]");

				// Home Gui
				if (inventoryName.equals(plugin.gui.get("home").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getToolbars().get("exit").getName())))) {
						player.closeInventory();
					}

					System.out.println("servers:[" + plugin.gui.get("home").getSlots().get("servers").getName() + "]");
					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("servers").getName())))) {
						guiUtil.openGui(player, "servers", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("players").getName())))) {
						guiUtil.openGui(player, "players", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("friends").getName())))) {
						guiUtil.openGui(player, "friends", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("rewards").getName())))) {
						guiUtil.openGui(player, "rewards", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("settings").getName())))) {
						guiUtil.openSettingsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("staff").getName())))) {
						guiUtil.openGui(player, "staff", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("worlds").getName())))) {
						guiUtil.openGui(player, "worlds", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("warps").getName())))) {
						guiUtil.openGui(player, "warps", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("homes").getName())))) {
						guiUtil.openGui(player, "homes", player.getName());
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("applications").getName())))) {
						guiUtil.openGui(player, "applications", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("rules").getName())))) {
						player.closeInventory();
						guiUtil.openRulesGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("home").getSlots().get("ranks").getName()))) {
						player.closeInventory();
						guiUtil.openRanksGui(player);
					}
				}

				// Servers Gui
				if (inventoryName.equals(plugin.gui.get("servers").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getToolbars().get("back").getName())))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getToolbars().get("exit").getName())))) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', config.getServerName())))) {
						player.closeInventory();
						if (player.hasPermission("opticore.server." + config.getServerName().toLowerCase())) {
							player.sendMessage(ChatColor.RED + "You are already connected to the " + config.getServerName() + " server.");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the " + config.getServerName() + " server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getSlots().get("hub").getName())))) {
						player.closeInventory();
						if (player.hasPermission("opticore.server.hub")) {
							bungeecordUtil.sendPlayerToServer(player, "hub");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Hub server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getSlots().get("survival").getName())))) {
						player.closeInventory();
						if (player.hasPermission("opticore.server.survival")) {
							bungeecordUtil.sendPlayerToServer(player, "survival");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Survival server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getSlots().get("creative").getName())))) {
						player.closeInventory();
						if (player.hasPermission("opticore.server.creative")) {
							bungeecordUtil.sendPlayerToServer(player, "creative");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Creative server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getSlots().get("quest").getName())))) {
						player.closeInventory();
						if (player.hasPermission("opticore.server.quest")) {
							bungeecordUtil.sendPlayerToServer(player, "quest");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Quest server.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("servers").getSlots().get("legacy").getName())))) {
						player.closeInventory();
						if (player.hasPermission("opticore.server.legacy")) {
							//methods.sendPlayerToServer(player, "legacy");
							player.sendMessage(ChatColor.RED + "Sorry, this server is not currently linked to the network.");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Legacy server.");
						}
					}
				}

				// Players Gui
				if (inventoryName.equals(plugin.gui.get("players").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("players").getToolbars().get("back").getName())))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("players").getToolbars().get("task").getName())))) {
						player.closeInventory();
						plugin.guiSearch.put(player.getName(), "players");
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Enter a username to search:");
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', plugin.gui.get("players").getToolbars().get("exit").getName())))) {
						player.closeInventory();
					}

					// Skull click
				}

				// Friends Gui
				if (inventoryName.equals(plugin.gui.get("friends").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("friends").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("friends").getToolbars().get("task").getName()))) {
						player.closeInventory();
						plugin.guiSearch.put(player.getName(), "friends");
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Enter a username to search:");
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("friends").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					// Skull click
				}

				// Rewards Gui
				if (inventoryName.equals(plugin.gui.get("rewards").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getSlots().get("points").getName()))) {
						//guiUtil.openPointsGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getSlots().get("vote").getName()))) {
						//guiUtil.openVoteGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getSlots().get("donate").getName()))) {
						//guiUtil.openDonateGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getSlots().get("challenges").getName()))) {
						//guiUtil.openChallengesGui(player);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("rewards").getSlots().get("daily").getName()))) {
						//guiUtil.openDailyGui(player);
					}
				}

				// Settings Gui
				if (inventoryName.equals(plugin.gui.get("settings").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					// Slot: connect-disconnect-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsConnectDisconnect(0);
							mysql.setUsersColumnValue(player.getName(), "setting_connect_disconnect", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: connect-disconnect-ff
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsConnectDisconnect(1);
							mysql.setUsersColumnValue(player.getName(), "setting_connect_disconnect", 1);
							guiUtil.openSettingsGui(player);
						}
					}

					// Slot: server-change-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-change-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-change-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsServerChange(0);
							mysql.setUsersColumnValue(player.getName(), "setting_server_change", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: server-change-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-change-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-change-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsServerChange(1);
							mysql.setUsersColumnValue(player.getName(), "setting_server_change", 1);
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: player-change-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("player-chat-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("player-chat-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsPlayerChat(0);
							mysql.setUsersColumnValue(player.getName(), "setting_player_chat", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: player-change-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("player-chat-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("player-chat-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsPlayerChat(1);
							mysql.setUsersColumnValue(player.getName(), "setting_player_chat", 1);
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: server-announcement-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-announcement-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-announcement-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsServerAnnouncement(0);
							mysql.setUsersColumnValue(player.getName(), "setting_server_announcement", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: server-announcement-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-announcement-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-announcement-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsServerAnnouncement(1);
							mysql.setUsersColumnValue(player.getName(), "setting_server_announcement", 1);
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: friend-request-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("friend-request-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("friend-request-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsFriendRequest(0);
							mysql.setUsersColumnValue(player.getName(), "setting_friend_request", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: friend-request-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("friend-request-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("friend-request-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsFriendRequest(1);
							mysql.setUsersColumnValue(player.getName(), "setting_friend_request", 1);
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: direct-message-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("direct-message-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("direct-message-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsDirectMessage(2);
							mysql.setUsersColumnValue(player.getName(), "setting_direct_message", 2);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: direct-message-friend
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("direct-message-friend").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("direct-message-friend").getName()))) {
							plugin.players.get(player.getName()).setSettingsDirectMessage(0);
							mysql.setUsersColumnValue(player.getName(), "setting_direct_message", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: direct-message-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("direct-message-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("direct-message-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsDirectMessage(1);
							mysql.setUsersColumnValue(player.getName(), "setting_direct_message", 1);
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: teleport-request-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("teleport-request-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("teleport-request-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsTeleportRequest(2);
							mysql.setUsersColumnValue(player.getName(), "setting_teleport_request", 2);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: teleport-request-friend
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("teleport-request-friend").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("teleport-request-friend").getName()))) {
							plugin.players.get(player.getName()).setSettingsTeleportRequest(0);
							mysql.setUsersColumnValue(player.getName(), "setting_teleport_request", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: teleport-request-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("teleport-request-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("teleport-request-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsTeleportRequest(1);
							mysql.setUsersColumnValue(player.getName(), "setting_teleport_request", 1);
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: spectate-request-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("spectate-request-on").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("spectate-request-on").getName()))) {
							plugin.players.get(player.getName()).setSettingsSpectateRequest(2);
							mysql.setUsersColumnValue(player.getName(), "setting_spectate_request", 2);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: spectate-request-friend
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("spectate-request-friend").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("spectate-request-friend").getName()))) {
							plugin.players.get(player.getName()).setSettingsSpectateRequest(0);
							mysql.setUsersColumnValue(player.getName(), "setting_spectate_request", 0);
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: spectate-request-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("spectate-request-off").getPosition()) {
						if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("spectate-request-off").getName()))) {
							plugin.players.get(player.getName()).setSettingsSpectateRequest(1);
							mysql.setUsersColumnValue(player.getName(), "setting_spectate_request", 1);
							guiUtil.openSettingsGui(player);
						}
					}
				}

				// Worlds Gui
				if (inventoryName.equals(plugin.gui.get("worlds").getTitle())) {
					
					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("worlds").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("worlds").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					String world = ChatColor.stripColor(itemName);

					// Teleport player to clicked world
					if (plugin.worlds.containsKey(world)) {
						if (player.hasPermission("opticore.world.join." + plugin.worlds.get(world).getPermission())) {
							worldUtil.teleportPlayerToWorld(player, world);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the '" + world + "' world.");
						}
					}
				}

				// Warps Gui
				if (inventoryName.equals(plugin.gui.get("warps").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("warps").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("warps").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					String warp = ChatColor.stripColor(itemName);

					// Teleport player to clicked warp
					if (plugin.warps.containsKey(warp)) {
						if (player.hasPermission("opticore.warp." + warp.toLowerCase())) {
							warpUtil.teleportPlayerToWarp(player, warp);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the '" + warp + "' warp.");
						}
					}
				}

				// Gui: Homes
				if (inventoryName.startsWith("Homes: ")) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("homes").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("homes").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					String[] parts = inventoryName.split(": ");
					String target = parts[1];

					String home = ChatColor.stripColor(itemName);

					// Teleport player to clicked home
					if (homeUtil.homeExists(target, home)) {
						if (!homeUtil.getLock(target, home) || player.getName().equalsIgnoreCase(target) || player.hasPermission("opticore.lockhome.bypass")) {
							homeUtil.teleportPlayerToHome(player, target, home);
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to home '" + home + "' of '" + target + "'.");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The home '" + home + "' of '" + target + "' is locked.");
						}
					}
				}

				// Applications Gui
				if (inventoryName.equals(plugin.gui.get("applications").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("applications").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("applications").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}
				}

				// Player Gui
				if (inventoryName.startsWith("Player: ")) {

					String[] parts = inventoryName.split(": ");
					String targetName = parts[1];

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getSlots().get("friend").getName()))) {
						//player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getSlots().get("message").getName()))) {
						plugin.playerMessage.put(player.getName(), targetName);
						util.sendStyledMessage(player, null, "LIGHT_PURPLE", "M", "GOLD", "Enter your message to " + targetName + ":");
						player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getSlots().get("teleport").getName()))) {

						if (teleportUtil.getTeleportTo(player) == null) {

							if (plugin.getServer().getPlayer(targetName) != null) {

								Player target = plugin.getServer().getPlayer(targetName);

								teleportUtil.teleportRequest(player.getName(), target.getName());

								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Sent teleport request to player '" + target.getName() + "'.");

							} else {
								// Target is offline or on another server

								String server = bungeecordUtil.getPlayerServer(targetName);

								if (server != null) {
									bungeecordUtil.sendTeleportInfo(player.getName(), targetName, server, "tpr", "");

									plugin.players.get(player.getName()).setTeleportTo(targetName);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Sent teleport request to player '" + targetName + "'.");

								} else {
									// Target is offline

									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
								}
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have already sent a teleport request.");
						}
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getSlots().get("spectate").getName()))) {
						//player.closeInventory();
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("player").getSlots().get("report").getName()))) {
						//plugin.playerReport.add(player.getName());
						//player.sendMessage(ChatColor.GREEN + "You are about to report " + targetName + ". Enter a short message:");
						//player.closeInventory();
					}
				}

				// Staff Gui
				if (inventoryName.equals(plugin.gui.get("staff").getTitle())) {

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("staff").getToolbars().get("back").getName()))) {
						guiUtil.openGui(player, "home", null);
					}

					if (itemName.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("staff").getToolbars().get("exit").getName()))) {
						player.closeInventory();
					}
				}
			}
		}
	}
}
