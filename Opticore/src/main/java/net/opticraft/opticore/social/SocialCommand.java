package net.opticraft.opticore.social;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class SocialCommand implements CommandExecutor {

	public Main plugin;

	public Config config;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public GuiUtil guiUtil;
	
	public SocialUtil socialUtil;

	public SocialCommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.socialUtil = this.plugin.socialUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("social")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 2) {

					String website = args[0];
					String url = args[1];

					if (website.equalsIgnoreCase("youtube")) {

						if (url.contains("https://www.youtube.com/channel/")) {
							
							String[] urlParts = url.split("https://www.youtube.com/channel/");
							String user = urlParts[1];
							
							socialUtil.updateSocial(player, website, user);
						}
					} else if (website.equalsIgnoreCase("youtube")) {
						
					}

				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /social youtube|twitch|twitter|instagram|discord <link>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
