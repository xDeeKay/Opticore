package net.opticraft.opticore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class OpticraftCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;

	public Methods methods;

	public OpticraftCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
		this.methods = this.plugin.methods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("opticraft") || cmd.getName().equalsIgnoreCase("opti") || cmd.getName().equalsIgnoreCase("oc")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {

					guiMethods.openHomeGui(player);

				} else if (args.length == 1) {

					if (args[0].equalsIgnoreCase("server") || args[0].equalsIgnoreCase("servers")) {
						if (player.hasPermission("opticore.server")) {
							guiMethods.openServersGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {
						if (player.hasPermission("opticore.player")) {
							guiMethods.openPlayersGui(player, null);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("friend") || args[0].equalsIgnoreCase("friends") || args[0].equalsIgnoreCase("f")) {
						if (player.hasPermission("opticore.friend")) {
							guiMethods.openFriendsGui(player, null);
						} else {

						}

					} else if (args[0].equalsIgnoreCase("rewards") || args[0].equalsIgnoreCase("reward")) {
						if (player.hasPermission("opticore.rewards")) {
							guiMethods.openRewardsGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("settings") || args[0].equalsIgnoreCase("setting")) {
						if (player.hasPermission("opticore.settings")) {
							guiMethods.openSettingsGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("world") || args[0].equalsIgnoreCase("worlds")) {
						if (player.hasPermission("opticore.world")) {
							guiMethods.openWorldsGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("warp") || args[0].equalsIgnoreCase("warps")) {
						if (player.hasPermission("opticore.warp")) {
							guiMethods.openWarpsGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("homes")) {
						if (player.hasPermission("opticore.home")) {
							guiMethods.openHomesGui(player, player.getName());
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("rules")) {
						if (player.hasPermission("opticore.rules")) {
							guiMethods.openRulesGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("ranks")) {
						if (player.hasPermission("opticore.ranks")) {
							guiMethods.openRanksGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (args[0].equalsIgnoreCase("staff") && player.hasPermission("opticore.staff")) {
						if (player.hasPermission("opticore.staff")) {
							guiMethods.openStaffGui(player);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft server|player|friend|rewards|settings|world|warp|home|rules|ranks");
					}
				} else if (args.length == 1) {

					String target = args[1].toString();

					if (args[0].equalsIgnoreCase("player") || args[0].equalsIgnoreCase("players")) {
						if (player.hasPermission("opticore.player")) {
							guiMethods.openPlayerGui(player, target);
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft player <arg>");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /opticraft server|player|friend|rewards|settings|world|warp|home|rules|ranks");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
