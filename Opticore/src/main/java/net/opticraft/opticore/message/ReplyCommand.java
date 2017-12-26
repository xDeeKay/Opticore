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

public class ReplyCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public BungeecordUtil bungeecordUtil;
	
	public Util util;

	public ReplyCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reply") || cmd.getName().equalsIgnoreCase("r")) {
			if (args.length >= 1) {
				
				if (plugin.players.get(sender.getName()).getLastMessageFrom() != null) {
					
					String target = plugin.players.get(sender.getName()).getLastMessageFrom();
					String message = StringUtils.join(args, ' ', 0, args.length);
					
					if (plugin.getServer().getPlayer(target) != null) {
						
						Player targetPlayer = plugin.getServer().getPlayer(target);
						
						util.sendStyledMessage(null, sender, "LIGHT_PURPLE", "M", "WHITE", "You > " + targetPlayer.getName() + ": " + ChatColor.GRAY + message);
						util.sendStyledMessage(null, targetPlayer, "LIGHT_PURPLE", "M", "WHITE", sender.getName() + " > You: " + ChatColor.GRAY + message);

					} else {
						// Target is offline or on another server

						String server = bungeecordUtil.getPlayerServer(target);
						
						if (server != null) {
							// Target is on another server
							
							util.sendStyledMessage(null, sender, "LIGHT_PURPLE", "M", "WHITE", "You > " + target + ": " + ChatColor.GRAY + message);
							bungeecordUtil.sendMessageToPlayer(sender.getName(), target, server, message);
							
						} else {
							// Target is offline
							util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "The player '" + target + "' is offline.");
						}
					}
					
				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You have no players to reply to.");
				}

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /reply <message>");
			}
		}
		return true;
	}
}