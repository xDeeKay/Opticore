package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class ServerCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;
	
	public Config config;

	public ServerCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
		this.config = this.plugin.config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("server") || cmd.getName().equalsIgnoreCase("servers")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {

					if (!plugin.playerCount.containsKey("hub")) {
						bungeecordMethods.requestServerPlayerCount("hub");
					}
					if (!plugin.playerCount.containsKey("survival")) {
						bungeecordMethods.requestServerPlayerCount("survival");
					}
					if (!plugin.playerCount.containsKey("creative")) {
						bungeecordMethods.requestServerPlayerCount("creative");
					}
					if (!plugin.playerCount.containsKey("quest")) {
						bungeecordMethods.requestServerPlayerCount("quest");
					}
					/*
					if (!plugin.playerCount.containsKey("legacy")) {
						bungeecordMethods.requestServerPlayerCount("legacy");
					}
					 */

					guiMethods.openServersGui(player);

				} else if (args.length == 1) {

					String server = args[0];
					
					if (server.equalsIgnoreCase(config.getServerName())) {
						if (player.hasPermission("opticore.server." + config.getServerName().toLowerCase())) {
							player.sendMessage(ChatColor.RED + "You are already connected to the " + config.getServerName() + " server.");
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the " + config.getServerName() + " server.");
						}
					}

					if (server.equalsIgnoreCase("hub")) {
						if (player.hasPermission("opticore.server.hub")) {
							bungeecordMethods.sendPlayerToServer(player, "hub");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Hub server.");
						}

					} else if (server.equalsIgnoreCase("survival")) {
						if (player.hasPermission("opticore.server.survival")) {
							bungeecordMethods.sendPlayerToServer(player, "survival");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Survival server.");
						}

					} else if (server.equalsIgnoreCase("creative")) {
						if (player.hasPermission("opticore.server.creative")) {
							bungeecordMethods.sendPlayerToServer(player, "creative");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Creative server.");
						}

					} else if (server.equalsIgnoreCase("quest")) {
						if (player.hasPermission("opticore.server.quest")) {
							bungeecordMethods.sendPlayerToServer(player, "quest");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Quest server.");
						}

					} else if (server.equalsIgnoreCase("legacy")) {
						/*
						if (player.hasPermission("opticore.server.legacy")) {
							bungeecordMethods.sendPlayerToServer(player, "legacy");
						} else {
							player.sendMessage(ChatColor.RED + "You do not have permission to access the Legacy server.");
						}
						 */
						player.sendMessage(ChatColor.RED + "Sorry, this server is not currently linked to the network.");

					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect server name. Availabe servers: Hub, Survival, Creative, Quest, Legacy");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /server <server name>");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
