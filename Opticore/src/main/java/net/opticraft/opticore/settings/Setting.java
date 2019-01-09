package net.opticraft.opticore.settings;

public class Setting {
	
	private int value;
	
	private int maxValue;
	
	public Setting(int value, int maxValue) {
		this.setValue(value);
		this.setMaxValue(maxValue);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
}
