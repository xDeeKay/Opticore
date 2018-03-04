package net.opticraft.opticore.staff.ban;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class BanListener implements Listener {

	public Main plugin;
	public Util util;
	public Config config;
	public BanUtil banUtil;

	long banLength;
	long unbanDateLong;

	public BanListener(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.config = this.plugin.config;
		this.banUtil = this.plugin.banUtil;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player target = event.getPlayer();
		
		if (banUtil.getActiveBan(target.getName()) != null) {
			
			String senderName = banUtil.getActiveBan(target.getName()).getSenderName();
			long banTimestamp = banUtil.getActiveBan(target.getName()).getBanTimestamp();
			long banLength = banUtil.getActiveBan(target.getName()).getBanLength();
			
			long unbanTimestamp = banTimestamp + banLength;
			String unbanDate = util.timestampDateFormat(unbanTimestamp) + " (UTC)";
			
			if (banLength == 0) {
				unbanDate = "forever";
			}
			
			String banReason = banUtil.getActiveBan(target.getName()).getBanReason();
			
			event.setKickMessage("You have been banned from the network by " + senderName + " until " + unbanDate + "\nReason: " + banReason + "\nAppeal at www.opticraft.net");
			event.setResult(Result.KICK_BANNED);
		}
	}
}
