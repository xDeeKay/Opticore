package net.opticraft.opticore.challenge;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
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
						int reward = plugin.challenges.get(challenge).getReward();

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
								plugin.rewardUtil.giveRewardPoint(player.getName(), reward);

								long timestamp = System.currentTimeMillis() / 1000;
								String server = plugin.config.getServerName();

								// Log challenge to the database
								plugin.mysql.insert("oc_challenges", 
										Arrays.asList("uuid", "task", "target", "amount", "reward", "timestamp", "server"), 
										Arrays.asList(uuid, plugin.challenges.get(challenge).getTask(), plugin.challenges.get(challenge).getTarget(), amount, reward, timestamp, server));
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
		Player killer = entity.getKiller();

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
		Entity caught = event.getCaught();

		if (event.getState().equals(State.CAUGHT_FISH)) {

			if (caught.getType().equals(EntityType.DROPPED_ITEM)) {

				Item item = (Item) caught;

				if (item != null) {

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
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();
		Block block = event.getBlock();

		// Check if a player breaks wheat, potatoes, or carrots at age 7
		if (block.getType().equals(Material.WHEAT) || block.getType().equals(Material.POTATOES) || block.getType().equals(Material.CARROTS)) {
			Ageable crop = (Ageable) block.getBlockData();
			if (crop.getAge() == 7) {
				challengeCheck(player, "farm", block.getType().toString());
			}
		}

		// Check if a player breaks cocoa at age 2
		if (block.getType().equals(Material.COCOA)) {
			Ageable crop = (Ageable) block.getBlockData();
			if (crop.getAge() == 2) {
				challengeCheck(player, "farm", block.getType().toString());
			}
		}

		// Check if a player breaks beetroots, sweet berry bush, or nether wart at age 3
		if (block.getType().equals(Material.BEETROOTS) || block.getType().equals(Material.SWEET_BERRY_BUSH) || block.getType().equals(Material.NETHER_WART)) {
			Ageable crop = (Ageable) block.getBlockData();
			if (crop.getAge() == 3) {
				challengeCheck(player, "farm", block.getType().toString());
			}
		}

		// Check if a player breaks a melon or pumpkin with an adjacent stem
		if (block.getType().equals(Material.MELON) || block.getType().equals(Material.PUMPKIN)) {

			Material stemMaterial = Material.valueOf("ATTACHED_" + block.getType().toString() + "_STEM");

			List<BlockFace> faces = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

			for (BlockFace face : faces) {

				Block facingBlock = block.getRelative(face);

				if (facingBlock.getType().equals(stemMaterial)) {

					Directional stem = (Directional) facingBlock.getBlockData();

					if (stem.getFacing().equals(face.getOppositeFace())) {
						challengeCheck(player, "farm", block.getType().toString());
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityBreed(EntityBreedEvent event) {

		LivingEntity breeder = event.getBreeder();
		Entity entity = event.getEntity();

		if (breeder != null && breeder.getType().equals(EntityType.PLAYER)) {
			Player player = (Player) breeder;
			challengeCheck(player, "breed", entity.getType().toString());
		}
	}

	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {

		Player player = event.getEnchanter();
		ItemStack item = event.getItem();

		challengeCheck(player, "enchant", item.getType().toString());
	}

	public int getItemAmountShiftClicked(CraftingInventory inventory, ItemStack item) {

		int itemsChecked = 0;
		int possibleCreations = 1;

		for (ItemStack item1 : inventory.getMatrix()) {
			if (item1 != null && !item1.getType().equals(Material.AIR)) {
				if (itemsChecked == 0) {
					possibleCreations = item1.getAmount();
				} else {
					possibleCreations = Math.min(possibleCreations, item1.getAmount());
				}
				itemsChecked++;
			}
		}

		int amountOfItems = item.getAmount() * possibleCreations;

		return amountOfItems;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getClickedInventory();
		SlotType slotType = event.getSlotType();
		ClickType clickType = event.getClick();
		ItemStack item = event.getCurrentItem();

		if (item != null && !item.getType().equals(Material.AIR)) {

			if (slotType.equals(SlotType.CRAFTING) && inventory.getType().equals(InventoryType.BREWING)) {

				if (item.getType().equals(Material.POTION) || item.getType().equals(Material.LINGERING_POTION) || item.getType().equals(Material.SPLASH_POTION)) {

					PotionMeta potion = (PotionMeta) item.getItemMeta();
					PotionData potionData = potion.getBasePotionData();
					PotionType potionType = potionData.getType();

					String potionPrefix = item.getType().toString() + "_OF_";
					String potionEffect = potionType.toString();

					if (potionType.equals(PotionType.INSTANT_HEAL)) {
						potionEffect = "HEALING";
					}

					if (potionType.equals(PotionType.REGEN)) {
						potionEffect = "REGENERATION";
					}

					if (potionType.equals(PotionType.SPEED)) {
						potionEffect = "SWIFTNESS";
					}

					if (potionType.equals(PotionType.JUMP)) {
						potionEffect = "LEAPING";
					}

					if (potionType.equals(PotionType.INSTANT_DAMAGE)) {
						potionEffect = "HARMING";
					}

					if (potionType.equals(PotionType.TURTLE_MASTER)) {
						potionEffect = "THE_TURTLE_MASTER";
					}

					String target = potionPrefix + potionEffect;

					challengeCheck(player, "brew", target);
				}
			}

			if (slotType.equals(SlotType.RESULT)) {

				if (clickType.equals(ClickType.SHIFT_LEFT) || clickType.equals(ClickType.SHIFT_RIGHT)) {

					if (inventory.getType().equals(InventoryType.CRAFTING) || inventory.getType().equals(InventoryType.WORKBENCH)) {

						CraftingInventory inv = (CraftingInventory) inventory;
						int amountOfItems = getItemAmountShiftClicked(inv, item);

						for (int i = 0; i < amountOfItems; i++) {
							challengeCheck(player, "craft", item.getType().toString());
						}
					}

					if (inventory.getType().equals(InventoryType.ANVIL) || inventory.getType().equals(InventoryType.GRINDSTONE)) {
						challengeCheck(player, "repair", item.getType().toString());
					}

					if (inventory.getType().equals(InventoryType.BLAST_FURNACE) || inventory.getType().equals(InventoryType.FURNACE) || inventory.getType().equals(InventoryType.SMOKER)) {

						int amountOfItems = item.getAmount();

						for (int i = 0; i < amountOfItems; i++) {
							challengeCheck(player, "smelt", item.getType().toString());
						}
					}

					/*if (inventory.getType().equals(InventoryType.MERCHANT)) {

						MerchantInventory inv = (MerchantInventory) inventory;
						MerchantRecipe recipe = inv.getSelectedRecipe();

						List<ItemStack> ingredients = recipe.getIngredients();
						ItemStack ingredient1 = ingredients.get(0);
						ItemStack ingredient2 = ingredients.get(1);
						player.sendMessage("ingredient1:" + ingredient1);
						player.sendMessage("ingredient2:" + ingredient2);

						float priceMultiplier = recipe.getPriceMultiplier();

						ItemStack slot1 = inv.getItem(0);
						ItemStack slot2 = inv.getItem(1);

						player.sendMessage("slot1:" + slot1);
						player.sendMessage("slot2:" + slot2);

						int total = 0;

						if (ingredient1.getType().equals(slot1.getType())) {

							int ingredient1Discount = (int) (ingredient1.getAmount() * priceMultiplier);
							player.sendMessage("ingredient1Discount:" + ingredient1Discount);
							int ingredient1Amount = ingredient1.getAmount() - ingredient1Discount;
							player.sendMessage("ingredient1Amount:" + ingredient1Amount);

							if (slot1.getAmount() >= ingredient1Amount) {

								total = slot1.getAmount() / ingredient1Amount;

								player.sendMessage("total:" + total);

							}
						}

						for (int i = 0; i < total; i++) {
							challengeCheck(player, "trade", item.getType().toString());
						}
					}*/

				} else {

					for (int i = 0; i < item.getAmount(); i++) {

						if (inventory.getType().equals(InventoryType.CRAFTING) || inventory.getType().equals(InventoryType.WORKBENCH)) {
							challengeCheck(player, "craft", item.getType().toString());
						}

						/*if (inventory.getType().equals(InventoryType.MERCHANT)) {
							challengeCheck(player, "trade", item.getType().toString());
						}*/

						if (inventory.getType().equals(InventoryType.ANVIL) || inventory.getType().equals(InventoryType.GRINDSTONE)) {
							challengeCheck(player, "repair", item.getType().toString());
						}

						if (inventory.getType().equals(InventoryType.BLAST_FURNACE) || inventory.getType().equals(InventoryType.FURNACE) || inventory.getType().equals(InventoryType.SMOKER)) {
							challengeCheck(player, "smelt", item.getType().toString());
						}
					}
				}
			}
		}
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

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		// Check if a player right clicks a sweet berry bush at age 3
		if (block != null && block.getType().equals(Material.SWEET_BERRY_BUSH) && action.equals(Action.RIGHT_CLICK_BLOCK)) {
			Ageable crop = (Ageable) block.getBlockData();
			if (crop.getAge() == 3) {
				challengeCheck(player, "farm", block.getType().toString());
			}
		}

		// Check if a player right clicks a beehive or bee nest with a honey bottle at honey level 5
		if (action.equals(Action.RIGHT_CLICK_BLOCK) && util.getItemInAnyHand(player) != null && util.getItemInAnyHand(player).getType().equals(Material.GLASS_BOTTLE)) {
			if (block != null && block.getType().equals(Material.BEEHIVE) || block.getType().equals(Material.BEE_NEST)) {
				Beehive hive = (Beehive) block.getBlockData();
				if (hive.getHoneyLevel() == 5) {
					challengeCheck(player, "farm", "HONEY_BOTTLE");
				}
			}
		}

		// Check if a player right clicks a beehive or bee nest with shears at honey level 5
		if (action.equals(Action.RIGHT_CLICK_BLOCK) && util.getItemInAnyHand(player) != null  && util.getItemInAnyHand(player).getType().equals(Material.SHEARS)) {
			if (block != null && block.getType().equals(Material.BEEHIVE) || block.getType().equals(Material.BEE_NEST)) {
				Beehive hive = (Beehive) block.getBlockData();
				if (hive.getHoneyLevel() == 5) {
					challengeCheck(player, "farm", "HONEYCOMB");
				}
			}
		}
	}
}
