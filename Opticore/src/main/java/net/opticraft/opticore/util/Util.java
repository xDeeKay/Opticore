package net.opticraft.opticore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.mask.ExistingBlockMask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.registry.WorldData;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@SuppressWarnings("deprecation")
public class Util {

	public Main plugin;

	public Config config;

	public Util(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public void log(Level level, String message) {
		plugin.getLogger().log(level, message);
	}

	public void debug(String message) {
		if (config.getLoggingDebug()) {
			log(Level.INFO, message);
		}
	}

	public ChatColor c(String color) {
		return ChatColor.valueOf(color.toUpperCase());
	}

	public void sendStyledMessage(Player player, CommandSender sender, String symbolColor, String symbol, String messageColor, String message) {

		String string = c("WHITE") + "[" + c(symbolColor) + symbol + c("WHITE") + "] " + c(messageColor) + message;

		if (player != null) {
			player.sendMessage(string);
		} else if (sender != null) {
			sender.sendMessage(string);
		} else {
			plugin.getServer().broadcastMessage(string);
		}
	}

	public boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public String getServerShort(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0);
		return rank;
	}

	public String getPlayerGroup(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> ranks = user.getParentIdentifiers();
		String rank = ranks.get(0);
		return rank;
	}

	public String getPlayerGroupPrefix(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		String color = user.getPrefix();
		return color;
	}

	public long parse(String input) {
		long result = 0;
		String number = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c)) {
				number += c;
			} else if (Character.isLetter(c) && !number.isEmpty()) {
				result += convert(Integer.parseInt(number), c);
				number = "";
			}
		}
		return result;
	}

	public long convert(int value, char unit) {
		switch (unit) {
		case 'w':
			return value * 604800;
		case 'd':
			return value * 86400;
		case 'h':
			return value * 3600;
		case 'm':
			return value * 60;
		case 's':
			return value * 1;
		}
		return 0;
	}

	public boolean isValidTimeString(String lengthString) {
		if (lengthString.contains("s") || 
				lengthString.contains("m") || 
				lengthString.contains("h") || 
				lengthString.contains("d")|| 
				lengthString.contains("w")) {
			return true;
		}
		return false;
	}

	public boolean isPermanent(String lengthString) {
		if ("permanent".startsWith(lengthString.toLowerCase()) || lengthString.equals("0")) {
			return true;
		}
		return false;
	}

	public String timestampDateFormat(long timestamp) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date(timestamp * 1000);
		String dateFormat = simpleDateFormat.format(date);
		return dateFormat;
	}

	public void saveSchematic(String filename, int x1, int y1, int z1, int x2, int y2, int z2, org.bukkit.World world) {
		World weWorld = new BukkitWorld(world);
		WorldData worldData = weWorld.getWorldData();
		Vector pos1 = new Vector(x1, y1, z1); //First corner of your cuboid
		Vector pos2 = new Vector(x2, y2, z2); //Second corner fo your cuboid
		CuboidRegion cReg = new CuboidRegion(weWorld, pos1, pos2);
		File dataDirectory = new File (plugin.getDataFolder(), "wither");
		File file = new File(dataDirectory, filename + ".schematic"); // The schematic file
		try {
			BlockArrayClipboard clipboard = new BlockArrayClipboard(cReg);      
			Extent source = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
			Extent destination = clipboard;
			ForwardExtentCopy copy = new ForwardExtentCopy(source, cReg, clipboard.getOrigin(), destination, pos1);
			copy.setSourceMask(new ExistingBlockMask(source));
			Operations.completeLegacy(copy);
			ClipboardFormat.SCHEMATIC.getWriter(new FileOutputStream(file)).write(clipboard, worldData);
		} catch (IOException | MaxChangedBlocksException e) {
			e.printStackTrace();
		}
	}

	public void pasteSchematics(org.bukkit.World world, CuboidClipboard cc) {
		try {
			EditSession es = new EditSession(new BukkitWorld(world), Integer.MAX_VALUE);
			cc.paste(es, new Vector(0, 1, 0), false);
			System.out.println("PASTING AREA INTO WITHER ARENA");
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		}
	}

	public void loadSchem(String filename, Location location) {
		WorldEditPlugin we = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		File schematic = new File(filename);
		EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), Integer.MAX_VALUE);
		try {
			MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, new Vector(0, location.getBlockY(), 0), false);
			System.out.println("PASTING SCHEMATIC");
		} catch (MaxChangedBlocksException | com.sk89q.worldedit.data.DataException | IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSchematic(String filename, int x, int y, int z, org.bukkit.World world) {
		File file = new File(plugin.getDataFolder() + File.separator + "wither", filename); // The schematic file
		Vector to = new Vector(x, y, z); // Where you want to paste
		World weWorld = new BukkitWorld(world);
		WorldData worldData = weWorld.getWorldData();
		Clipboard clipboard;
		try {
			clipboard = ClipboardFormat.SCHEMATIC.getReader(new FileInputStream(file)).read(worldData);
			Extent source = clipboard;
			Extent destination = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld, -1);
			ForwardExtentCopy copy = new ForwardExtentCopy(source, clipboard.getRegion(), clipboard.getOrigin(), destination, to);
			copy.setSourceMask(new ExistingBlockMask(clipboard));
			Operations.completeLegacy(copy);
		} catch (IOException | WorldEditException e) {
			e.printStackTrace();
		}
	}

	public static void copyFolder(File source, File destination) {
		if (source.isDirectory()) {

			if (!destination.exists()) {
				destination.mkdirs();
			}

			String files[] = source.list();

			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);

				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = null;
			OutputStream out = null;

			try {
				in = new FileInputStream(source);
				out = new FileOutputStream(destination);

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
