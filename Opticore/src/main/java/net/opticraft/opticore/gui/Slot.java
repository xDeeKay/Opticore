package net.opticraft.opticore.gui;

import java.util.List;

public class Slot {
	
	private int position;
	
	private String material;

	private String name;
	
	private List<String> lore;
	
	private List<String> commands;

	public Slot(int position, String material, String name, List<String> lore, List<String> commands) {
		this.position = position;
		this.material = material;
		this.name = name;
		this.lore = lore;
		this.commands = commands;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getLore() {
		return lore;
	}
	public void setLore(List<String> lore) {
		this.lore = lore;
	}
	
	public List<String> getCommands() {
		return commands;
	}
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
}
