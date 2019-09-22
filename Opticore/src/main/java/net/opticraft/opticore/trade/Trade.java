package net.opticraft.opticore.trade;

import org.bukkit.inventory.ItemStack;

public class Trade {
	
	private String name;
	
	private ItemStack hasItem;
	
	private String wantsItem;
	private int wantsAmount;
	
	private long expiry;
	
	private String description;
	
	private int status;
	
	public Trade(String name, ItemStack hasItem, String wantsItem, int wantsAmount, long expiry, String description, int status) {
		this.name = name;
		this.hasItem = hasItem;
		this.wantsItem = wantsItem;
		this.wantsAmount = wantsAmount;
		this.expiry = expiry;
		this.description = description;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getHasItem() {
		return hasItem;
	}

	public void setHasItem(ItemStack hasItem) {
		this.hasItem = hasItem;
	}

	public String getWantsItem() {
		return wantsItem;
	}

	public void setWantsItem(String wantsItem) {
		this.wantsItem = wantsItem;
	}

	public int getWantsAmount() {
		return wantsAmount;
	}

	public void setWantsAmount(int wantsAmount) {
		this.wantsAmount = wantsAmount;
	}

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean isActive() {
		if (status == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isExpired() {
		if (status == 1) {
			return true;
		}
		return false;
	}
	
	public boolean isSold() {
		if (status == 2) {
			return true;
		}
		return false;
	}
}
