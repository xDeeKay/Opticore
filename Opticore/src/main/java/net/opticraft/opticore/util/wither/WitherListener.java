package net.opticraft.opticore.util.wither;

import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
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
	public void onCreatureSpawn(CreatureSpawnEvent event) {

		if (event.getSpawnReason().equals(SpawnReason.BUILD_WITHER)) {
			
			org.bukkit.World template = plugin.getServer().getWorld("template");
			
			plugin.getServer().createWorld(new WorldCreator("newworld").copy(template));

			LivingEntity wither = event.getEntity();
			String witherUUID = wither.getUniqueId().toString();

			Location loc = event.getLocation();
			String x = String.valueOf(loc.getX());
			String y = String.valueOf(loc.getY());
			String z = String.valueOf(loc.getZ());
			String location = StringUtils.join(new String[] {x, y, z}, ", ");

			List<Entity> nearbyList = wither.getNearbyEntities(5, 5, 5);
			
			if (!plugin.withers.containsKey(witherUUID)) {
				plugin.withers.put(witherUUID, new Wither());
				util.log(Level.INFO, "Wither created with uuid " + witherUUID);
			}
			
			util.sendStyledMessage(null, null, "DARK_GRAY", "!", "GOLD", "A wither has been spawned at " + location + ".");
			util.sendStyledMessage(null, null, "DARK_GRAY", "!", "GOLD", "Type '/wither' and get in range to fight it!");

			for (Entity nearby : nearbyList) {

				if (nearby instanceof Player) {
					Player player = (Player) nearby;

					if (plugin.wither.contains(player.getUniqueId().toString())) {

						plugin.withers.get(witherUUID).getTargets().add(player.getUniqueId().toString());
						util.log(Level.INFO, "Added player to Wither with uuid " + witherUUID);

					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
		
		Entity entity = event.getEntity();

		if (entity.getType().equals(EntityType.WITHER)) {
			
			util.log(Level.INFO, "Wither targeted something");

			Entity wither = event.getEntity();
			String witherUUID = wither.getUniqueId().toString();

			LivingEntity target = event.getTarget();

			if (plugin.withers.containsKey(witherUUID)) {

				if (target instanceof Player) {
					Player player = (Player) target;

					if (plugin.withers.get(witherUUID).getTargets().contains(player.getUniqueId().toString())) {

						event.setCancelled(false);
						util.log(Level.INFO, "Wither " + witherUUID + " targeted player " + player);

					} else {
						event.setCancelled(true);
						util.log(Level.INFO, "Wither " + witherUUID + " tried to target player " + player + " but they were immune");
					}
				} else {
					event.setCancelled(true);
					util.log(Level.INFO, "Wither " + witherUUID + " tried to target entity " + target + " but they were not a player");
				}
			} else {
				event.setCancelled(true);
				util.log(Level.INFO, "Wither " + witherUUID + " tried to target entity " + target + " but wither does not exist");
			}
		} else if (entity.getType().equals(EntityType.WITHER_SKULL)) {
			
			util.log(Level.INFO, "Wither skull targeted something");
			
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

		Entity entity = event.getEntity();

		if (event.getDamager().getType().equals(EntityType.WITHER)) {
			util.log(Level.INFO, "Wither damaged something");
			
			Entity wither = event.getDamager();
			String witherUUID = wither.getUniqueId().toString();

			if (plugin.withers.containsKey(witherUUID)) {

				if (entity instanceof Player) {
					Player player = (Player) entity;

					if (plugin.withers.get(witherUUID).getTargets().contains(player.getUniqueId().toString())) {

						event.setCancelled(false);
						util.log(Level.INFO, "Wither " + witherUUID + " dealt damage to player " + player);

					} else {
						event.setCancelled(true);
						util.log(Level.INFO, "Wither " + witherUUID + " tried to damage player " + player + " but they were immune");
					}
				} else {
					event.setCancelled(true);
					util.log(Level.INFO, "Wither " + witherUUID + " tried to damage entity " + entity + " but they were not a player");
				}
			} else {
				event.setCancelled(true);
				util.log(Level.INFO, "Wither " + witherUUID + " tried to damage entity " + entity + " but wither does not exist");
			}
			
		} else if (event.getDamager().getType().equals(EntityType.PLAYER)) {
			Player player = (Player) event.getDamager();

			if (entity instanceof Wither) {
				Entity wither = entity;
				String witherUUID = wither.getUniqueId().toString();

				if (plugin.withers.containsKey(witherUUID)) {

					if (plugin.wither.contains(player.getUniqueId().toString())) {

						if (plugin.withers.get(witherUUID).getTargets().contains(player.getUniqueId().toString())) {
							
							event.setCancelled(false);
							util.log(Level.INFO, "Player " + player + " dealt damage to wither " + witherUUID);

						} else {
							event.setCancelled(false);
							plugin.withers.get(witherUUID).getTargets().add(player.getUniqueId().toString());
							util.log(Level.INFO, "Player " + player + " tried to damage wither " + witherUUID + " but they were immune, adding them now and dealing damage");
						}
					} else {
						event.setCancelled(true);
						util.log(Level.INFO, "Player " + player + " tried to damage wither " + witherUUID + " but player was not in /wither");
					}
				} else {
					event.setCancelled(true);
					util.log(Level.INFO, "Player " + player + " tried to damage wither " + witherUUID + " but wither does not exist");
				}
			}
		} else if (event.getDamager().getType().equals(EntityType.WITHER_SKULL)) {
			event.setCancelled(true);
			util.log(Level.INFO, "Wither skull damaged something");
		}
	}

}
