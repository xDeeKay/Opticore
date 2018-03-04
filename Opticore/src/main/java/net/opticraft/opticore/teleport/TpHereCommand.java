package net.opticraft.opticore.teleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TpHereCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public Config config;
	public BungeecordUtil bungeecordUtil;

	public TeleportUtil teleportUtil;

	public TpHereCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.teleportUtil = this.plugin.teleportUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tphere") || cmd.getName().equalsIgnoreCase("tph")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String targetName = args[0];

					if (plugin.getServer().getPlayer(targetName) != null) {
						
						Player target = plugin.getServer().getPlayer(targetName);
						
						Location location = player.getLocation();
						
						target.teleport(location);

						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported player '" + target.getName() + "' to you.");
						util.sendStyledMessage(target, null, "GREEN", "/", "GOLD", "Teleported to player '" + player.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = bungeecordUtil.getPlayerServer(targetName);
						
						if (server != null) {
							// Target is on another server
							
							// Send teleport info to other server to get the player ready
							bungeecordUtil.sendTeleportInfo(player.getName(), targetName, server, "tphere", config.getServerName().toLowerCase());
							
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported player '" + targetName + "' to you.");
							
						} else {
							// Target is offline
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
						}
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tphere <player>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
