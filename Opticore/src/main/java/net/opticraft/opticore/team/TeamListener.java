package net.opticraft.opticore.team;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.opticraft.opticore.Main;

public class TeamListener implements Listener {

	public Main plugin;
	
	public TeamUtil teamUtil;

	public TeamListener(Main plugin) {
		this.plugin = plugin;
		this.teamUtil = this.plugin.teamUtil;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {

		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		for (String team : plugin.teams.keySet()) {
			if (plugin.teams.get(team).getMembers().contains(uuid)) {
				event.setRespawnLocation(teamUtil.getTeamSpawn(team));
				teamUtil.setPlayerInventory(team, player);
				teamUtil.setPlayerGamemode(team, player);
			}
		}
	}
}
