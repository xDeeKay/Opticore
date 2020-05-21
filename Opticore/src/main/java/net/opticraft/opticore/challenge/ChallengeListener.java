package net.opticraft.opticore.challenge;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import net.md_5.bungee.api.ChatColor;
import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class ChallengeListener implements Listener {

	public Main plugin;

	public Config config;
	public Util util;

	public ChallengeListener(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public void challengeCheck(Player player, String task, String target) {
		
		if (player != null) {
			
			String uuid = player.getUniqueId().toString();
			
			for (String challenge : plugin.challenges.keySet()) {

				if (plugin.challenges.get(challenge).getTask().equalsIgnoreCase(task)) {

					if (target.equals(plugin.challenges.get(challenge).getTarget().toUpperCase())) {

						if (!plugin.challenges.get(challenge).getProgress().containsKey(uuid)) {
							plugin.challenges.get(challenge).getProgress().put(uuid, 0);
							plugin.getConfig().set("challenges." + challenge + ".progress." + uuid, 0);
							plugin.saveConfig();
						}

						int progress = plugin.challenges.get(challenge).getProgress().get(uuid);
						int amount = plugin.challenges.get(challenge).getAmount();

						if (progress + 1 <= amount) {
							plugin.challenges.get(challenge).getProgress().put(uuid, progress + 1);
							plugin.getConfig().set("challenges." + challenge + ".progress." + uuid, progress + 1);
							plugin.saveConfig();

							if (progress + 1 == amount) {
								task = task.substring(0, 1).toUpperCase() + task.substring(1);
								target = target.toLowerCase().replaceAll("_", " ");
								target = WordUtils.capitalize(target);
								target = target.replaceAll("Of", "of");
								target = target.replaceAll("The", "the");
								util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "Challenge Completed: " + task + " " + ChatColor.WHITE + amount + ChatColor.GOLD + " of " + ChatColor.WHITE + target);
								plugin.rewardUtil.giveRewardPoint(player.getName(), plugin.challenges.get(challenge).getReward());
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {

		LivingEntity entity = event.getEntity();
		Player killer = event.getEntity().getKiller();

		if (killer != null) {

			String target = entity.getType().toString();

			// Check if a baby zombie variant dies while riding a chicken
			if (entity instanceof Zombie) {

				Zombie zombie = (Zombie) entity;

				if (zombie.isBaby()) {

					if (zombie.getVehicle() != null) {
						if (zombie.getVehicle().getType().equals(EntityType.CHICKEN)) {

							target = "CHICKEN_JOCKEY";
						}
					}
				}
			}

			// Check if a chicken dies while carrying a baby zombie variant
			if (entity.getType().equals(EntityType.CHICKEN)) {

				if (!entity.getPassengers().isEmpty()) {

					Entity passenger = entity.getPassengers().get(0);

					if (passenger instanceof Zombie) {

						Zombie zombie = (Zombie) passenger;

						if (zombie.isBaby()) {

							target = "CHICKEN_JOCKEY";

						}
					}
				}
			}

			// Check if a skeleton variant dies while riding a spider or cave spider
			if (entity.getType().equals(EntityType.SKELETON) || 
					entity.getType().equals(EntityType.STRAY) || 
					entity.getType().equals(EntityType.WITHER_SKELETON)) {

				if (entity.getVehicle() != null) {
					if (entity.getVehicle().getType().equals(EntityType.SPIDER) || entity.getVehicle().getType().equals(EntityType.CAVE_SPIDER)) {

						target = "SPIDER_JOCKEY";
					}
				}
			}

			// Check if a spider or cave spider dies while carrying a skeleton variant
			if (entity.getType().equals(EntityType.SPIDER) || entity.getType().equals(EntityType.CAVE_SPIDER)) {

				if (!entity.getPassengers().isEmpty()) {

					Entity passenger = entity.getPassengers().get(0);

					if (passenger.getType().equals(EntityType.SKELETON) || 
							passenger.getType().equals(EntityType.STRAY) || 
							passenger.getType().equals(EntityType.WITHER_SKELETON)) {

						target = "SPIDER_JOCKEY";
					}
				}
			}

			// Check if a skeleton dies while riding a skeleton horse
			if (entity.getType().equals(EntityType.SKELETON)) {

				if (entity.getVehicle() != null) {
					if (entity.getVehicle().getType().equals(EntityType.SKELETON_HORSE)) {
						
						target = "SKELETON_HORSEMAN";
					}
				}
			}

			// Check if a skeleton horse dies while carrying a skeleton
			if (entity.getType().equals(EntityType.SKELETON_HORSE)) {

				if (!entity.getPassengers().isEmpty()) {

					Entity passenger = entity.getPassengers().get(0);

					if (passenger.getType().equals(EntityType.SKELETON)) {

						target = "SKELETON_HORSEMAN";
					}
				}
			}

			challengeCheck(killer, "kill", target);
		}
	}

	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {

		Player player = event.getPlayer();
		Item item = (Item) event.getCaught();

		if (event.getState().equals(State.CAUGHT_FISH) && item != null) {

			String target = item.getItemStack().getType().toString();

			if (item.getItemStack().getType().equals(Material.POTION)) {

				PotionMeta meta = (PotionMeta) item.getItemStack().getItemMeta();

				if (meta.getBasePotionData().getType().equals(PotionType.WATER)) {

					target = "WATER_BOTTLE";

				}
			}

			challengeCheck(player, "fish", target);
		}
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent event) {

		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();

		//System.out.println("craft item");

		for (int i = 0; i < item.getAmount(); i++) {
			//System.out.println("item: " + i);
			challengeCheck(player, "craft", item.getType().toString());
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();
		Block block = event.getBlock();

		challengeCheck(player, "mine", block.getType().toString());
	}

	@EventHandler
	public void onEntityBreed(EntityBreedEvent event) {

		Player player = (Player) event.getBreeder();
		Entity entity = event.getEntity();

		challengeCheck(player, "breed", entity.getType().toString());
	}

	@EventHandler
	public void onFurnaceExtract(FurnaceExtractEvent event) {

		Player player = event.getPlayer();

		//System.out.println("furnace extract");

		for (int i = 0; i < event.getItemAmount(); i++) {
			//System.out.println("item: " + i);
			challengeCheck(player, "smelt", event.getItemType().toString());
		}
	}

	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {

		Player player = event.getEnchanter();
		ItemStack item = event.getItem();

		challengeCheck(player, "enchant", item.getType().toString());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {


	}

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {

		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		challengeCheck(player, "consume", item.getType().toString());
	}

	@EventHandler
	public void onEntityTame(EntityTameEvent event) {

		Player player = (Player) event.getOwner();
		Entity entity = event.getEntity();

		challengeCheck(player, "tame", entity.getType().toString());
	}
}
