package net.opticraft.opticore.challenge;

import java.util.HashMap;
import java.util.Map;

public class Challenge {
	
	private String name;
	
	private long ends;
	
	private boolean replace;
	
	private String task;
	
	private String target;
	
	private int amount;
	
	private int reward;
	
	private Map<String, Integer> progress = new HashMap<String, Integer>();
	
	public Challenge(String name, long ends, boolean replace, String task, String target, int amount, int reward, Map<String, Integer> progress) {
		this.name = name;
		this.ends = ends;
		this.replace = replace;
		this.task = task;
		this.target = target;
		this.amount = amount;
		this.reward = reward;
		this.progress = progress;
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

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
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
