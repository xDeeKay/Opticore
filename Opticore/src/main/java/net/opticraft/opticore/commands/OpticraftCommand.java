package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class OpticraftCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordUtil bungeecordUtil;
	public GuiUtil guiUtil;

	public Util util;

	public OpticraftCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("opticraft") || cmd.getName().equalsIgnoreCase("opti") || cmd.getName().equalsIgnoreCase("oc")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "home", null);

				} else if (args.length == 1) {

					if (args[0].equalsIgnoreCase("server") || args[0].equalsIgnoreCase("servers")) {
						if (player.hasPermission("opticore.server")) {
							guiUtil.openGui(player, "servers", null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {
						if (player.hasPermission("opticore.player")) {
							guiUtil.openGui(player, "players", null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("friend") || args[0].equalsIgnoreCase("friends") || args[0].equalsIgnoreCase("f")) {
						if (player.hasPermission("opticore.friend")) {
							guiUtil.openGui(player, "friends", null);
						} else {

						}

					} else if (args[0].equalsIgnoreCase("rewards") || args[0].equalsIgnoreCase("reward")) {
						if (player.hasPermission("opticore.rewards")) {
							guiUtil.openGui(player, "rewards", null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("settings") || args[0].equalsIgnoreCase("setting")) {
						if (player.hasPermission("opticore.settings")) {
							guiUtil.openSettingsGui(player);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("world") || args[0].equalsIgnoreCase("worlds")) {
						if (player.hasPermission("opticore.world")) {
							guiUtil.openGui(player, "worlds", null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("warp") || args[0].equalsIgnoreCase("warps")) {
						if (player.hasPermission("opticore.warp")) {
							guiUtil.openGui(player, "warps", null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("homes")) {
						if (player.hasPermission("opticore.home")) {
							guiUtil.openGui(player, "homes", player.getName());
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("rules")) {
						if (player.hasPermission("opticore.rules")) {
							guiUtil.openRulesGui(player);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("ranks")) {
						if (player.hasPermission("opticore.ranks")) {
							guiUtil.openRanksGui(player);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("staff") && player.hasPermission("opticore.staff")) {
						if (player.hasPermission("opticore.staff")) {
							guiUtil.openGui(player, "staff", null);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft server|player|friend|rewards|settings|world|warp|home|rules|ranks");
					}
				} else if (args.length == 2) {

					String target = args[1];

					if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {
						if (player.hasPermission("opticore.player")) {
							guiUtil.openGui(player, "player", target);
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft player <arg>");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft server|player|friend|rewards|settings|world|warp|home|rules|ranks");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
