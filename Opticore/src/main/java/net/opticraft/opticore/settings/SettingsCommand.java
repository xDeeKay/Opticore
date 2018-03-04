package net.opticraft.opticore.settings;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class SettingsCommand implements CommandExecutor {

	public Main plugin;

	public GuiUtil guiUtil;
	
	public SettingsUtil settingsUtil;

	public Config config;

	public Util util;

	public SettingsCommand(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.settingsUtil = this.plugin.settingsUtil;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("settings") || cmd.getName().equalsIgnoreCase("setting")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openSettingsGui(player);

				} else if (args.length == 1) {

					String setting = args[0];

					if (setting.equalsIgnoreCase("connect_disconnect") || 
							setting.equalsIgnoreCase("server_change") || 
							setting.equalsIgnoreCase("player_chat") || 
							setting.equalsIgnoreCase("server_announcement") || 
							setting.equalsIgnoreCase("friend_request") || 
							setting.equalsIgnoreCase("direct_message") || 
							setting.equalsIgnoreCase("teleport_request") || 
							setting.equalsIgnoreCase("spectate_request")) {
						
						settingsUtil.toggleSetting(player, setting);
						
					} else {
						util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /settings connect_disconnect/server_change/player_chat/server_announcement/friend_request/direct_message/teleport_request/spectate_request");
					}
				} else {
					util.sendStyledMessage(player, null, "RED", "/", "GOLD", "Incorrect syntax. Usage: /settings <setting>");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
