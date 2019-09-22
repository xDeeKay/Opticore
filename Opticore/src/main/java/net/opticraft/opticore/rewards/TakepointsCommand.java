package net.opticraft.opticore.rewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class TakepointsCommand implements CommandExecutor {

	public Main plugin;

	public RewardUtil rewardUtil;

	public Util util;

	public TakepointsCommand(Main plugin) {
		this.plugin = plugin;
		this.rewardUtil = this.plugin.rewardUtil;
		this.util = this.plugin.util;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("takepoints")) {

			if (args.length == 2) {

				String target = args[0];

				if (util.isInt(args[1])) {

					int amount = Integer.parseInt(args[1]);

					rewardUtil.takeRewardPoint(target, amount);

					util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Took " + amount + " reward points from player '" + target + "'.");

				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /takepoints <player> <amount>");
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must specify a valid number for <amount>.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /takepoints <player> <amount>");
			}
		}
		return true;
	}
}
