package net.opticraft.opticore.settings;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class SettingUtil {

	public Main plugin;

	public Config config;
	public BungeecordUtil bungeecordUtil;

	public SettingUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
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
		
		plugin.mysql.update("oc_users", 
				Arrays.asList("setting_" + setting), 
				Arrays.asList(value), 
				"uuid", player.getUniqueId().toString());
		
		//plugin.mysql.setUUIDColumnValue(player.getName(), "oc_users", "setting_" + setting, value);
	}
}
