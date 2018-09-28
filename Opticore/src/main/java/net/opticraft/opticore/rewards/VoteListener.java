package net.opticraft.opticore.rewards;

import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class VoteListener implements Listener {

	public Main plugin;

	public Util util;

	public RewardUtil rewardUtil;

	public VoteListener(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.rewardUtil = this.plugin.rewardUtil;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVotifierEvent(VotifierEvent event) {

		Vote vote = event.getVote();
		String username = vote.getUsername();
		String uuid = plugin.getServer().getOfflinePlayer(username).getUniqueId().toString();
		String address = vote.getAddress();
		String serviceName = vote.getServiceName();
		String timestamp = vote.getTimeStamp();

		timestamp = timestamp.replace(" +0000", "");

		long timestampLong = Long.parseLong(timestamp);

		// Log vote to the database
		plugin.mysql.insert("oc_votes", 
				Arrays.asList("uuid", "username", "ip", "timestamp", "service"), 
				Arrays.asList(uuid, username, address, timestampLong, serviceName));
		
		// Give player reward points
		rewardUtil.giveRewardPoints(username, plugin.config.getPointsVote());
	}
}
