package net.opticraft.opticore.server;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class ServerCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public GuiUtil guiUtil;

	public ServerUtil serverUtil;

	public ServerCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.serverUtil = this.plugin.serverUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("server") || cmd.getName().equalsIgnoreCase("servers")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "servers", null);

				} else if (args.length == 1) {

					String server = args[0];
					
					Set<String> servers = plugin.servers.keySet();
					String serverList = StringUtils.join(servers, ", ");

					if (serverUtil.serverExists(server)) {
						
						if (player.hasPermission("opticore.server." + plugin.servers.get(server).getName())) {

							bungeecordUtil.sendPlayerToServer(player, server);

						} else {
							util.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access the " + server + " server.");
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Unknown server. Availabe servers: " + serverList);
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /server <server>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
