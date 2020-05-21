package net.opticraft.opticore.world;

import java.util.List;

public class World {
	
	private String type;
	
	private String material;
	
	private String permission;
	
	private boolean forced;
	
	private double spread;
	
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	
	private String group;
	
	private List<String> owners;
	private List<String> members;
	private List<String> guests;
	private List<String> spectators;
	
	public World(String type, String material, String permission, boolean forced, double spread, String world, double x, double y, double z, double yaw, double pitch, String group, List<String> owners, List<String> members, List<String> guests, List<String> spectators) {
		this.type = type;
		this.material = material;
		this.permission = permission;
		this.forced = forced;
		this.spread = spread;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.group = group;
		this.owners = owners;
		this.members = members;
		this.guests = guests;
		this.spectators = spectators;
	}
	
	public String getType() {
		return type;
	}
	
	public String getMaterial() {
		return material;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public boolean getForced() {
		return forced;
	}
	
	public double getSpread() {
		return spread;
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
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public List<String> getOwners() {
		return this.owners;
	}
	
	public void setOwners(List<String> owners) {
		this.owners = owners;
	}
	
	public List<String> getMembers() {
		return this.members;
	}
	
	public void setMembers(List<String> members) {
		this.members = members;
	}
	
	public List<String> getGuests() {
		return this.guests;
	}
	
	public void setGuests(List<String> guests) {
		this.guests = guests;
	}
	
	public List<String> getSpectators() {
		return this.spectators;
	}
	
	public void setSpectators(List<String> spectators) {
		this.spectators = spectators;
	}
}
