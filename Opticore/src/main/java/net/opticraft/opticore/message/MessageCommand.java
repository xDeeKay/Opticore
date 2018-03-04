package net.opticraft.opticore.message;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class MessageCommand implements CommandExecutor {

	public Main plugin;

	public Config config;

	public BungeecordUtil bungeecordUtil;

	public Util util;

	public MessageCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
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

						util.sendStyledMessage(player, null, "LIGHT_PURPLE", "M", "WHITE", "You > " + targetPlayer.getName() + ": " + ChatColor.GRAY + message);
						util.sendStyledMessage(null, targetPlayer, "LIGHT_PURPLE", "M", "WHITE", sender.getName() + " > You: " + ChatColor.GRAY + message);

						plugin.players.get(player.getName()).setLastMessageFrom(targetPlayer.getName());
						plugin.players.get(targetPlayer.getName()).setLastMessageFrom(player.getName());

					} else {

						String server = bungeecordUtil.getPlayerServer(target);

						if (server != null) {
							// Target is on another server

							bungeecordUtil.sendMessageToPlayer(player.getName(), target, server, message);
							
							util.sendStyledMessage(player, null, "LIGHT_PURPLE", "M", "WHITE", "You > " + target + ": " + ChatColor.GRAY + message);

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
