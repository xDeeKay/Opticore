package net.opticraft.opticore.util.wither;

import java.util.List;

import org.bukkit.Location;

public class Wither {
	
	private Location entryPortalBlock1;
	private Location entryPortalBlock2;
	private Location entryPortalSpawn;
	
	private Location exitPortalBlock1;
	private Location exitPortalBlock2;
	private Location exitPortalSpawn;
	
	private List<String> occupants;
	
	private boolean witherAlive;
	
	private boolean starObtained;
	
	public Wither(Location entryPortalBlock1, Location entryPortalBlock2, Location entryPortalSpawn, Location exitPortalBlock1, Location exitPortalBlock2, Location exitPortalSpawn) {
		this.entryPortalBlock1 = entryPortalBlock1;
		this.entryPortalBlock2 = entryPortalBlock2;
		this.entryPortalSpawn = entryPortalSpawn;
		this.exitPortalBlock1 = exitPortalBlock1;
		this.exitPortalBlock2 = exitPortalBlock2;
		this.exitPortalSpawn = exitPortalSpawn;
	}
	
	public Location getEntryPortalBlock1() {
		return entryPortalBlock1;
	}
	public void setEntryPortalBlock1(Location entryPortalBlock1) {
		this.entryPortalBlock1 = entryPortalBlock1;
	}
	
	public Location getEntryPortalBlock2() {
		return entryPortalBlock2;
	}
	public void setEntryPortalBlock2(Location entryPortalBlock2) {
		this.entryPortalBlock2 = entryPortalBlock2;
	}
	
	public Location getEntryPortalSpawn() {
		return entryPortalSpawn;
	}
	public void setEntryPortalSpawn(Location entryPortalSpawn) {
		this.entryPortalSpawn = entryPortalSpawn;
	}
	
	public Location getExitPortalBlock1() {
		return exitPortalBlock1;
	}
	public void setExitPortalBlock1(Location exitPortalBlock1) {
		this.exitPortalBlock1 = exitPortalBlock1;
	}
	
	public Location getExitPortalBlock2() {
		return exitPortalBlock2;
	}
	public void setExitPortalBlock2(Location exitPortalBlock2) {
		this.exitPortalBlock2 = exitPortalBlock2;
	}
	
	public Location getExitPortalSpawn() {
		return exitPortalSpawn;
	}
	public void setExitPortalSpawn(Location exitPortalSpawn) {
		this.exitPortalSpawn = exitPortalSpawn;
	}
	
	public List<String> getOccupants() {
		return occupants;
	}
	public void setOccupants(List<String> occupants) {
		this.occupants = occupants;
	}
	public void addOccupant(String player) {
		this.occupants.add(player);
	}
	
	public boolean getWitherAlive() {
		return witherAlive;
	}
	public void setWitherAlive(boolean witherAlive) {
		this.witherAlive = witherAlive;
	}
	
	public boolean getStarObtained() {
		return starObtained;
	}
	public void setStarObtained(boolean starObtained) {
		this.starObtained = starObtained;
	}
}
