package net.opticraft.opticore.gui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class GuiCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;

	public GuiCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gui") || cmd.getName().equalsIgnoreCase("menu") || cmd.getName().equalsIgnoreCase("home") || cmd.getName().equalsIgnoreCase("opticraft")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {

					guiMethods.openHomeGui(player);

				} else if (args.length == 1) {

					if (args[0].equalsIgnoreCase("servers") || args[0].equalsIgnoreCase("server")) {
						guiMethods.openServersGui(player);

					} else if (args[0].equalsIgnoreCase("friends") || args[0].equalsIgnoreCase("friend")) {
						guiMethods.openFriendsGui(player, null);

					} else if (args[0].equalsIgnoreCase("settings") || args[0].equalsIgnoreCase("setting")) {
						guiMethods.openSettingsGui(player);

					} else if (args[0].equalsIgnoreCase("staff")) {
						if (player.hasPermission("opticore.staff")) {
							guiMethods.openStaffGui(player);
						} else {
							sender.sendMessage(ChatColor.RED + "You lack the required permission to perform this command.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /" + cmd.getName() + " servers/friends/settings/staff");
					}

				} else if (args.length == 2) {
					
					String target = args[1].toString();
					
					if (args[0].equalsIgnoreCase("players") || args[0].equalsIgnoreCase("player")) {
						guiMethods.openPlayerGui(player, target);
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /" + cmd.getName() + " player <username>");
					}

				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /" + cmd.getName() + " servers/friends/settings/staff/player");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be in-game to perform this command.");
			}
		}
		return true;
	}
}
