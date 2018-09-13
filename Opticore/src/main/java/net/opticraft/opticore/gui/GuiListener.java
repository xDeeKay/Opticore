package net.opticraft.opticore.gui;

import java.util.List;

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
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.settings.SettingsUtil;
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
	public ServerUtil serverUtil;
	public SettingsUtil settingsUtil;

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
		this.serverUtil = this.plugin.serverUtil;
		this.settingsUtil = this.plugin.settingsUtil;
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

				String server = serverUtil.getPlayerServer(target);

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

		ItemStack item = event.getCurrentItem();

		if (inventory.getHolder() instanceof GuiInventoryHolder) {

			event.setCancelled(true);

			if (event.getRawSlot() < inventory.getSize() && item != null && !item.getType().equals(Material.AIR)) {

				String title = ChatColor.translateAlternateColorCodes('&', inventory.getName());

				int position = event.getSlot() + 1;

				String material = item.getType().toString();

				String name = ChatColor.translateAlternateColorCodes('&', item.getItemMeta().getDisplayName());

				//List<String> lore = item.getItemMeta().getLore();

				for (String gui : plugin.gui.keySet()) {
					
					String guiTitle = plugin.gui.get(gui).getTitle(); //Homes: %player%
					
					String guiTitleStrip = guiTitle.replace("%player%", ""); //Homes: 
					
					//title = Homes: xDeeKay
					
					String target = title.replace(guiTitleStrip, "");//xDeeKay

					if (title.equals(guiTitle.replace("%player%", target))) {

						//player.sendMessage("gui:[" + gui + "]");

						//player.sendMessage("position:[" + position + "]");
						//player.sendMessage("material:[" + material + "]");
						//player.sendMessage("name:[" + name + "]");
						//player.sendMessage("lore:[" + lore + "]");

						for (String slot : plugin.gui.get(gui).getSlots().keySet()) {

							if (plugin.gui.get(gui).getSlots().get(slot).getPosition() == position && 
									plugin.gui.get(gui).getSlots().get(slot).getMaterial().equalsIgnoreCase(material) && 
									ChatColor.translateAlternateColorCodes('&', plugin.gui.get(gui).getSlots().get(slot).getName()).equals(name)) {

								//player.sendMessage("slot:[" + slot + "]");

								List<String> commands = plugin.gui.get(gui).getSlots().get(slot).getCommands();

								for (String command : commands) {
									plugin.getServer().dispatchCommand(player, command.replace("%warp%", name).replace("%world%", name));
								}

							} else if (slot.equals("playerlist") && position >= plugin.gui.get(gui).getSlots().get("playerlist").getPosition()) {
								List<String> commands = plugin.gui.get(gui).getSlots().get("playerlist").getCommands();
								for (String command : commands) {
									plugin.getServer().dispatchCommand(player, command.replace("%player%", ChatColor.stripColor(name)));
								}
							} else if (slot.equals("friendlist") && position >= plugin.gui.get(gui).getSlots().get("friendlist").getPosition()) {
								List<String> commands = plugin.gui.get(gui).getSlots().get("friendlist").getCommands();
								for (String command : commands) {
									plugin.getServer().dispatchCommand(player, command.replace("%player%", ChatColor.stripColor(name)));
								}
							}  else if (slot.equals("worldlist") && position >= plugin.gui.get(gui).getSlots().get("worldlist").getPosition()) {
								List<String> commands = plugin.gui.get(gui).getSlots().get("worldlist").getCommands();
								for (String command : commands) {
									plugin.getServer().dispatchCommand(player, command.replace("%world%", ChatColor.stripColor(name)));
								}
							}  else if (slot.equals("warplist") && position >= plugin.gui.get(gui).getSlots().get("warplist").getPosition()) {
								List<String> commands = plugin.gui.get(gui).getSlots().get("warplist").getCommands();
								for (String command : commands) {
									plugin.getServer().dispatchCommand(player, command.replace("%warp%", ChatColor.stripColor(name)));
								}
							}  else if (slot.equals("homelist") && position >= plugin.gui.get(gui).getSlots().get("homelist").getPosition()) {
								List<String> commands = plugin.gui.get(gui).getSlots().get("homelist").getCommands();
								for (String command : commands) {
									plugin.getServer().dispatchCommand(player, command.replace("%player%", ChatColor.stripColor(target)).replace("%home%", ChatColor.stripColor(name)));
								}
							}
						}
					}
				}
				
				// Settings Gui
				if (title.equals(plugin.gui.get("settings").getTitle())) {

					if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("tb1").getName()))) {
						guiUtil.openGui(player, "opticraft", null);
					}

					if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("tb9").getName()))) {
						player.closeInventory();
					}

					// Slot: connect-disconnect-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("connect-disconnect-on").getName()))) {
							settingsUtil.toggleSetting(player, "connect_disconnect");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: connect-disconnect-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("connect-disconnect-off").getName()))) {
							settingsUtil.toggleSetting(player, "connect_disconnect");
							guiUtil.openSettingsGui(player);
						}
					}

					// Slot: server-change-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-change-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-change-on").getName()))) {
							settingsUtil.toggleSetting(player, "server_change");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: server-change-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-change-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-change-off").getName()))) {
							settingsUtil.toggleSetting(player, "server_change");
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: player-chat-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("player-chat-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("player-chat-on").getName()))) {
							settingsUtil.toggleSetting(player, "player_chat");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: player-chat-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("player-chat-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("player-chat-off").getName()))) {
							settingsUtil.toggleSetting(player, "player_chat");
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: server-announcement-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-announcement-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-announcement-on").getName()))) {
							settingsUtil.toggleSetting(player, "server_announcement");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: server-announcement-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("server-announcement-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("server-announcement-off").getName()))) {
							settingsUtil.toggleSetting(player, "server_announcement");
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: friend-request-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("friend-request-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("friend-request-on").getName()))) {
							settingsUtil.toggleSetting(player, "friend_request");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: friend-request-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("friend-request-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("friend-request-off").getName()))) {
							settingsUtil.toggleSetting(player, "friend_request");
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: direct-message-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("direct-message-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("direct-message-on").getName()))) {
							settingsUtil.toggleSetting(player, "direct_message");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: direct-message-friend
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("direct-message-friend").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("direct-message-friend").getName()))) {
							settingsUtil.toggleSetting(player, "direct_message");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: direct-message-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("direct-message-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("direct-message-off").getName()))) {
							settingsUtil.toggleSetting(player, "direct_message");
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: teleport-request-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("teleport-request-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("teleport-request-on").getName()))) {
							settingsUtil.toggleSetting(player, "teleport_request");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: teleport-request-friend
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("teleport-request-friend").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("teleport-request-friend").getName()))) {
							settingsUtil.toggleSetting(player, "teleport_request");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: teleport-request-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("teleport-request-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("teleport-request-off").getName()))) {
							settingsUtil.toggleSetting(player, "teleport_request");
							guiUtil.openSettingsGui(player);
						}
					}
					
					// Slot: spectate-request-on
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("spectate-request-on").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("spectate-request-on").getName()))) {
							settingsUtil.toggleSetting(player, "spectate_request");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: spectate-request-friend
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("spectate-request-friend").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("spectate-request-friend").getName()))) {
							settingsUtil.toggleSetting(player, "spectate_request");
							guiUtil.openSettingsGui(player);
						}
					}
					// Slot: spectate-request-off
					if (event.getRawSlot() + 1 == plugin.gui.get("settings").getSlots().get("spectate-request-off").getPosition()) {
						if (name.equals(ChatColor.translateAlternateColorCodes('&', plugin.gui.get("settings").getSlots().get("spectate-request-off").getName()))) {
							settingsUtil.toggleSetting(player, "spectate_request");
							guiUtil.openSettingsGui(player);
						}
					}
				}
			}
		}
	}
}
