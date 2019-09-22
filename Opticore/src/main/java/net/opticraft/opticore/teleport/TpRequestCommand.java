package net.opticraft.opticore.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TpRequestCommand implements CommandExecutor {

	public Main plugin;

	public ServerUtil serverUtil;

	public TeleportUtil teleportUtil;

	public Util util;

	public BungeecordUtil bungeecordUtil;

	public TpRequestCommand(Main plugin) {
		this.plugin = plugin;
		this.serverUtil = this.plugin.serverUtil;
		this.teleportUtil = this.plugin.teleportUtil;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tprequest") || cmd.getName().equalsIgnoreCase("tpr")) {
			if (sender instanceof Player) {

				Player senderPlayer = (Player) sender;
				String senderName = senderPlayer.getName();

				if (args.length == 1) {

					String target = args[0];

					if (plugin.players.get(senderName).getTprOutgoing() == null && plugin.players.get(senderName).getTprhereOutgoing() == null) {

						if (plugin.getServer().getPlayer(target) != null) {

							Player targetPlayer = plugin.getServer().getPlayer(target);
							String targetName = targetPlayer.getName();

							if (plugin.players.get(targetName).getSettings().get("teleport_request").getValue() == 1) {
								teleportUtil.teleportRequest(senderName, targetName);
							}

							util.sendStyledMessage(senderPlayer, null, "GREEN", "/", "GOLD", "Teleport request sent to '" + targetName + "'.");

						} else {
							// Target is offline or on another server

							String server = serverUtil.getPlayerServerName(target);

							if (server != null) {
								bungeecordUtil.sendTeleportInfo(senderName, target, server, "tpr", "");

								plugin.players.get(senderName).setTprOutgoing(target);

								util.sendStyledMessage(senderPlayer, null, "GREEN", "/", "GOLD", "Teleport request sent to '" + target + "'.");

							} else {
								// Target is offline

								util.sendStyledMessage(senderPlayer, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
							}
						}
					} else {
						util.sendStyledMessage(senderPlayer, null, "RED", "/", "GOLD", "You already have an active teleport request. Type /tpc to cancel the request.");
					}
				} else {
					util.sendStyledMessage(senderPlayer, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /tpr <player>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
