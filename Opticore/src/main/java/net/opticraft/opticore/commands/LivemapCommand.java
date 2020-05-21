package net.opticraft.opticore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class LivemapCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public Util util;

	public LivemapCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("livemap") || cmd.getName().equalsIgnoreCase("map")) {

			if (args.length == 0) {
				guiUtil.sendListAsMessage(sender, plugin.config.getLivemap());
				
				if (sender instanceof Player) {
					
					Player player = (Player) sender;
					
					String serverName = plugin.config.getServerName().toLowerCase();
					
					Location location = player.getLocation();
					String world = location.getWorld().getName();
					int x = location.getBlockX();
					int y = location.getBlockY();
					int z = location.getBlockZ();
					
					sender.sendMessage(ChatColor.GOLD + "Your location: " + ChatColor.WHITE + "www.opticraft.net/map/" + serverName + "/?worldname=" + world + "&mapname=flat&zoom=7&x=" + x + "&y=" + y + "&z=" + z);
				}

			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /livemap");
			}
		}
		return true;
	}
}
