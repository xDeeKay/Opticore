package net.opticraft.opticore.gui;

import java.util.List;

public class Slot {
	
	private int position; //21
	
	private String material; //bookshelf:0

	private String name; //&fServers
	
	private List<String> lore; //- "&6Select a server"

	public Slot(int position, String material, String name, List<String> lore) {
		this.position = position;
		this.material = material;
		this.name = name;
		this.lore = lore;
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
}
