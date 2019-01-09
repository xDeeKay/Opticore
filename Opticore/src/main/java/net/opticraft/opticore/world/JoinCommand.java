package net.opticraft.opticore.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class JoinCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public WorldUtil worldUtil;

	public Util util;

	public JoinCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.worldUtil = this.plugin.worldUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("j") || cmd.getName().equalsIgnoreCase("join")) {
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
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /join <world>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
