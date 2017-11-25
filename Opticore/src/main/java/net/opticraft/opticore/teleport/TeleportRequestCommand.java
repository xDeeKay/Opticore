package net.opticraft.opticore.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class TeleportRequestCommand implements CommandExecutor {

	public Main plugin;

	public Methods methods;
	public BungeecordMethods bungeecordMethods;

	public TeleportMethods teleportMethods;

	public TeleportRequestCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.teleportMethods = this.plugin.teleportMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpr") || cmd.getName().equalsIgnoreCase("tprequest") || cmd.getName().equalsIgnoreCase("teleportr")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String targetName = args[0];

					if (teleportMethods.getTeleportTo(player) == null) {

						if (plugin.getServer().getPlayer(targetName) != null) {

							Player target = plugin.getServer().getPlayer(targetName);

							teleportMethods.teleportRequest(player.getName(), target.getName());
							
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Sent teleport request to player '" + target.getName() + "'.");

						} else {
							// Target is offline or on another server

							String server = bungeecordMethods.getPlayerServer(targetName);

							if (server != null) {
								bungeecordMethods.sendTeleportInfo(player.getName(), targetName, server, "tpr", "");
								
								plugin.players.get(player.getName()).setTeleportTo(targetName);
								methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Sent teleport request to player '" + targetName + "'.");

							} else {
								// Target is offline

								methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
							}
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have already sent a teleport request.");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tpr <player>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
