package net.opticraft.opticore.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class PlayersCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public Util util;

	public PlayersCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("players") || cmd.getName().equalsIgnoreCase("list") || cmd.getName().equalsIgnoreCase("playerlist") || cmd.getName().equalsIgnoreCase("online")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "players", player.getName());

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /players");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
