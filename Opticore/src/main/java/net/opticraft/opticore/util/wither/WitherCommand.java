package net.opticraft.opticore.util.wither;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.RegionSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.schematic.SchematicFormat;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class WitherCommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public WitherCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wither")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;
				String playerUUID = player.getUniqueId().toString();

				if (args.length == 0) {

					Location location = player.getLocation();

					World world = location.getWorld();
					int x = location.getBlockX();
					int y = location.getBlockY();
					int z = location.getBlockZ();

					LocalWorld localWorld = BukkitUtil.getLocalWorld(plugin.getServer().getWorld(world.getName()));
					EditSession editSession = new EditSession(localWorld, Integer.MAX_VALUE);

					Location pos1 = new Location(world, x + 5, y + 5, z + 5);
					Location pos2 = new Location(world, x - 5, y - 5, z - 5);

					CuboidSelection selection = new CuboidSelection(world, pos1, pos2);

					Vector size = new Vector(selection.getLength(), selection.getHeight(), selection.getWidth());

					Vector origin = selection.getNativeMinimumPoint();
					Vector offset = selection.getNativeMaximumPoint();

					player.sendMessage("min: " + origin);
					player.sendMessage("max: " + offset);

					CuboidClipboard clipboard = new CuboidClipboard(size, origin, offset);

					clipboard.copy(editSession);

					//util.saveSchematic("wither1", x + 5, y + 5, z + 5, x - 5, y - 5, z - 5, world);

					File dataDirectory = new File (plugin.getDataFolder(), "wither");
					File source = new File(dataDirectory, "wither-template");
					File destination = new File(plugin.getServer().getWorldContainer().getAbsolutePath(), "wither1");

					Util.copyFolder(source, destination);
					
					WorldCreator wc = new WorldCreator("wither1");
					wc.environment(Environment.NETHER);
					wc.createWorld();

					World wither1 = plugin.getServer().getWorld("wither1");
					Location newLoc = new Location(wither1, 0, y, 0);
					
					player.teleport(newLoc);
					
					util.pasteSchematics(wither1, clipboard);
					//util.loadSchematic("wither1.schematic", 0, y, 0, wither1);
					//util.loadSchem("wither1.schematic", newLoc);

				} else {
					util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "Incorrect syntax. Usage: /wither.");
				}
			} else {
				util.sendStyledMessage(null, sender, "RED", "/", "GOLD", "You must be a player to perform this command.");
			}
		}
		return true;
	}
}
