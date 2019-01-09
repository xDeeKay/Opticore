package net.opticraft.opticore.teleport;

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

	public ServerUtil serverUtil;

	public TeleportUtil teleportUtil;

	public Util util;

	public BungeecordUtil bungeecordUtil;

	public TpAcceptCommand(Main plugin) {
		this.plugin = plugin;
		this.serverUtil = this.plugin.serverUtil;
		this.teleportUtil = this.plugin.teleportUtil;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
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
						
					} else if (!plugin.players.get(player.getName()).getTprhereIncoming().isEmpty()) {
						targetName = plugin.players.get(player.getName()).getTprhereIncoming().iterator().next();
						
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

				if (plugin.players.get(player.getName()).getTprIncoming().contains(targetName) || plugin.players.get(player.getName()).getTprhereIncoming().contains(targetName)) {
					
					teleportUtil.teleportAccept(player.getName(), targetName);
					
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
