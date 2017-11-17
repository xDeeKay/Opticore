package net.opticraft.opticore.world;

public class WorldInfo {
	
	private String type;
	
	private String item;
	
	private String permission;
	
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	
	public WorldInfo(String type, String item, String permission, String world, double x, double y, double z, double yaw, double pitch) {
		this.type = type;
		this.item = item;
		this.permission = permission;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public String getType() {
		return type;
	}
	
	public String getItem() {
		return item;
	}
	
	public String getPermission() {
		return permission;
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
}
