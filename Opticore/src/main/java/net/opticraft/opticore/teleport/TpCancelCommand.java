package net.opticraft.opticore.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TpCancelCommand implements CommandExecutor {

	public Main plugin;

	public ServerUtil serverUtil;

	public TeleportUtil teleportUtil;

	public Util util;

	public BungeecordUtil bungeecordUtil;

	public TpCancelCommand(Main plugin) {
		this.plugin = plugin;
		this.serverUtil = this.plugin.serverUtil;
		this.teleportUtil = this.plugin.teleportUtil;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpcancel") || cmd.getName().equalsIgnoreCase("tpc") || cmd.getName().equalsIgnoreCase("tpnvm")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				String targetName = null;

				if (args.length == 0) {
					if (plugin.players.get(player.getName()).getTprOutgoing() != null) {
						targetName = plugin.players.get(player.getName()).getTprOutgoing();

					} else if (plugin.players.get(player.getName()).getTprhereOutgoing() != null) {
						targetName = plugin.players.get(player.getName()).getTprhereOutgoing();

					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You have no teleport requests.");
						return true;
					}

				} else if (args.length == 1) {

					targetName = args[0];

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: '/tpc' or '/tpc <player>'");
					return true;
				}

				if ((plugin.players.get(player.getName()).getTprOutgoing() != null && plugin.players.get(player.getName()).getTprOutgoing().equals(targetName)) 
						|| (plugin.players.get(player.getName()).getTprhereOutgoing() != null && plugin.players.get(player.getName()).getTprhereOutgoing().equals(targetName))) {
					teleportUtil.teleportCancel(player.getName(), targetName);
					
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
