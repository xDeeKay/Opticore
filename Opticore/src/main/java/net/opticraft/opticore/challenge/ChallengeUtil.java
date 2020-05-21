package net.opticraft.opticore.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.opticraft.opticore.Main;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.Util;

public class ChallengeUtil {

	public Main plugin;

	public Config config;

	public Util util;

	public BukkitTask task;

	public ChallengeUtil(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public void loadConfig() {

		if (plugin.getConfig().isSet("challenges")) {

			Set<String> challenges = plugin.getConfig().getConfigurationSection("challenges").getKeys(false);

			if (!challenges.isEmpty()) {

				for (String challenge : challenges) {

					long ends = plugin.getConfig().getLong("challenges." + challenge + ".ends");
					boolean replace = plugin.getConfig().getBoolean("challenges." + challenge + ".replace");
					String task = plugin.getConfig().getString("challenges." + challenge + ".task");
					String target = plugin.getConfig().getString("challenges." + challenge + ".target");
					int amount = plugin.getConfig().getInt("challenges." + challenge + ".amount");
					int reward = plugin.getConfig().getInt("challenges." + challenge + ".reward");

					Map<String, Integer> progress = new HashMap<String, Integer>();

					Set<String> progressPlayers = plugin.getConfig().getConfigurationSection("challenges." + challenge + ".progress").getKeys(false);

					if (!progressPlayers.isEmpty()) {

						for (String player : progressPlayers) {

							progress.put(player, plugin.getConfig().getInt("challenges." + challenge + ".progress." + player));
						}
					}

					plugin.challenges.put(challenge, new Challenge(challenge, ends, replace, task, target, amount, reward, progress));
				}
			}
		}
	}

	public void startExpiryCheck() {
		this.task = new BukkitRunnable() {
			public void run() {
				if (!plugin.challenges.isEmpty()) {
					//System.out.println("running challenge expiry check");
					checkExpiry();
				}
			}
		}.runTaskTimer(plugin, 20 * 5, 20 * 5);
	}

	public void stopExpiryCheck() {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}

	public void checkExpiry() {

		long timestamp = System.currentTimeMillis() / 1000;

		List<String> toRemove = new ArrayList<String>();
		for (String challenge : plugin.challenges.keySet()) {

			if (timestamp >= plugin.challenges.get(challenge).getEnds()) {

				if (plugin.challenges.get(challenge).isReplace()) {
					Challenge randomChallenge = randomChallenge();
					createChallenge(randomChallenge.getName(), randomChallenge.getEnds(), randomChallenge.isReplace(), randomChallenge.getTask(), 
							randomChallenge.getTarget(), randomChallenge.getAmount(), randomChallenge.getReward());
				}

				//deleteChallenge(challenge);

				challenge = resolveChallengeCase(challenge);

				plugin.getConfig().set("challenges." + challenge, null);
				plugin.saveConfig();

				toRemove.add(challenge);
			}
		}
		plugin.challenges.keySet().removeAll(toRemove);
	}

	public Challenge randomChallenge() {

		Random random = new Random();

		String name = String.valueOf(0);

		int highest = 0;
		for (String challenge : plugin.challenges.keySet()) {

			if (util.isInt(challenge)) {

				int challengeNum = Integer.valueOf(challenge);

				if (challengeNum > highest) {

					highest = challengeNum;
				}
			}
		}
		name = String.valueOf(highest + 1);

		long timestamp = System.currentTimeMillis() / 1000;
		long ends = timestamp + 604800;

		boolean replace = true;

		//List<String> tasks = Arrays.asList("kill", "fish", "craft", "mine", "breed", "smelt", "enchant", "repair", "trade", "consume", "brew", "tame");
		List<String> tasks = Arrays.asList("kill", "fish", "breed", "smelt", "enchant", "consume", "tame");
		String task = tasks.get(random.nextInt(tasks.size()));

		String target = null;

		int amount = 0;
		int min = 0;
		int max = 0;

		int reward = 0;
		double rewardMultiplier = 0.0;

		if (task.equals("kill")) {

			List<String> targets = Arrays.asList("blaze", "cave_spider", "chicken_jockey", "creeper", "drowned", "elder_guardian", "ender_dragon", "enderman", "endermite", "evoker", 
					"ghast", "guardian", "husk", "magma_cube", "phantom", "pillager", "ravager", "shulker", "silverfish", "skeleton_horseman", 
					"skeleton", "slime", "spider_jockey", "spider", "stray", "vex", "vindicator", "witch", "wither_skeleton", "wither", 
					"pig_zombie", "zombie_villager", "zombie");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("cave_spider") || target.equals("creeper") || target.equals("drowned") || target.equals("guardian") || 
					target.equals("husk") || target.equals("pillager") || target.equals("skeleton") || target.equals("slime") || 
					target.equals("spider") || target.equals("stray") || target.equals("witch") || target.equals("zombie")) {
				min = 16;
				max = 48;
				rewardMultiplier = 0.8;

			} else if (target.equals("blaze") || target.equals("enderman") || target.equals("endermite") || target.equals("evoker") || 
					target.equals("magma_cube") || target.equals("phantom") || target.equals("shulker") || target.equals("silverfish") || 
					target.equals("vindicator") || target.equals("pig_zombie") || target.equals("zombie_villager")) {
				min = 8;
				max = 24;
				rewardMultiplier = 1.0;

			} else if (target.equals("ghast") || target.equals("ravager") || target.equals("vex") || target.equals("wither_skeleton")) {
				min = 1;
				max = 4;
				rewardMultiplier = 8.0;

			} else if (target.equals("chicken_jockey") || target.equals("elder_guardian") || target.equals("skeleton_horseman") || target.equals("spider_jockey")) {
				min = 1;
				max = 1;
				rewardMultiplier = 32.0;
				
			} else if (target.equals("ender_dragon") || target.equals("wither")) {
				min = 1;
				max = 1;
				rewardMultiplier = 48.0;
			}

		} else if (task.equals("fish")) {

			List<String> targets = Arrays.asList("cod", "salmon", "tropical_fish", "pufferfish", "bow", "enchanted_book", "fishing_rod", "name_tag", "nautilus_shell", "saddle", 
					"lily_pad", "bamboo", "cocoa_beans", "bowl", "leather", "leather_boots", "rotten_flesh", "stick", "string", "water_bottle", 
					"bone", "ink_sac", "tripwire_hook");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("cod")) {
				min = 16;
				max = 64;
				rewardMultiplier = 0.6;

			} else if (target.equals("salmon")) {
				min = 12;
				max = 32;
				rewardMultiplier = 1.0;

			} else if (target.equals("pufferfish")) {
				min = 8;
				max = 16;
				rewardMultiplier = 1.5;

			} else if (target.equals("tropical_fish") || target.equals("bow") || target.equals("enchanted_book") || target.equals("name_tag") || 
					target.equals("nautilus_shell") || target.equals("saddle") || target.equals("lily_pad") || target.equals("bamboo") || 
					target.equals("cocoa_beans") || target.equals("bowl") || target.equals("leather") || target.equals("leather_boots") || 
					target.equals("rotten_flesh") || target.equals("water_bottle") || target.equals("bone") || target.equals("tripwire_hook")) {
				min = 4;
				max = 8;
				rewardMultiplier = 4.0;

			} else if (target.equals("fishing_rod") || target.equals("stick") || target.equals("string") || target.equals("ink_sac")) {
				min = 2;
				max = 4;
				rewardMultiplier = 8.0;
			}

		} else if (task.equals("craft")) {

		} else if (task.equals("mine")) {

			List<String> targets = Arrays.asList("coal_ore", "iron_ore", "gold_ore", "redstone_ore", "lapis_ore", "diamond_ore", "emerald_ore", "nether_quartz_ore", "stone", "andesite", 
					"diorite", "granite", "spruce_leaves", "birch_leaves", "jungle_leaves", "acacia_leaves", "dark_oak_leaves", "dirt", "grass_block", "gravel", 
					"podzol", "sand", "sandstone", "ice", "packed_ice", "snow_block", "end_stone", "glowstone", "netherrack", "soul_sand", 
					"clay", "cobweb", "obsidian", "dandelion", "poppy", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", 
					"white_tulip", "pink_tulip", "oxeye_daisy", "cornflower", "lily_of_the_valley", "wither_rose", "sunflower", "lilac", "rose_bush", "peony", 
					"vine", "sugar_cane", "sweet_berry_bush", "dead_bush");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("coal_ore") || target.equals("iron_ore") || target.equals("stone") || target.equals("andesite") || 
					target.equals("diorite") || target.equals("granite") || target.equals("spruce_leaves") || target.equals("birch_leaves") || 
					target.equals("jungle_leaves") || target.equals("acacia_leaves") || target.equals("dark_oak_leaves") || target.equals("dirt") || 
					target.equals("grass_block") || target.equals("gravel") || target.equals("sand") || target.equals("sandstone") || 
					target.equals("end_stone") || target.equals("netherrack") || target.equals("soul_sand") || target.equals("dandelion") || 
					target.equals("poppy") || target.equals("vine") || target.equals("sugar_cane") || target.equals("blue_orchid") || 
					target.equals("allium") || target.equals("azure_bluet") || target.equals("red_tulip") || target.equals("orange_tulip") || 
					target.equals("white_tulip") || target.equals("pink_tulip") || target.equals("oxeye_daisy") || target.equals("cornflower") || 
					target.equals("lily_of_the_valley") || target.equals("lilac") || target.equals("rose_bush") || target.equals("peony")) {
				min = 16;
				max = 64;
				rewardMultiplier = 0.5;

			} else if (target.equals("gold_ore") || target.equals("redstone_ore") || target.equals("lapis_ore") || target.equals("nether_quartz_ore") || 
					target.equals("podzol") || target.equals("ice") || target.equals("snow_block") || target.equals("glowstone") || 
					target.equals("clay") || target.equals("cobweb") || target.equals("obsidian") || target.equals("sweet_berry_bush") || 
					target.equals("dead_bush") || target.equals("packed_ice")) {
				min = 8;
				max = 32;
				rewardMultiplier = 2.0;

			} else if (target.equals("diamond_ore") || target.equals("emerald_ore") || target.equals("wither_rose") || target.equals("sunflower")) {
				min = 4;
				max = 8;
				rewardMultiplier = 8.0;
			}

		} else if (task.equals("breed")) {

			//List<String> targets = Arrays.asList("horse", "donkey", "sheep", "cow", "mushroom_cow", "pig", "chicken", "wolf", "cat", "ocelot", "rabbit", "llama", "turtle", "panda", "fox");
			List<String> targets = Arrays.asList("horse", "donkey", "sheep", "cow", "mushroom_cow", "pig", "chicken", "wolf", "cat", "ocelot", "rabbit", "llama", "panda", "fox");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("sheep") || target.equals("cow") || target.equals("pig") || target.equals("chicken")) {
				min = 8;
				max = 16;
				rewardMultiplier = 2.0;

			} else if (target.equals("horse") || target.equals("donkey") || target.equals("mushroom_cow") || target.equals("wolf") || 
					target.equals("cat") || target.equals("ocelot") || target.equals("rabbit") || target.equals("llama") || 
					target.equals("panda") || target.equals("fox")) {
				min = 4;
				max = 8;
				rewardMultiplier = 4.0;

			/*} else if (target.equals("turtle")) {
				min = 2;
				max = 4;
				rewardMultiplier = 8.0;*/
			}

		} else if (task.equals("smelt")) {

			List<String> targets = Arrays.asList("cooked_porkchop", "beef", "cooked_chicken", "cooked_cod", "cooked_salmon", "baked_potato", "cooked_mutton", "cooked_rabbit", "dried_kelp", "iron_ingot", 
					"gold_ingot", "glass", "stone", "smooth_sandstone", "smooth_red_sandstone", "smooth_stone", "smooth_quartz", "brick", "nether_brick", "terracotta", 
					"cracked_stone_bricks", "white_glazed_terracotta", "orange_glazed_terracotta", "magenta_glazed_terracotta", "light_blue_glazed_terracotta", "yellow_glazed_terracotta", "lime_glazed_terracotta", "pink_glazed_terracotta", "gray_glazed_terracotta", "light_gray_glazed_terracotta", 
					"cyan_glazed_terracotta", "purple_glazed_terracotta", "blue_glazed_terracotta", "brown_glazed_terracotta", "green_glazed_terracotta", "red_glazed_terracotta", "black_glazed_terracotta", "iron_nugget", "gold_nugget", "green_dye", 
					"charcoal", "popped_chorus_fruit", "sponge", "lime_dye");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("cooked_porkchop") || target.equals("beef") || target.equals("cooked_chicken") || target.equals("cooked_cod") || 
					target.equals("cooked_salmon") || target.equals("baked_potato") || target.equals("cooked_mutton") || target.equals("cooked_rabbit") || 
					target.equals("dried_kelp") || target.equals("glass") || target.equals("stone") || target.equals("smooth_sandstone") || 
					target.equals("smooth_red_sandstone") || target.equals("smooth_stone") || target.equals("terracotta") || target.equals("cracked_stone_bricks") || 
					target.equals("charcoal")) {
				min = 16;
				max = 64;
				rewardMultiplier = 0.5;

			} else if (target.equals("smooth_quartz") || target.equals("brick") || target.equals("nether_brick") || target.equals("white_glazed_terracotta") || 
					target.equals("orange_glazed_terracotta") || target.equals("magenta_glazed_terracotta") || target.equals("light_blue_glazed_terracotta") || target.equals("yellow_glazed_terracotta") || 
					target.equals("lime_glazed_terracotta") || target.equals("pink_glazed_terracotta") || target.equals("gray_glazed_terracotta") || target.equals("light_gray_glazed_terracotta") || 
					target.equals("cyan_glazed_terracotta") || target.equals("purple_glazed_terracotta") || target.equals("blue_glazed_terracotta") || target.equals("brown_glazed_terracotta") || 
					target.equals("green_glazed_terracotta") || target.equals("red_glazed_terracotta") || target.equals("black_glazed_terracotta") || target.equals("iron_nugget") || 
					target.equals("gold_nugget") || target.equals("green_dye") || target.equals("lime_dye")) {
				min = 8;
				max = 32;
				rewardMultiplier = 0.8;

			} else if (target.equals("iron_ingot") || target.equals("gold_ingot") || target.equals("popped_chorus_fruit") || target.equals("sponge")) {
				min = 8;
				max = 16;
				rewardMultiplier = 1.4;
			}

		} else if (task.equals("enchant")) {

			List<String> targets = Arrays.asList("wooden_sword", "stone_sword", "iron_sword", "diamond_sword", "golden_sword", 
					"wooden_axe", "stone_axe", "iron_axe", "diamond_axe", "golden_axe", 
					"wooden_pickaxe", "stone_pickaxe", "iron_pickaxe", "diamond_pickaxe", "golden_pickaxe", 
					"wooden_shovel", "stone_shovel", "iron_shovel", "diamond_shovel", "golden_shovel", 
					"wooden_hoe", "stone_hoe", "iron_hoe", "diamond_hoe", "golden_hoe", 
					"leather_helmet", "chainmail_helmet", "iron_helmet", "diamond_helmet", "golden_helmet", 
					"leather_chestplate", "chainmail_chestplate", "iron_chestplate", "diamond_chestplate", "golden_chestplate", 
					"leather_leggings", "chainmail_leggings", "iron_leggings", "diamond_leggings", "golden_leggings", 
					"leather_boots", "chainmail_boots", "iron_boots", "diamond_boots", "golden_boots", 
					"book", "shears", "bow", "fishing_rod", "trident", 
					"crossbow", "turtle_helmet");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("wooden_sword") || target.equals("stone_sword") || target.equals("wooden_axe") || target.equals("stone_axe") || 
					target.equals("wooden_pickaxe") || target.equals("stone_pickaxe") || target.equals("wooden_shovel") || target.equals("stone_shovel") || 
					target.equals("wooden_hoe") || target.equals("stone_hoe") || target.equals("leather_helmet") || target.equals("leather_chestplate") || 
					target.equals("leather_leggings") || target.equals("leather_boots") || target.equals("book") || target.equals("shears") || 
					target.equals("bow") || target.equals("fishing_rod")) {
				min = 8;
				max = 16;
				rewardMultiplier = 1.0;

			} else if (target.equals("iron_sword") || target.equals("iron_axe") || target.equals("iron_pickaxe") || target.equals("iron_shovel") || 
					target.equals("iron_hoe") || target.equals("chainmail_helmet") || target.equals("iron_helmet") || target.equals("chainmail_chestplate") || 
					target.equals("iron_chestplate") || target.equals("chainmail_leggings") || target.equals("iron_leggings") || target.equals("chainmail_boots") || 
					target.equals("iron_boots") || target.equals("crossbow")) {
				min = 8;
				max = 16;
				rewardMultiplier = 1.8;

			} else if (target.equals("diamond_sword") || target.equals("golden_sword") || target.equals("diamond_axe") || target.equals("golden_axe") || 
					target.equals("diamond_pickaxe") || target.equals("golden_pickaxe") || target.equals("diamond_shovel") || target.equals("golden_shovel") || 
					target.equals("diamond_hoe") || target.equals("golden_hoe") || target.equals("diamond_helmet") || target.equals("golden_helmet") || 
					target.equals("diamond_chestplate") || target.equals("golden_chestplate") || target.equals("diamond_leggings") || target.equals("golden_leggings") || 
					target.equals("diamond_boots") || target.equals("golden_boots") || target.equals("trident") || target.equals("turtle_helmet")) {
				min = 4;
				max = 8;
				rewardMultiplier = 4.0;
			}

		} else if (task.equals("repair")) {

		} else if (task.equals("trade")) {

		} else if (task.equals("consume")) {

			List<String> targets = Arrays.asList("apple", "baked_potato", "beetroot", "beetroot_soup", "bread", "cake", "carrot", "chorus_fruit", "cooked_chicken", "cooked_cod", 
					"cooked_mutton", "cooked_porkchop", "cooked_rabbit", "cooked_salmon", "cookie", "dried_kelp", "golden_apple", "enchanted_golden_apple", "golden_carrot", "melon_slice", 
					"mushroom_stew", "poisonous_potato", "potato", "pufferfish", "pumpkin_pie", "rabbit_stew", "beef", "chicken", "cod", "mutton", 
					"porkchop", "rabbit", "salmon", "rotten_flesh", "spider_eye", "cooked_beef", "suspicious_stew", "sweet_berries", "tropical_fish");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("apple") || target.equals("beetroot") || target.equals("bread") || target.equals("carrot") || 
					target.equals("cookie") || target.equals("dried_kelp") || target.equals("melon_slice") || target.equals("potato") || 
					target.equals("beef") || target.equals("cod") || target.equals("mutton") || target.equals("porkchop") || 
					target.equals("rabbit") || target.equals("salmon")) {
				min = 12;
				max = 16;
				rewardMultiplier = 1.0;

			} else if (target.equals("baked_potato") || target.equals("beetroot_soup") || target.equals("cooked_chicken") || target.equals("cooked_cod") || 
					target.equals("cooked_mutton") || target.equals("cooked_porkchop") || target.equals("cooked_rabbit") || target.equals("cooked_salmon") || 
					target.equals("mushroom_stew") || target.equals("pumpkin_pie") || target.equals("rabbit_stew") || target.equals("cooked_beef") || 
					target.equals("sweet_berries")) {
				min = 8;
				max = 12;
				rewardMultiplier = 1.8;

			} else if (target.equals("cake") || target.equals("chorus_fruit") || target.equals("golden_carrot") || target.equals("poisonous_potato") || 
					target.equals("pufferfish") || target.equals("chicken") || target.equals("rotten_flesh") || target.equals("spider_eye") || 
					target.equals("suspicious_stew") || target.equals("tropical_fish")) {
				min = 4;
				max = 8;
				rewardMultiplier = 3.0;

			} else if (target.equals("golden_apple") || target.equals("enchanted_golden_apple")) {
				min = 1;
				max = 2;
				rewardMultiplier = 16.0;
			}

		} else if (task.equals("brew")) {

		} else if (task.equals("tame")) {

			List<String> targets = Arrays.asList("donkey", "horse", "mule", "llama", "cat", "parrot", "wolf", "skeleton_horse", "trader_llama");
			target = targets.get(random.nextInt(targets.size()));

			if (target.equals("donkey") || target.equals("horse") || target.equals("mule") || target.equals("llama")) {
				min = 2;
				max = 8;
				rewardMultiplier = 3.0;

			} else if (target.equals("cat") || target.equals("parrot") || target.equals("wolf")) {
				min = 2;
				max = 8;
				rewardMultiplier = 4.0;

			} else if (target.equals("skeleton_horse") || target.equals("trader_llama")) {
				min = 1;
				max = 2;
				rewardMultiplier = 14.0;
			}
		}

		if (min == 1 && max == 1) {
			amount = 1;
		} else {
			amount = random.nextInt(max - min) + min;
		}

		reward = (int) (amount * rewardMultiplier);

		Map<String, Integer> progress = new HashMap<String, Integer>();

		return new Challenge(name, ends, replace, task, target, amount, reward, progress);
	}

	public String resolveChallengeCase(String challengeName) {

		String challenge = null;

		for (String challengeKey : plugin.challenges.keySet()) {
			if (challengeName.toLowerCase().equalsIgnoreCase(challengeKey)) {
				challenge = challengeKey;
			}
		}
		return challenge;
	}

	public boolean challengeExists(String challenge) {
		return plugin.challenges.containsKey(challenge);
	}

	public void createChallenge(String challenge, long ends, boolean replace, String task, String target, int amount, int reward) {

		plugin.getConfig().set("challenges." + challenge, "");
		plugin.getConfig().set("challenges." + challenge + ".ends", ends);
		plugin.getConfig().set("challenges." + challenge + ".replace", replace);
		plugin.getConfig().set("challenges." + challenge + ".task", task);
		plugin.getConfig().set("challenges." + challenge + ".target", target);
		plugin.getConfig().set("challenges." + challenge + ".amount", amount);
		plugin.getConfig().set("challenges." + challenge + ".reward", reward);
		plugin.getConfig().set("challenges." + challenge + ".progress", Collections.emptyMap());
		plugin.saveConfig();

		plugin.challenges.put(challenge, new Challenge(challenge, ends, replace, task, target, amount, reward, new HashMap<String, Integer>()));
	}

	public void deleteChallenge(String challenge) {

		challenge = resolveChallengeCase(challenge);

		plugin.getConfig().set("challenges." + challenge, null);
		plugin.saveConfig();

		plugin.challenges.remove(challenge);
	}
}
