package net.opticraft.opticore.challenge;

import java.util.HashMap;
import java.util.Map;

public class Challenge {
	
	private String name;
	
	private long ends;
	
	private boolean replace;
	
	private String type;
	
	private String target;
	
	private int amount;
	
	private int reward;
	
	private Map<String, Integer> progress = new HashMap<String, Integer>();
	
	public Challenge(String name, long ends, boolean replace, String type, String target, int amount, int reward, Map<String, Integer> progress) {
		this.setName(name);
		this.setEnds(ends);
		this.setReplace(replace);
		this.setType(type);
		this.setTarget(target);
		this.setAmount(amount);
		this.setReward(reward);
		this.setProgress(progress);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getEnds() {
		return ends;
	}

	public void setEnds(long ends) {
		this.ends = ends;
	}
	
	public boolean isReplace() {
		return replace;
	}

	public void setReplace(boolean replace) {
		this.replace = replace;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public Map<String, Integer> getProgress() {
		return progress;
	}

	public void setProgress(Map<String, Integer> progress) {
		this.progress = progress;
	}
}
