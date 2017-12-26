package net.opticraft.opticore.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TpdenyCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public BungeecordUtil bungeecordUtil;

	public TeleportUtil teleportUtil;

	public TpdenyCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.teleportUtil = this.plugin.teleportUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpdeny") || cmd.getName().equalsIgnoreCase("tpd") || cmd.getName().equalsIgnoreCase("tpno")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				String targetName = null;

				if (args.length == 0) {
					if (!teleportUtil.getTeleportRequests(player).isEmpty()) {
						targetName = teleportUtil.getTeleportRequests(player).iterator().next();
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no teleport requests.");
						return true;
					}

				} else if (args.length == 1) {

					targetName = args[0];

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: '" + cmd.getName().toLowerCase() + "' or '/tpd <player>'.");
					return true;
				}

				if (teleportUtil.getTeleportRequests(player).contains(targetName)) {

					if (plugin.getServer().getPlayer(targetName) != null) {
						// Target is online

						Player target = plugin.getServer().getPlayer(targetName);

						teleportUtil.teleportDeny(player.getName(), target.getName());

						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Denied teleport request from player '" + target.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = bungeecordUtil.getPlayerServer(targetName);

						if (server != null) {
							bungeecordUtil.sendTeleportInfo(player.getName(), targetName, server, "tpd", "");

							teleportUtil.getTeleportRequests(player).remove(targetName);
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Denied teleport request from player '" + targetName + "'.");

						} else {
							// Target is offline
							teleportUtil.getTeleportRequests(player).remove(targetName);
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
						}
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no teleport request from player '" + targetName + "'.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
