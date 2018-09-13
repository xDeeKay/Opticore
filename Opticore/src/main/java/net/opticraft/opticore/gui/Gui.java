package net.opticraft.opticore.gui;

import java.util.HashMap;

public class Gui {

	private String title;
	private int rows;
	
	private HashMap<String, Slot> slots = new HashMap<String, Slot>();
	
	public Gui(String title, int rows, HashMap<String, Slot> slots) {
		this.title = title;
		this.rows = rows;
		this.slots = slots;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public HashMap<String, Slot> getSlots() {
		return slots;
	}
	public void setSlots(HashMap<String, Slot> slots) {
		this.slots = slots;
	}
}
