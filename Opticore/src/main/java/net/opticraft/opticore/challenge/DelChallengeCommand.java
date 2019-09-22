package net.opticraft.opticore.challenge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class DelChallengeCommand implements CommandExecutor {

	public Main plugin;

	public ChallengeUtil challengeUtil;

	public Util util;

	public DelChallengeCommand(Main plugin) {
		this.plugin = plugin;
		this.challengeUtil = this.plugin.challengeUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delchallenge")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {
					
					String challenge = args[0];
					
					if (challengeUtil.challengeExists(challenge)) {

						challengeUtil.deleteChallenge(challenge);
						util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Deleted challenge '" + challenge + "'.");
						
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The challenge '" + challenge + "' does not exist.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /delchallenge <challenge>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
