package net.opticraft.opticore.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class TeleportUtil {

	public Main plugin;

	public Config config;

	public ServerUtil serverUtil;

	public Util util;

	public BungeecordUtil bungeecordUtil;

	public BukkitTask task;

	public TeleportUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.serverUtil = this.plugin.serverUtil;
		this.util = this.plugin.util;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	public void teleportRequest(String sender, String target) {

		if (plugin.getServer().getPlayer(sender) != null) {
			// sender is online

			// sender player
			Player senderPlayer = plugin.getServer().getPlayer(sender);

			// set sender tpr outgoing to target
			plugin.players.get(senderPlayer.getName()).setTprOutgoing(target);
		}

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// target player
			Player targetPlayer = plugin.getServer().getPlayer(target);

			// add sender to target tpr incoming
			plugin.players.get(targetPlayer.getName()).getTprIncoming().add(sender);

			// send tpr message to target
			util.sendStyledMessage(targetPlayer, null, "GREEN", "/", "GOLD", "Received teleport request from player '" + sender + "'.");

			// send tpr command usage message to target
			String usage;
			if (plugin.players.get(targetPlayer.getName()).getTprIncoming().size() > 1) {
				usage = "Type '/tpa " + sender + "' to accept or '/tpd " + sender + "' to deny.";
			} else {
				usage = "Type '/tpa' to accept or '/tpd' to deny.";
			}
			util.sendStyledMessage(targetPlayer, null, "GREEN", "/", "GOLD", usage);

			// start tpr timeout timer
			if (config.getTeleportRequestTimeout() > 0) {
				this.task = new BukkitRunnable() {
					public void run() {
						teleportDeny(targetPlayer.getName(), sender);
					}
				}.runTaskLater(plugin, config.getTeleportRequestTimeout() * 20);
			}
		}
	}

	public void teleportRequestHere(String sender, String target) {

		if (plugin.getServer().getPlayer(sender) != null) {
			// sender is online

			// sender player
			Player senderPlayer = plugin.getServer().getPlayer(sender);

			// set sender tprhere outgoing to target
			plugin.players.get(senderPlayer.getName()).setTprhereOutgoing(target);
		}

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// target player
			Player targetPlayer = plugin.getServer().getPlayer(target);

			// add sender to target tprhere incoming
			plugin.players.get(targetPlayer.getName()).getTprhereIncoming().add(sender);

			// send tprhere message to target
			util.sendStyledMessage(targetPlayer, null, "GREEN", "/", "GOLD", "Received teleport here request from player '" + sender + "'.");

			// send tprhere command usage message to target
			String usage;
			if (plugin.players.get(targetPlayer.getName()).getTprhereIncoming().size() > 1) {
				usage = "Type '/tpa " + sender + "' to accept or '/tpd " + sender + "' to deny.";
			} else {
				usage = "Type '/tpa' to accept or '/tpd' to deny.";
			}
			util.sendStyledMessage(targetPlayer, null, "GREEN", "/", "GOLD", usage);

			// start tprhere timeout timer
			if (config.getTeleportRequestTimeout() > 0) {
				this.task = new BukkitRunnable() {
					public void run() {
						teleportDeny(targetPlayer.getName(), sender);
					}
				}.runTaskLater(plugin, config.getTeleportRequestTimeout() * 20);
			}
		}
	}

	public void teleportAccept(String sender, String target) {

		task.cancel();

		Player toTeleport = null;
		Location location = null;

		if (plugin.getServer().getPlayer(sender) != null) {
			// sender is online

			// sender player
			Player senderPlayer = plugin.getServer().getPlayer(sender);

			// send tpa message to sender
			util.sendStyledMessage(senderPlayer, null, "GREEN", "/", "GOLD", "Accepted teleport request from '" + target + "'.");

			// if sender is accepting a tpr from target
			// remove target from sender tpr incoming
			// set location to sender location
			// target > sender
			if (plugin.players.get(senderPlayer.getName()).getTprIncoming().contains(target)) {
				plugin.players.get(senderPlayer.getName()).getTprIncoming().remove(target);
				location = senderPlayer.getLocation();
			}

			// if sender is accepting a tprhere from target
			// remove target from sender tprhere incoming
			// set toTeleport as sender
			// sender > target
			if (plugin.players.get(senderPlayer.getName()).getTprhereIncoming().contains(target)) {
				plugin.players.get(senderPlayer.getName()).getTprhereIncoming().remove(target);
				toTeleport = senderPlayer;
			}
		}

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// target player
			Player targetPlayer = plugin.getServer().getPlayer(target);

			// send tpa message to target
			util.sendStyledMessage(targetPlayer, null, "GREEN", "/", "GOLD", "Teleport request accepted by '" + sender + "'.");

			// if target tpr is being accepting by sender
			// remove sender from target tpr outgoing
			// set toTeleport as target
			// target > sender
			if (plugin.players.get(targetPlayer.getName()).getTprOutgoing() != null) {
				plugin.players.get(targetPlayer.getName()).setTprOutgoing(null);
				toTeleport = targetPlayer;
			}

			// if target tprhere is being accepted by sender
			// remove sender from target tprhere outgoing
			// set location to target location
			// sender > target
			if (plugin.players.get(targetPlayer.getName()).getTprhereOutgoing() != null) {
				plugin.players.get(targetPlayer.getName()).setTprhereOutgoing(null);
				location = targetPlayer.getLocation();
			}

			toTeleport.teleport(location);
		}
	}

	public void teleportDeny(String sender, String target) {

		task.cancel();

		if (plugin.getServer().getPlayer(sender) != null) {
			// sender is online

			// sender player
			Player senderPlayer = plugin.getServer().getPlayer(sender);

			// send tpd message to sender
			util.sendStyledMessage(senderPlayer, null, "GREEN", "/", "GOLD", "Denied teleport request from '" + target + "'.");

			if (plugin.players.get(senderPlayer.getName()).getTprIncoming().contains(target)) {
				// remove target from sender tpr incoming
				plugin.players.get(senderPlayer.getName()).getTprIncoming().remove(target);
			}

			if (plugin.players.get(senderPlayer.getName()).getTprhereIncoming().contains(target)) {
				// remove target from sender tprhere incoming
				plugin.players.get(senderPlayer.getName()).getTprhereIncoming().remove(target);
			}
		}

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// target player
			Player targetPlayer = plugin.getServer().getPlayer(target);

			// send tpd message to target
			util.sendStyledMessage(targetPlayer, null, "RED", "/", "GOLD", "Teleport request denied by '" + sender + "'.");

			// remove sender from target tpr outgoing
			if (plugin.players.get(targetPlayer.getName()).getTprOutgoing() != null) {
				plugin.players.get(targetPlayer.getName()).setTprOutgoing(null);
			}

			// remove sender from target tprhere outgoing
			if (plugin.players.get(targetPlayer.getName()).getTprhereOutgoing() != null) {
				plugin.players.get(targetPlayer.getName()).setTprhereOutgoing(null);
			}
		}
	}

	public void teleportCancel(String sender, String target) {

		task.cancel();

		if (plugin.getServer().getPlayer(sender) != null) {
			// sender is online

			// sender player
			Player senderPlayer = plugin.getServer().getPlayer(sender);

			// send tpc message to sender
			util.sendStyledMessage(senderPlayer, null, "GREEN", "/", "GOLD", "Cancelled teleport request to '" + target + "'.");

			// remove target from sender tpr outgoing
			if (plugin.players.get(senderPlayer.getName()).getTprOutgoing() != null) {
				plugin.players.get(senderPlayer.getName()).setTprOutgoing(null);
			}

			// remove target from sender tprhere outgoing
			if (plugin.players.get(senderPlayer.getName()).getTprhereOutgoing() != null) {
				plugin.players.get(senderPlayer.getName()).setTprhereOutgoing(null);
			}
		}

		if (plugin.getServer().getPlayer(target) != null) {
			// target is online

			// target player
			Player targetPlayer = plugin.getServer().getPlayer(target);

			// send tpc message to target
			util.sendStyledMessage(targetPlayer, null, "RED", "/", "GOLD", "Teleport request cancelled by '" + sender + "'.");

			// remove sender from target tpr incoming
			if (plugin.players.get(targetPlayer.getName()).getTprIncoming().contains(sender)) {
				plugin.players.get(targetPlayer.getName()).getTprIncoming().remove(sender);
			}

			// remove sender from target tprhere incoming
			if (plugin.players.get(targetPlayer.getName()).getTprhereIncoming().contains(sender)) {
				plugin.players.get(targetPlayer.getName()).getTprhereIncoming().remove(sender);
			}
		}
	}
}
