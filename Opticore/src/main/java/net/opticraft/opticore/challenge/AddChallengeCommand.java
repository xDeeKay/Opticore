package net.opticraft.opticore.challenge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;

public class AddChallengeCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;

	public ChallengeUtil challengeUtil;

	public Util util;

	public AddChallengeCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.challengeUtil = this.plugin.challengeUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("addchallenge")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				
				if (args.length == 0) {
					
					Challenge challenge = challengeUtil.randomChallenge();
					challengeUtil.createChallenge(challenge.getName(), challenge.getEnds(), challenge.isReplace(), challenge.getTask(), 
							challenge.getTarget(), challenge.getAmount(), challenge.getReward());
					util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Created random challenge '" + challenge.getName() + "'.");

				} else if (args.length == 7) {

					String challenge = args[0];
					long timestamp = System.currentTimeMillis() / 1000;
					long ends = timestamp + Long.valueOf(args[1]);
					boolean replace = Boolean.valueOf(args[2]);
					String task = args[3];
					String target = args[4];
					int amount = Integer.valueOf(args[5]);
					int reward = Integer.valueOf(args[6]);

					if (!challengeUtil.challengeExists(challenge)) {
						
						if (util.isInt(args[1])) {
							
							if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
								
								if (task.equalsIgnoreCase("kill") || task.equalsIgnoreCase("fish") || task.equalsIgnoreCase("farm") || task.equalsIgnoreCase("breed") || 
										task.equalsIgnoreCase("smelt") || task.equalsIgnoreCase("enchant") || task.equalsIgnoreCase("consume") || task.equalsIgnoreCase("tame") || 
										task.equalsIgnoreCase("craft") || task.equalsIgnoreCase("brew") || task.equalsIgnoreCase("trade") || task.equalsIgnoreCase("repair")) {
									
									if (util.isInt(args[5])) {
										
										if (util.isInt(args[6])) {
											
											challengeUtil.createChallenge(challenge, ends, replace, task, target, amount, reward);
											util.sendStyledMessage(player, null, "GREEN", "/", "GOLD", "Created challenge '" + challenge + "'.");
											
										} else {
											// reward not int
										}
									} else {
										// amount not int
									}
								} else {
									// incorrect task
								}
							} else {
								// replace not boolean
							}
						} else {
							// duration not int
						}
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "The challenge '" + challenge + "' already exists.");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /addchallenge <challenge> <duration> <replace> <task> <target> <amount> <reward>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
