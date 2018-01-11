package net.opticraft.opticore.server;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class ServerCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public GuiUtil guiUtil;

	public ServerCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.guiUtil = this.plugin.guiUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("server") || cmd.getName().equalsIgnoreCase("servers")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {

					if (!plugin.playerCount.containsKey("hub")) {
						bungeecordUtil.requestServerPlayerCount("hub");
					}
					if (!plugin.playerCount.containsKey("survival")) {
						bungeecordUtil.requestServerPlayerCount("survival");
					}
					if (!plugin.playerCount.containsKey("creative")) {
						bungeecordUtil.requestServerPlayerCount("creative");
					}
					if (!plugin.playerCount.containsKey("quest")) {
						bungeecordUtil.requestServerPlayerCount("quest");
					}
					/*
					if (!plugin.playerCount.containsKey("legacy")) {
						bungeecordUtil.requestServerPlayerCount("legacy");
					}
					 */

					guiUtil.openGui(player, "servers", null);

				} else if (args.length == 1) {

					String server = args[0];

					if (server.equalsIgnoreCase(config.getServerName())) {
						if (player.hasPermission("opticore.server." + config.getServerName().toLowerCase())) {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are already connected to the " + config.getServerName() + " server.");
							return true;
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the " + config.getServerName() + " server.");
						}
					}

					if (server.equalsIgnoreCase("hub")) {
						if (player.hasPermission("opticore.server.hub")) {
							bungeecordUtil.sendPlayerToServer(player, "hub");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the Hub server.");
						}

					} else if (server.equalsIgnoreCase("survival")) {
						if (player.hasPermission("opticore.server.survival")) {
							bungeecordUtil.sendPlayerToServer(player, "survival");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the Survival server.");
						}

					} else if (server.equalsIgnoreCase("creative")) {
						if (player.hasPermission("opticore.server.creative")) {
							bungeecordUtil.sendPlayerToServer(player, "creative");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the Creative server.");
						}

					} else if (server.equalsIgnoreCase("quest")) {
						if (player.hasPermission("opticore.server.quest")) {
							bungeecordUtil.sendPlayerToServer(player, "quest");
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the Quest server.");
						}

					} else if (server.equalsIgnoreCase("legacy")) {
						/*
						if (player.hasPermission("opticore.server.legacy")) {
							bungeecordUtil.sendPlayerToServer(player, "legacy");
						} else {
							util.sendStyledMessage(player, "RED", "/", "GOLD", "You do not have permission to access the Legacy server.");
						}
						 */
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Sorry, this server is not currently linked to the network.");

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Unknown server. Availabe servers: Hub, Survival, Creative, Quest, Legacy");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /server <server-name>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
