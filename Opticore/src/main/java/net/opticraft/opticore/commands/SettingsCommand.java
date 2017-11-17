package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Config;

public class SettingsCommand implements CommandExecutor {

	public Main plugin;

	public GuiMethods guiMethods;

	public Config config;

	public SettingsCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
		this.config = this.plugin.config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("settings") || cmd.getName().equalsIgnoreCase("setting")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {
					guiMethods.openSettingsGui(player);

				} else if (args.length == 2) {

					String setting = args[0];
					String value = args[1];

					int valueInt;

					if (value.equalsIgnoreCase("on")) {
						valueInt = 1;
					} else if (value.equalsIgnoreCase("off")) {
						valueInt = 0;
					} else if (value.equalsIgnoreCase("friends")) {
						valueInt = 2;
					} else {
						player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings <setting> on/off/friend");
						return true;
					}

					if (setting.equalsIgnoreCase("connect-disconnect")) {
						if (valueInt != 2) {
							plugin.players.get(player.getName()).setSettingsConnectDisconnect(valueInt);
						} else {
							player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings connect-disconnect on/off");
						}
					} else if (setting.equalsIgnoreCase("server-change")) {
						if (valueInt != 2) {
							plugin.players.get(player.getName()).setSettingsServerChange(valueInt);
						} else {
							player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings server-change on/off");
						}
					} else if (setting.equalsIgnoreCase("player-chat")) {
						if (valueInt != 2) {
							plugin.players.get(player.getName()).setSettingsPlayerChat(valueInt);
						} else {
							player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings player-chat on/off");
						}
					} else if (setting.equalsIgnoreCase("server-announcement")) {
						if (valueInt != 2) {
							plugin.players.get(player.getName()).setSettingsServerAnnouncement(valueInt);
						} else {
							player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings server-announcement on/off");
						}
					} else if (setting.equalsIgnoreCase("friend-request")) {
						plugin.players.get(player.getName()).setSettingsFriendRequest(valueInt);
						if (valueInt != 2) {
							plugin.players.get(player.getName()).setSettingsConnectDisconnect(valueInt);
						} else {
							player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings friend-request on/off");
						}
					} else if (setting.equalsIgnoreCase("direct-message")) {
						plugin.players.get(player.getName()).setSettingsDirectMessage(valueInt);
					} else if (setting.equalsIgnoreCase("teleport-request")) {
						plugin.players.get(player.getName()).setSettingsTeleportRequest(valueInt);
					} else if (setting.equalsIgnoreCase("spectate-request")) {
						plugin.players.get(player.getName()).setSettingsSpectateRequest(valueInt);
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings <setting> on/off/friend");
					}
					
				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /settings <setting> on/off/friend");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
