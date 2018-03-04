package net.opticraft.opticore.util.wither;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Util;

public class WitherListener implements Listener {

	public Main plugin;

	public Util util;

	public WitherListener(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {

		Player player = event.getPlayer();

		Location location = player.getLocation();

		if (event.getCause().equals(TeleportCause.NETHER_PORTAL)) {
			for(String wither : plugin.withers.keySet()) {
				if (plugin.withers.get(wither).getEntryPortalBlock1().equals(location) || plugin.withers.get(wither).getEntryPortalBlock2().equals(location)) {
					event.setCancelled(true);
					event.setTo(plugin.withers.get(wither).getExitPortalSpawn());
				}
			}
		}
	}

	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event) {
		if (event.getBlock().getType().equals(Material.PORTAL)) {
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {

		if (event.getSpawnReason().equals(SpawnReason.BUILD_WITHER)) {

			// Original location of wither
			Location location = event.getLocation();
			World world = location.getWorld();
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();

			util.sendStyledMessage(null, null, "GOLD", "!", "GOLD", "wither is attempting to spawn at " + x + " " + y + " " + z);

			// North Skull
			Location skullNorthLocation = new Location(world, x, y + 2, z - 1);
			Block skullNorthBlock = skullNorthLocation.getWorld().getBlockAt(skullNorthLocation);

			// South Skull
			Location skullSouthLocation = new Location(world, x, y + 2, z + 1);
			Block skullSouthBlock = skullSouthLocation.getWorld().getBlockAt(skullSouthLocation);

			// East Skull
			Location skullEastLocation = new Location(world, x + 1, y + 2, z);
			Block skullEastBlock = skullEastLocation.getWorld().getBlockAt(skullEastLocation);

			// West Skull
			Location skullWestLocation = new Location(world, x - 1, y + 2, z);
			Block skullWestBlock = skullWestLocation.getWorld().getBlockAt(skullWestLocation);
			
			// Portal Top
			Location portalTopLocation = new Location(world, x, y + 1, z);
			Block portalTopBlock = portalTopLocation.getWorld().getBlockAt(portalTopLocation);
			portalTopBlock.setType(Material.PORTAL);
			
			// Portal Bottom
			Location portalBottomLocation = new Location(world, x, y, z);
			Block portalBottomBlock = portalBottomLocation.getWorld().getBlockAt(portalBottomLocation);
			portalBottomBlock.setType(Material.PORTAL);

			// Center Skull
			Location skullCenterLocation = new Location(world, x, y + 2, z);
			Block skullCenterBlock = skullCenterLocation.getWorld().getBlockAt(skullCenterLocation);
			skullCenterBlock.setType(Material.AIR);

			if (skullNorthBlock.getType().equals(Material.SKULL) && skullSouthBlock.getType().equals(Material.SKULL)) {

				// North Skull
				skullNorthBlock.setType(Material.AIR);

				// North Frame Top
				Location frameNorthTopLocation = new Location(world, skullNorthLocation.getX(), skullNorthLocation.getY() - 1, skullNorthLocation.getZ());
				Block frameNorthTopBlock = frameNorthTopLocation.getWorld().getBlockAt(frameNorthTopLocation);
				frameNorthTopBlock.setType(Material.SOUL_SAND);

				// North Frame Bottom
				Location frameNorthBottomLocation = new Location(world, skullNorthLocation.getX(), skullNorthLocation.getY() - 2, skullNorthLocation.getZ());
				Block frameNorthBottomBlock = frameNorthBottomLocation.getWorld().getBlockAt(frameNorthBottomLocation);
				frameNorthBottomBlock.setType(Material.SOUL_SAND);

				// South Skull
				skullSouthBlock.setType(Material.AIR);

				// South Frame Top
				Location frameSouthTopLocation = new Location(world, skullSouthLocation.getX(), skullSouthLocation.getY() - 1, skullSouthLocation.getZ());
				Block frameSouthTopBlock = frameSouthTopLocation.getWorld().getBlockAt(frameSouthTopLocation);
				frameSouthTopBlock.setType(Material.SOUL_SAND);

				// South Frame Bottom
				Location frameSouthBottomLocation = new Location(world, skullSouthLocation.getX(), skullSouthLocation.getY() - 2, skullSouthLocation.getZ());
				Block frameSouthBottomBlock = frameSouthBottomLocation.getWorld().getBlockAt(frameSouthBottomLocation);
				frameSouthBottomBlock.setType(Material.SOUL_SAND);
				
				// Portal Orientation
				portalTopBlock.setData((byte) 2, false);
				portalBottomBlock.setData((byte) 2, false);

			} else if (skullEastBlock.getType().equals(Material.SKULL) && skullWestBlock.getType().equals(Material.SKULL)) {
				
				// East Skull
				skullEastBlock.setType(Material.AIR);

				// East Frame Top
				Location frameEastTopLocation = new Location(world, skullEastLocation.getX(), skullEastLocation.getY() - 1, skullEastLocation.getZ());
				Block frameEastTopBlock = frameEastTopLocation.getWorld().getBlockAt(frameEastTopLocation);
				frameEastTopBlock.setType(Material.SOUL_SAND);

				// East Frame Bottom
				Location frameEastBottomLocation = new Location(world, skullEastLocation.getX(), skullEastLocation.getY() - 2, skullEastLocation.getZ());
				Block frameEastBottomBlock = frameEastBottomLocation.getWorld().getBlockAt(frameEastBottomLocation);
				frameEastBottomBlock.setType(Material.SOUL_SAND);

				// West Skull
				skullWestBlock.setType(Material.AIR);

				// West Frame Top
				Location frameWestTopLocation = new Location(world, skullWestLocation.getX(), skullWestLocation.getY() - 1, skullWestLocation.getZ());
				Block frameWestTopBlock = frameWestTopLocation.getWorld().getBlockAt(frameWestTopLocation);
				frameWestTopBlock.setType(Material.SOUL_SAND);

				// West Frame Bottom
				Location frameWestBottomLocation = new Location(world, skullWestLocation.getX(), skullWestLocation.getY() - 2, skullWestLocation.getZ());
				Block frameWestBottomBlock = frameWestBottomLocation.getWorld().getBlockAt(frameWestBottomLocation);
				frameWestBottomBlock.setType(Material.SOUL_SAND);
				
				// Portal Orientation
				portalTopBlock.setData((byte) 4, false);
				portalBottomBlock.setData((byte) 4, false);
			}
			
			LocalWorld localWorld = BukkitUtil.getLocalWorld(plugin.getServer().getWorld(world.getName()));
			EditSession editSession = new EditSession(localWorld, Integer.MAX_VALUE);

			Location pos1 = new Location(world, x + 5, y + 5, z + 5);
			Location pos2 = new Location(world, x - 5, y - 5, z - 5);

			CuboidSelection selection = new CuboidSelection(world, pos1, pos2);

			Vector size = new Vector(selection.getLength(), selection.getHeight(), selection.getWidth());

			Vector origin = selection.getNativeMinimumPoint();
			Vector offset = selection.getNativeMaximumPoint();

			CuboidClipboard clipboard = new CuboidClipboard(size, origin, offset);
			clipboard.copy(editSession);

			File dataDirectory = new File (plugin.getDataFolder(), "wither");
			File source = new File(dataDirectory, "wither-template");
			File destination = new File(plugin.getServer().getWorldContainer().getAbsolutePath(), "wither1");

			Util.copyFolder(source, destination);
			
			WorldCreator wc = new WorldCreator("wither1");
			wc.environment(Environment.NETHER);
			wc.createWorld();

			World wither1 = plugin.getServer().getWorld("wither1");
			
			util.pasteSchematics(wither1, clipboard);
			
			Location entryPortalSpawn = new Location(world, portalBottomLocation.getX(), portalBottomLocation.getY(), portalBottomLocation.getZ());
			Location exitPortalSpawn = new Location(wither1, portalBottomLocation.getX(), portalBottomLocation.getY(), portalBottomLocation.getZ());
			
			plugin.withers.put("wither1", new Wither(portalTopLocation, portalBottomLocation, entryPortalSpawn, skullCenterLocation, skullCenterLocation, exitPortalSpawn));

			/*
			// Portal block locations
			Location portalBlock1Location = new Location(world, x - 1, y, z);
			Block portalBlock1 = portalBlock1Location.getWorld().getBlockAt(portalBlock1Location);
			portalBlock1.setType(Material.BEDROCK);

			Location portalBlock2Location = new Location(world, x - 1, y + 1, z);
			Block portalBlock2 = portalBlock2Location.getWorld().getBlockAt(portalBlock2Location);
			portalBlock2.setType(Material.BEDROCK);

			Location portalBlock3Location = new Location(world, x, y, z);
			Block portalBlock3 = portalBlock3Location.getWorld().getBlockAt(portalBlock3Location);
			portalBlock3.setType(Material.PORTAL);

			Location portalBlock4Location = new Location(world, x, y + 1, z);
			Block portalBlock4 = portalBlock4Location.getWorld().getBlockAt(portalBlock4Location);
			portalBlock4.setType(Material.PORTAL);

			Location portalBlock5Location = new Location(world, x + 1, y, z);
			Block portalBlock5 = portalBlock5Location.getWorld().getBlockAt(portalBlock5Location);
			portalBlock5.setType(Material.BRICK);

			Location portalBlock6Location = new Location(world, x + 1, y + 1, z);
			Block portalBlock6 = portalBlock6Location.getWorld().getBlockAt(portalBlock6Location);
			portalBlock6.setType(Material.BRICK);

			Location portalBlock7Location = new Location(world, x - 1, y + 2, z);
			Block portalBlock7 = portalBlock7Location.getWorld().getBlockAt(portalBlock7Location);
			portalBlock7.setType(Material.STONE);

			Location portalBlock8Location = new Location(world, x, y + 2, z);
			Block portalBlock8 = portalBlock8Location.getWorld().getBlockAt(portalBlock8Location);
			portalBlock8.setType(Material.STONE);

			Location portalBlock9Location = new Location(world, x + 1, y + 2, z);
			Block portalBlock9 = portalBlock9Location.getWorld().getBlockAt(portalBlock9Location);
			portalBlock9.setType(Material.STONE);
			 */

			// Cancel wither spawn
			event.setCancelled(true);
		}
	}
}
