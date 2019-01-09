package net.opticraft.opticore.ignore;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class IgnoredCommand implements CommandExecutor {

	public Main plugin;
	
	public IgnoreUtil ignoreUtil;

	public Util util;

	public IgnoredCommand(Main plugin) {
		this.plugin = plugin;
		this.ignoreUtil = this.plugin.ignoreUtil;
		this.util = this.plugin.util;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ignored")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					
					for (String igoreUUID : plugin.players.get(player.getName()).getIgnored().keySet()) {
						
						String name = plugin.getServer().getOfflinePlayer(UUID.fromString(igoreUUID)).getName();
						
						long timestamp = plugin.players.get(player.getName()).getIgnored().get(igoreUUID).getIgnoreTimestamp();
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						String date = sdf.format(timestamp);
						
						player.sendMessage(ChatColor.GOLD + name + " - Ignored on " + date);
					}

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /ignored");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
