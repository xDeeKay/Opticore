package net.opticraft.opticore.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TpRequestHereCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public BungeecordUtil bungeecordUtil;

	public TeleportUtil teleportUtil;

	public TpRequestHereCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.teleportUtil = this.plugin.teleportUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tprequesthere") || cmd.getName().equalsIgnoreCase("tprhere")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String targetName = args[0];

					if (plugin.players.get(player.getName()).getTprOutgoing() == null) {

						if (plugin.getServer().getPlayer(targetName) != null) {

							Player target = plugin.getServer().getPlayer(targetName);

							teleportUtil.teleportRequest(player.getName(), target.getName());
							
							util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Sent teleport request to player '" + target.getName() + "'.");

						} else {
							// Target is offline or on another server

							String server = bungeecordUtil.getPlayerServer(targetName);

							if (server != null) {
								bungeecordUtil.sendTeleportInfo(player.getName(), targetName, server, "tpr", "");
								
								plugin.players.get(player.getName()).setTprOutgoing(targetName);
								
								util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Sent teleport request to player '" + targetName + "'.");

							} else {
								// Target is offline

								util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + targetName + "' is offline.");
							}
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have already sent a teleport request.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tprhere <player>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}