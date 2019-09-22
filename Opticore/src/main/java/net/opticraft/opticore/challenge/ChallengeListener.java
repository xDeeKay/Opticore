package net.opticraft.opticore.challenge;

import org.apache.commons.lang.WordUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

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

	public void challengeCheck(Player player, String type, String target) {

		String uuid = player.getUniqueId().toString();

		for (String challenge : plugin.challenges.keySet()) {

			if (plugin.challenges.get(challenge).getType().equalsIgnoreCase(type)) {

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
							type = type.substring(0, 1).toUpperCase() + type.substring(1);
							target = target.toLowerCase().replaceAll("_", " ");
							target = WordUtils.capitalize(target);
							target = target.replaceAll("Of", "of");
							target = target.replaceAll("The", "the");
							util.sendStyledMessage(player, null, "GREEN", "R", "GOLD", "Challenge Completed: " + type + " " + ChatColor.WHITE + amount + ChatColor.GOLD + " of " + ChatColor.WHITE + target);
							plugin.rewardUtil.giveRewardPoint(player.getName(), plugin.challenges.get(challenge).getReward());
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

			challengeCheck(killer, "kill", entity.getType().toString());
		}
	}

	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {

		Player player = event.getPlayer();
		Item item = (Item) event.getCaught();

		if (event.getState().equals(State.CAUGHT_FISH) && item != null) {

			challengeCheck(player, "fish", item.getItemStack().getType().toString());
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
}
