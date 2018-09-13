package net.opticraft.opticore.social;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class SocialUtil {

	public Main plugin;

	public Config config;
	public BungeecordUtil bungeecordUtil;

	public SocialUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	public boolean serverExists(String server) {

		return plugin.servers.containsKey(server);
	}
	
	public String getPlayerServer(String player) {
		
		String playerServer = null;
		
		for (String server : plugin.servers.keySet()) {
			
			if (plugin.servers.get(server).getPlayers().contains(player)) {
				
				playerServer = server;
				
			}
		}
		return playerServer;
	}

	public void updateSocial(Player player, String website, String user) {
		// TODO Auto-generated method stub
		
	}
}
