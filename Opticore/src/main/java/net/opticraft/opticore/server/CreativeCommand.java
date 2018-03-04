package net.opticraft.opticore.server;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class CreativeCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public Util util;
	
	public BungeecordUtil bungeecordUtil;

	public CreativeCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("creative") || cmd.getName().equalsIgnoreCase("c")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;

				if (args.length == 0) {

					if (!config.getServerName().equalsIgnoreCase("creative")) {
						
						if (player.hasPermission("opticore.server.creative")) {
							
							bungeecordUtil.sendPlayerToServer(player, "creative");
							
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the Creative server.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are already connected to the Creative server.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /creative");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
