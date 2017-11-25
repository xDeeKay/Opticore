package net.opticraft.opticore.teleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class TeleportHereCommand implements CommandExecutor {

	public Main plugin;

	public Methods methods;
	public Config config;
	public BungeecordMethods bungeecordMethods;

	public TeleportMethods teleportMethods;

	public TeleportHereCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.config = this.plugin.config;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.teleportMethods = this.plugin.teleportMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tph") || cmd.getName().equalsIgnoreCase("tphere") || cmd.getName().equalsIgnoreCase("teleporth")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String targetName = args[0];

					if (plugin.getServer().getPlayer(targetName) != null) {
						
						Player target = plugin.getServer().getPlayer(targetName);
						
						Location location = player.getLocation();
						
						target.teleport(location);

						methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported player '" + target.getName() + "' to you.");
						methods.sendStyledMessage(target, null, "GREEN", "/", "GOLD", "Teleported to player '" + player.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = bungeecordMethods.getPlayerServer(targetName);
						
						if (server != null) {
							// Target is on another server
							
							// Send teleport info to other server to get the player ready
							bungeecordMethods.sendTeleportInfo(player.getName(), targetName, server, "tphere", config.getServerName().toLowerCase());
							
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported player '" + targetName + "' to you.");
							
						} else {
							// Target is offline
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
						}
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tphere <player>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
