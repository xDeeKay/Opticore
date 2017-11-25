package net.opticraft.opticore.server;

public class ServerInfo {

	private String serverName;
	private String serverShort;
	private String playerList;
	
	public ServerInfo(String serverName, String serverShort, String playerList) {
		this.serverName = serverName;
		this.serverShort = serverShort;
		this.playerList = playerList;
	}
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public String getServerShort() {
		return serverShort;
	}
	public void setServerShort(String serverShort) {
		this.serverShort = serverShort;
	}
	
	public String getPlayerList() {
		return playerList;
	}
	public void setPlayerList(String playerList) {
		this.playerList = playerList;
	}
}
