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

public class TpAcceptCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	
	public ServerUtil serverUtil;
	
	public BungeecordUtil bungeecordUtil;

	public TeleportUtil teleportUtil;

	public TpAcceptCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.serverUtil = this.plugin.serverUtil;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.teleportUtil = this.plugin.teleportUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpaccept") || cmd.getName().equalsIgnoreCase("tpa") || cmd.getName().equalsIgnoreCase("tpyes")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				String targetName = null;

				if (args.length == 0) {
					if (!plugin.players.get(player.getName()).getTprIncoming().isEmpty()) {
						targetName = plugin.players.get(player.getName()).getTprIncoming().iterator().next();
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no teleport requests.");
						return true;
					}

				} else if (args.length == 1) {

					targetName = args[0];

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: '/tpa' or '/tpa <player>'");
					return true;
				}

				if (plugin.players.get(player.getName()).getTprIncoming().contains(targetName)) {

					if (plugin.getServer().getPlayer(targetName) != null) {
						// Target is online

						Player target = plugin.getServer().getPlayer(targetName);

						teleportUtil.teleportAccept(player.getName(), target.getName());

						Location location = player.getLocation();

						target.teleport(location);

						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Accepted teleport request from player '" + target.getName() + "'.");

					} else {
						// Target is offline or on another server

						String server = serverUtil.getPlayerServer(targetName);

						if (server != null) {
							bungeecordUtil.sendTeleportInfo(player.getName(), targetName, server, "tpa", "");

							plugin.players.get(player.getName()).getTprIncoming().remove(targetName);
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Accepted teleport request from player '" + targetName + "'.");

						} else {
							// Target is offline
							plugin.players.get(player.getName()).getTprIncoming().remove(targetName);
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
