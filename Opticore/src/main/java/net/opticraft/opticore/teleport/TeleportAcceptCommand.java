package net.opticraft.opticore.teleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class TeleportAcceptCommand implements CommandExecutor {

	public Main plugin;

	public Methods methods;
	public BungeecordMethods bungeecordMethods;

	public TeleportMethods teleportMethods;

	public TeleportAcceptCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.teleportMethods = this.plugin.teleportMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpa") || cmd.getName().equalsIgnoreCase("tpaccept") || cmd.getName().equalsIgnoreCase("teleporta")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				String targetName = null;

				if (args.length == 0) {
					if (!teleportMethods.getTeleportRequests(player).isEmpty()) {
						targetName = teleportMethods.getTeleportRequests(player).iterator().next();
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no teleport requests.");
						return true;
					}

				} else if (args.length == 1) {

					targetName = args[0];

				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: '/tpa' or '/tpa <player>.");
					return true;
				}

				if (teleportMethods.getTeleportRequests(player).contains(targetName)) {

					if (plugin.getServer().getPlayer(targetName) != null) {
						// Target is online

						Player target = plugin.getServer().getPlayer(targetName);

						teleportMethods.teleportAccept(player.getName(), target.getName());

						Location location = player.getLocation();

						target.teleport(location);

						methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Accepted teleport request from player '" + target.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = bungeecordMethods.getPlayerServer(targetName);

						if (server != null) {
							bungeecordMethods.sendTeleportInfo(player.getName(), targetName, server, "tpa", "");

							teleportMethods.getTeleportRequests(player).remove(targetName);
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Accepted teleport request from player '" + targetName + "'.");

						} else {
							// Target is offline
							teleportMethods.getTeleportRequests(player).remove(targetName);
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
						}
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no teleport request from player '" + targetName + "'.");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
