package net.opticraft.opticore.rewards;

import java.util.List;

public class Reward {
	
	public int cost;
	
	private List<String> items;
	
	private List<String> commands;
	
	private List<String> disguises;
	
	public Reward(int cost, List<String> items, List<String> commands, List<String> disguises) {
		this.cost = cost;
		this.items = items;
		this.commands = commands;
		this.disguises = disguises;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public List<String> getItems() {
		return this.items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}
	
	public List<String> getCommands() {
		return this.commands;
	}
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	
	public List<String> getDisguises() {
		return this.disguises;
	}
	public void setDisguises(List<String> disguises) {
		this.disguises = disguises;
	}
}
