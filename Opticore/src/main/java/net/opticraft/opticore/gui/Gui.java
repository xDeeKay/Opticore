package net.opticraft.opticore.gui;

import java.util.HashMap;

public class Gui {

	private String title;
	private int rows;
	
	private HashMap<String, Toolbar> toolbars = new HashMap<String, Toolbar>();
	
	private HashMap<String, Slot> slots = new HashMap<String, Slot>();
	
	public Gui(String title, int rows, HashMap<String, Toolbar> toolbars, HashMap<String, Slot> slots) {
		this.title = title;
		this.rows = rows;
		this.toolbars = toolbars;
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
	
	public HashMap<String, Toolbar> getToolbars() {
		return toolbars;
	}
	public void setToolbars(HashMap<String, Toolbar> toolbars) {
		this.toolbars = toolbars;
	}
	
	public HashMap<String, Slot> getSlots() {
		return slots;
	}
	public void setSlots(HashMap<String, Slot> slots) {
		this.slots = slots;
	}
}
