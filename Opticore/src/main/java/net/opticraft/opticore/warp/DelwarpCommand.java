package net.opticraft.opticore.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;

public class DelwarpCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public WarpUtil warpUtil;

	public DelwarpCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.warpUtil = this.plugin.warpUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String warp = args[0];

					if (warpUtil.warpExists(warp)) {

						warpUtil.delWarp(warp);

						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "Deleted warp '" + warp + "'.");

					} else {
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
								ChatColor.GOLD + "The warp '" + warp + "' does not exist.");
					}
				} else {
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
							ChatColor.GOLD + "Incorrect syntax. Usage: /delwarp <warp-name>");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "/" + ChatColor.WHITE + "] " + 
						ChatColor.GOLD + "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
