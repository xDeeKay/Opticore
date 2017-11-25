package net.opticraft.opticore.friend;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;

public class FriendCommand implements CommandExecutor {

	public Main plugin;

	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;

	public FriendCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordMethods = this.plugin.bungeecordMethods;
		this.guiMethods = this.plugin.guiMethods;
	}

	public boolean playerHasFriend(Player player, String target) {
		return false;

	}

	public boolean friendRequestSent(Player player, String target) {
		return false;

	}

	public boolean friendRequestReceived(Player player, String target) {
		return false;

	}

	public boolean friendRequestDenied(Player player, String target) {
		return false;

	}
	
	public void addFriend(Player player, String target) {
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("friend") || cmd.getName().equalsIgnoreCase("friends") || cmd.getName().equalsIgnoreCase("f")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {			
					guiMethods.openFriendsGui(player, null);

				} else if (args.length == 2) {

					String target = args[1];

					if (args[0].equalsIgnoreCase("add")) {
						sender.sendMessage("friend add");

						if (!playerHasFriend(player, target)) {
							if (!friendRequestSent(player, target)) {
								if (!friendRequestReceived(player, target)) {
									if (!friendRequestDenied(player, target)) {
										
										addFriend(player, target);

									} else {
										player.sendMessage(ChatColor.RED + "Your friend request has already been denied by " + target + ".");
									}
								} else {
									player.sendMessage(ChatColor.RED + "You already have an incoming friend request from " + target + ".");
								}
							} else {
								player.sendMessage(ChatColor.RED + "You already have an outgoing friend request to " + target + ".");
							}
						} else {
							player.sendMessage(ChatColor.RED + target + " is already your friend.");
						}

					} else if (args[0].equalsIgnoreCase("remove")) {
						sender.sendMessage("friend remove");

					} else if (args[0].equalsIgnoreCase("accept")) {
						sender.sendMessage("friend accept");

					} else if (args[0].equalsIgnoreCase("deny")) {
						sender.sendMessage("friend deny");

					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /friends <add,remove,accept,deny>");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /friends <add,remove,accept,deny>");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be in-game to perform this command.");
			}
		}
		return true;
	}
}
