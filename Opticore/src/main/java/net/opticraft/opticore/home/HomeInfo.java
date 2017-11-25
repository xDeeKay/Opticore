package net.opticraft.opticore.home;

public class HomeInfo {

	private String item;
	
	private boolean locked;

	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;

	public HomeInfo(String item, boolean locked, String world, double x, double y, double z, double yaw, double pitch) {
		this.item = item;
		this.locked = locked;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	public boolean getLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getWorld() {
		return world;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getYaw() {
		return yaw;
	}

	public double getPitch() {
		return pitch;
	}
}
