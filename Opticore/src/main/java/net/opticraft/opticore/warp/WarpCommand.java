package net.opticraft.opticore.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.Methods;

public class WarpCommand implements CommandExecutor {

	public Main plugin;
	
	public GuiMethods guiMethods;
	public WarpMethods warpMethods;
	
	public Methods methods;

	public WarpCommand(Main plugin) {
		this.plugin = plugin;
		this.guiMethods = this.plugin.guiMethods;
		this.warpMethods = this.plugin.warpMethods;
		this.methods = this.plugin.methods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp") || cmd.getName().equalsIgnoreCase("warps")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiMethods.openWarpsGui(player);

				} else if (args.length == 1) {

					String warp = args[0];

					if (warpMethods.warpExists(warp)) {

						if (player.hasPermission("opticore.warp." + warp.toLowerCase())) {
							warpMethods.teleportPlayerToWarp(player, warp);
							
							methods.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Teleporting to warp '" + warp + "'.");
							
						} else {
							methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "You do not have permission to access warp '" + warp + "'.");
						}
					} else {
						methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "The warp '" + warp + "' does not exist.");
					}
				} else {
					methods.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /delwarp <warp-name>");
				}
			} else {
				methods.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
