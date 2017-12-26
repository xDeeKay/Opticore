package net.opticraft.opticore.staff.note;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;

public class NoteCommand implements CommandExecutor {

	public Main plugin;
	
	public NoteUtil noteUtil;
	
	public GuiUtil guiUtil;
	
	public BungeecordUtil bungeecordUtil;
	
	public Util util;

	public NoteCommand(Main plugin) {
		this.plugin = plugin;
		this.bungeecordUtil = this.plugin.bungeecordUtil;
		this.noteUtil = this.plugin.noteUtil;
		this.guiUtil = this.plugin.guiUtil;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("note")) {
			
			if (args.length >= 2) {
				
				String target = args[0];
				String message = StringUtils.join(args, ' ', 1, args.length);
				
				noteUtil.note(target, sender.getName(), message);
				
				util.sendStyledMessage(null, sender, "GREEN", "/", "GOLD", "Added note for player '" + target + "'.");
				
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /note [player] [message]");
			}
		}
		return true;
	}
}
