package net.opticraft.opticore.team;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class TeamCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;
	public TeamUtil teamUtil;

	public Util util;

	public TeamCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.teamUtil = this.plugin.teamUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("team")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length > 2) {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /team create/delete/join/leave/setspawn/setinventory/setgamemode <team>");

				} else if (args.length == 2) {

					String subCommand = args[0];
					String team = args[1];

					if (subCommand.equalsIgnoreCase("create")) {
						if (player.hasPermission("opticore.team.create")) {
							if (!teamUtil.teamExists(team)) {
								teamUtil.create(team, player);
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Created team '" + team + "'.");
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' already exists.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (subCommand.equalsIgnoreCase("delete")) {
						if (player.hasPermission("opticore.team.delete")) {
							if (teamUtil.teamExists(team)) {
								teamUtil.delete(team);
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Deleted team '" + team + "'.");
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (subCommand.equalsIgnoreCase("join")) {
						if (player.hasPermission("opticore.team.join")) {
							if (teamUtil.teamExists(team)) {
								if (!teamUtil.isMember(team, player)) {
									if (teamUtil.getTeam(player) == null) {
										teamUtil.join(team, player);
										util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Joined team '" + team + "'.");
									} else {
										util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are already a member of team '" + teamUtil.getTeam(player) + "'.");
									}
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are already a member of team '" + team + "'.");
								}
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (subCommand.equalsIgnoreCase("leave")) {
						if (player.hasPermission("opticore.team.leave")) {
							if (teamUtil.teamExists(team)) {
								if (teamUtil.isMember(team, player)) {
									teamUtil.leave(team, player);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Left team '" + team + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are not a member of team '" + team + "'.");
								}
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (subCommand.equalsIgnoreCase("setspawn")) {
						if (player.hasPermission("opticore.team.setspawn")) {
							if (teamUtil.teamExists(team)) {
								Location location = player.getLocation();
								teamUtil.setSpawn(team, location);
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set spawn for team '" + team + "' in your current location.");
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (subCommand.equalsIgnoreCase("setinventory")) {
						if (player.hasPermission("opticore.team.setinventory")) {
							if (teamUtil.teamExists(team)) {
								ItemStack[] inventory = player.getInventory().getContents();
								teamUtil.setInventory(team, inventory);
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set inventory for team '" + team + "' with your current inventory.");
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else if (subCommand.equalsIgnoreCase("setgamemode")) {
						if (player.hasPermission("opticore.team.setgamemode")) {
							if (teamUtil.teamExists(team)) {
								String gamemode = player.getGameMode().name();
								teamUtil.setGamemode(team, gamemode);
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set gamemode for team '" + team + "' with your current gamemode.");
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The team '" + team + "' does not exist.");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to do that.");
						}

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /team create/delete/join/leave/setspawn/setinventory/setgamemode <team>");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /team create/delete/join/leave/setspawn/setinventory/setgamemode <team>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
