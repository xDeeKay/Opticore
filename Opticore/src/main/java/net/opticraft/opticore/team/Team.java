package net.opticraft.opticore.team;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Team {
	
	private List<String> members;
	
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	
	private ItemStack[] inventory;
	
	private String gamemode;
	
	public Team(List<String> members, String world, double x, double y, double z, double yaw, double pitch, ItemStack[] inventory, String gamemode) {
		this.members = members;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.inventory = inventory;
		this.gamemode = gamemode;
	}
	
	public List<String> getMembers() {
		return this.members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getYaw() {
		return yaw;
	}
	public void setYaw(double yaw) {
		this.yaw = yaw;
	}
	
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public ItemStack[] getInventory() {
		return this.inventory;
	}
	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}
	
	public String getGamemode() {
		return this.gamemode;
	}
	public void setGamemode(String gamemode) {
		this.gamemode = gamemode;
	}
}
