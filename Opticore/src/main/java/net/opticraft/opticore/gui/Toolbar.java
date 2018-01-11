package net.opticraft.opticore.gui;

import java.util.List;

public class Toolbar {
	
	private String material;

	private String name;
	
	private List<String> lore;

	public Toolbar(String material, String name, List<String> lore) {
		this.material = material;
		this.name = name;
		this.lore = lore;
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
