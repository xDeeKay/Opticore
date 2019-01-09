package net.opticraft.opticore.rewards;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.opticraft.opticore.Main;

public class VoteListener implements Listener {

	public Main plugin;

	public RewardUtil rewardUtil;

	public VoteListener(Main plugin) {
		this.plugin = plugin;
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
		
		String timestampString = vote.getTimeStamp();
		long timestamp = Long.parseLong(timestampString.replace(" +0000", ""));
		
		rewardUtil.giveVoteReward(username, timestamp);
		rewardUtil.logVote(uuid, username, address, timestamp, serviceName);
	}
}
