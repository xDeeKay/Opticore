package net.opticraft.opticore.ignore;

public class Ignore {
	
	private String targetName = null;
	
	private long ignoreTimestamp;
	
	public Ignore(String targetName, long ignoreTimestamp) {
		this.targetName = targetName;
		this.ignoreTimestamp = ignoreTimestamp;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public long getIgnoreTimestamp() {
		return ignoreTimestamp;
	}

	public void setIgnoreTimestamp(long ignoreTimestamp) {
		this.ignoreTimestamp = ignoreTimestamp;
	}

}
