package net.opticraft.opticore.server;

import java.util.List;

public class Server {

	private String name;
	
	private List<String> players;
	
	public Server(String name, List<String> players) {
		this.name = name;
		this.players = players;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<String> players) {
		this.players = players;
	}
}
