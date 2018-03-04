package net.opticraft.opticore.server;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class HubCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	
	public Util util;
	
	public BungeecordUtil bungeecordUtil;

	public HubCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("hub") || cmd.getName().equalsIgnoreCase("h")) {
			if (sender instanceof Player) {
				
				Player player = (Player) sender;

				if (args.length == 0) {

					if (!config.getServerName().equalsIgnoreCase("hub")) {
						
						if (player.hasPermission("opticore.server.hub")) {
							
							bungeecordUtil.sendPlayerToServer(player, "hub");
							
						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the Hub server.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You are already connected to the Hub server.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /hub");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
