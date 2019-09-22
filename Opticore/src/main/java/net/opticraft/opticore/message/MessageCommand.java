package net.opticraft.opticore.message;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class MessageCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordUtil bungeecordUtil;

	public ServerUtil serverUtil;

	public Util util;

	public MessageCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.serverUtil = this.plugin.serverUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("message") || cmd.getName().equalsIgnoreCase("msg")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length >= 2) {

					String target = args[0];
					String message = StringUtils.join(args, ' ', 1, args.length);

					if (plugin.getServer().getPlayer(target) != null) {

						Player targetPlayer = plugin.getServer().getPlayer(target);

						util.sendStyledMessage(player, null, util.parseColor(player.getName()), "MSG", "WHITE", "You > " + targetPlayer.getName() + ": " + ChatColor.valueOf(util.parseColor(player.getName())) + message);
						plugin.players.get(player.getName()).setLastMessageFrom(targetPlayer.getName());

						if (plugin.players.get(targetPlayer.getName()).getSettings().get("direct_message").getValue() == 1) {
							util.sendStyledMessage(null, targetPlayer, util.parseColor(targetPlayer.getName()), "MSG", "WHITE", sender.getName() + " > You: " + ChatColor.valueOf(util.parseColor(targetPlayer.getName())) + message);
							plugin.players.get(targetPlayer.getName()).setLastMessageFrom(player.getName());
						}

					} else {

						String server = serverUtil.getPlayerServerName(target);

						if (server != null) {
							// Target is on another server

							bungeecordUtil.sendMessageToPlayer(player.getName(), target, server, message);

							util.sendStyledMessage(player, null, util.parseColor(player.getName()), "MSG", "WHITE", "You > " + target + ": " + ChatColor.valueOf(util.parseColor(player.getName())) + message);

							plugin.players.get(sender.getName()).setLastMessageFrom(target);

						} else {
							// Target is offline
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
						}
					}

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /message <player> <message>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
