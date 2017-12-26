package net.opticraft.opticore.server;

import java.util.List;

public class Server {

	private String serverName;
	
	private List<String> playerList;
	
	public Server(String serverName, List<String> playerList) {
		this.serverName = serverName;
		this.playerList = playerList;
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public List<String> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<String> playerList) {
		this.playerList = playerList;
	}
}
