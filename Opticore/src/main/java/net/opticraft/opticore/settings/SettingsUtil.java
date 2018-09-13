package net.opticraft.opticore.settings;

import org.bukkit.entity.Player;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class SettingsUtil {

	public Main plugin;

	public Config config;
	public BungeecordUtil bungeecordUtil;

	public SettingsUtil(Main plugin) {
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
		plugin.mysql.setUsersColumnValue(player.getName(), "setting_" + setting, value);
	}
}
