package net.opticraft.opticore.settings;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;

public class SettingUtil {

	public Main plugin;

	public SettingUtil(Main plugin) {
		this.plugin = plugin;
	}

	public void toggleSetting(Player player, String setting) {

		int value = plugin.players.get(player.getName()).getSettings().get(setting).getValue();
		int maxValue = plugin.players.get(player.getName()).getSettings().get(setting).getMaxValue();

		if (value < maxValue) {
			value = value + 1;
		} else {
			value = 0;
		}

		plugin.players.get(player.getName()).getSettings().get(setting).setValue(value);

		plugin.mysql.update("oc_settings", 
				Arrays.asList(setting), 
				Arrays.asList(value), 
				"uuid", player.getUniqueId().toString());
	}
}
