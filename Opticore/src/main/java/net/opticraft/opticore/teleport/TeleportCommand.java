package net.opticraft.opticore.teleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class TeleportCommand implements CommandExecutor {

	public Main plugin;

	public Methods methods;
	public BungeecordMethods bungeecordMethods;

	public TeleportMethods teleportMethods;

	public TeleportCommand(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.teleportMethods = this.plugin.teleportMethods;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tp") || cmd.getName().equalsIgnoreCase("teleport")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String target = args[0];

					if (plugin.getServer().getPlayer(target) != null) {
						
						Player targetPlayer = plugin.getServer().getPlayer(target);
						
						Location location = targetPlayer.getLocation();
						
						player.teleport(location);

						methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported to player '" + targetPlayer.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = bungeecordMethods.getPlayerServer(target);
						
						if (server != null) {
							// Target is on another server
							
							// Send teleport info to other server to get the player ready
							bungeecordMethods.sendTeleportInfo(player.getName(), target, server, "tp", "");
							
							// Send player to other server
							bungeecordMethods.sendPlayerToServer(player, server);
							
						} else {
							// Target is offline
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
						}
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tp <player>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
