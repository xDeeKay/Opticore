package net.opticraft.opticore.teleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TpCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	
	public ServerUtil serverUtil;
	
	public BungeecordUtil bungeecordUtil;

	public TeleportUtil teleportUtil;

	public TpCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.serverUtil = this.plugin.serverUtil;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.teleportUtil = this.plugin.teleportUtil;
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

						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleported to player '" + targetPlayer.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = serverUtil.getPlayerServer(target);
						
						if (server != null) {
							// Target is on another server
							
							// Send teleport info to other server to get the player ready
							bungeecordUtil.sendTeleportInfo(player.getName(), target, server, "tp", "");
							
							// Send player to other server
							bungeecordUtil.sendPlayerToServer(player, server);
							
						} else {
							// Target is offline
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
						}
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tp <player>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
