package net.opticraft.opticore.server;

import java.util.ArrayList;
import java.util.List;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class ServerUtil {

	public Main plugin;

	public BungeecordUtil bungeecordUtil;

	public ServerUtil(Main plugin) {
		this.plugin = plugin;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("servers")) {

			for (String server : plugin.getConfig().getStringList("servers")) {

				List<String> players = new ArrayList<String>();

				plugin.servers.put(server, new Server(server, players));
			}
		}
	}

	public boolean serverExists(String server) {

		return plugin.servers.containsKey(server);
	}

	public String getPlayerServerName(String playerName) {

		String playerServerName = null;

		for (Server server : plugin.servers.values()) {

			for (String serverPlayer : server.getPlayers()) {

				if (playerName.toLowerCase().equalsIgnoreCase(serverPlayer)) {

					playerServerName = server.getName();
				}
			}
		}

		return playerServerName;
	}
}
