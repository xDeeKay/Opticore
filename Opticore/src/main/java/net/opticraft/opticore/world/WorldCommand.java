package net.opticraft.opticore.world;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class WorldCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public WorldUtil worldUtil;

	public Util util;

	public WorldCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.worldUtil = this.plugin.worldUtil;
		this.util = this.plugin.util;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("world") || cmd.getName().equalsIgnoreCase("worlds")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "worlds", null);

				} else if (args.length == 1) {

					String world = worldUtil.resolveWorld(args[0]);

					if (worldUtil.worldExists(world)) {

						if (worldUtil.isOwner(player, world) || worldUtil.isMember(player, world) || worldUtil.isGuest(player, world) || worldUtil.isSpectator(player, world)) {

							player.teleport(worldUtil.getWorldLocation(world));

							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to world '" + world + "'.");

						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to join the world '" + world + "'.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The world '" + world + "' does not exist.");
					}

				} else if (args.length == 2) {

					String world = args[0];
					String flag = args[1];

					if (worldUtil.worldExists(world)) {
						if (worldUtil.isOwner(player, world)) {

							if (flag.equalsIgnoreCase("listowners")) {
								worldUtil.listOwners(player, world);

							} else if (flag.equalsIgnoreCase("listmembers")) {
								worldUtil.listMembers(player, world);

							} else if (flag.equalsIgnoreCase("listguests")) {
								worldUtil.listGuests(player, world);

							} else if (flag.equalsIgnoreCase("listspectators")) {
								worldUtil.listSpectators(player, world);

							} else if (flag.equalsIgnoreCase("setspawn")) {
								Location location = player.getLocation();
								worldUtil.setSpawn(world, location);
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set spawn for world '" + world + "' in your current location.");

							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /world <world> listowners/listmembers/listguests/listspectators/setspawn");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are not an owner of the world '" + world + "'.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The world '" + world + "' does not exist.");
					}

				} else if (args.length == 3) {

					String world = args[0];
					String flag = args[1];

					String target = args[2];
					String targetUUID = plugin.getServer().getOfflinePlayer(target).getUniqueId().toString();

					if (worldUtil.worldExists(world)) {
						if (worldUtil.isOwner(player, world)) {

							if (flag.equalsIgnoreCase("setgroup")) {
								if (target.equalsIgnoreCase("none") || target.equalsIgnoreCase("spectator") || target.equalsIgnoreCase("guest") || target.equalsIgnoreCase("member") || target.equalsIgnoreCase("owner")) {
									worldUtil.setGroup(world, target);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Set default group for world '" + world + "' to '" + target + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /world <world> setgroup none|spectator|guest|member|owner");
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "none: " + ChatColor.RED + "join" + ChatColor.GOLD + ", " + ChatColor.RED + "interact" + ChatColor.GOLD + ", " + ChatColor.RED + "build" + ChatColor.GOLD + ", " + ChatColor.RED +  "manage");
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "spectator: " + ChatColor.GREEN + "join" + ChatColor.GOLD + ", " + ChatColor.RED + "interact" + ChatColor.GOLD + ", " + ChatColor.RED +  "build" + ChatColor.GOLD + ", " + ChatColor.RED +  "manage");
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "guest: " + ChatColor.GREEN + "join" + ChatColor.GOLD + ", " + ChatColor.GREEN + "interact" + ChatColor.GOLD + ", " + ChatColor.RED +  "build" + ChatColor.GOLD + ", " + ChatColor.RED +  "manage");
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "member: " + ChatColor.GREEN + "join" + ChatColor.GOLD + ", " + ChatColor.GREEN + "interact" + ChatColor.GOLD + ", " + ChatColor.GREEN +  "build" + ChatColor.GOLD + ", " + ChatColor.RED +  "manage");
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "owner: " + ChatColor.GREEN + "join" + ChatColor.GOLD + ", " + ChatColor.GREEN + "interact" + ChatColor.GOLD + ", " + ChatColor.GREEN +  "build" + ChatColor.GOLD + ", " + ChatColor.GREEN +  "manage");
								}
							} else if (flag.equalsIgnoreCase("addowner")) {
								if (!plugin.worlds.get(world).getOwners().contains(targetUUID)) {
									worldUtil.addOwner(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Added the player '" + target + "' as an owner of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is already an owner of the world '" + world + "'.");
								}
							} else if (flag.equalsIgnoreCase("removeowner")) {
								if (plugin.worlds.get(world).getOwners().contains(targetUUID)) {
									worldUtil.removeOwner(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Removed the player '" + target + "' as an owner of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is not an owner of the world '" + world + "'.");
								}

							} else if (flag.equalsIgnoreCase("addmember")) {
								if (!plugin.worlds.get(world).getMembers().contains(targetUUID)) {
									worldUtil.addMember(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Added the player '" + target + "' as a member of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is already a member of the world '" + world + "'.");
								}
							} else if (flag.equalsIgnoreCase("removemember")) {
								if (plugin.worlds.get(world).getMembers().contains(targetUUID)) {
									worldUtil.removeMember(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Removed the player '" + target + "' as a member of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is not a member of the world '" + world + "'.");
								}
							} else if (flag.equalsIgnoreCase("addguest")) {
								if (!plugin.worlds.get(world).getGuests().contains(targetUUID)) {
									worldUtil.addGuest(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Added the player '" + target + "' as a guest of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is already a guest of the world '" + world + "'.");
								}
							} else if (flag.equalsIgnoreCase("removeguest")) {
								if (plugin.worlds.get(world).getGuests().contains(targetUUID)) {
									worldUtil.removeGuest(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Removed the player '" + target + "' as a guest of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is not a guest of the world '" + world + "'.");
								}
							} else if (flag.equalsIgnoreCase("addspectator")) {
								if (!plugin.worlds.get(world).getSpectators().contains(targetUUID)) {
									worldUtil.addSpectator(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Added the player '" + target + "' as a spectator of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is already a spectator of the world '" + world + "'.");
								}
							} else if (flag.equalsIgnoreCase("removespectator")) {
								if (plugin.worlds.get(world).getSpectators().contains(targetUUID)) {
									worldUtil.removeSpectator(world, targetUUID);
									util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Removed the player '" + target + "' as a spectator of the world '" + world + "'.");
								} else {
									util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is not a spectator of the world '" + world + "'.");
								}
							} else {
								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /world <world> setgroup|addowner/removeowner/addmember/removemember/addguest/removeguest/addspectator/removespectator");
							}
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are not an owner of the world '" + world + "'.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The world '" + world + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /world <world>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
