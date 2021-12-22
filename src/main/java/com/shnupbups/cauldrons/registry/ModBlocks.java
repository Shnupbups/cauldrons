package com.shnupbups.cauldrons.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.shnupbups.cauldronlib.block.ThreeLeveledCauldronBlock;
import com.shnupbups.cauldrons.Cauldrons;
import com.shnupbups.cauldrons.block.BeetrootSoupCauldronBlock;
import com.shnupbups.cauldrons.block.DragonBreathCauldronBlock;
import com.shnupbups.cauldrons.block.ExperienceCauldronBlock;
import com.shnupbups.cauldrons.block.HoneyCauldronBlock;
import com.shnupbups.cauldrons.block.MilkCauldronBlock;
import com.shnupbups.cauldrons.block.MushroomStewCauldronBlock;
import com.shnupbups.cauldrons.block.SuspiciousStewCauldronBlock;

public class ModBlocks {
	public static Block MILK_CAULDRON = new MilkCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.MILK_CAULDRON_BEHAVIOR);
	public static Block HONEY_CAULDRON = new HoneyCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.HONEY_CAULDRON_BEHAVIOR);
	public static Block DRAGON_BREATH_CAULDRON = new DragonBreathCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.DRAGON_BREATH_CAULDRON_BEHAVIOR);
	public static Block EXPERIENCE_CAULDRON = new ExperienceCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.EXPERIENCE_CAULDRON_BEHAVIOR);
	public static Block MUSHROOM_STEW_CAULDRON = new MushroomStewCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.MUSHROOM_STEW_CAULDRON_BEHAVIOR);
	public static Block RABBIT_STEW_CAULDRON = new ThreeLeveledCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.RABBIT_STEW_CAULDRON_BEHAVIOR);
	public static Block BEETROOT_SOUP_CAULDRON = new BeetrootSoupCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.BEETROOT_SOUP_CAULDRON_BEHAVIOR);
	public static Block SUSPICIOUS_STEW_CAULDRON = new SuspiciousStewCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON), ModCauldronBehavior.SUSPICIOUS_STEW_CAULDRON_BEHAVIOR);

	public static void init() {
		registerCauldron("milk_cauldron", MILK_CAULDRON);
		registerCauldron("honey_cauldron", HONEY_CAULDRON);
		registerCauldron("dragon_breath_cauldron", DRAGON_BREATH_CAULDRON);
		registerCauldron("experience_cauldron", EXPERIENCE_CAULDRON);
		registerCauldron("mushroom_stew_cauldron", MUSHROOM_STEW_CAULDRON);
		registerCauldron("rabbit_stew_cauldron", RABBIT_STEW_CAULDRON);
		registerCauldron("beetroot_soup_cauldron", BEETROOT_SOUP_CAULDRON);
		registerCauldron("suspicious_stew_cauldron", SUSPICIOUS_STEW_CAULDRON);
	}

	public static void register(String id, Block block) {
		Registry.register(Registry.BLOCK, Cauldrons.id(id), block);
	}

	public static void registerCauldron(String id, Block cauldron) {
		register(id, cauldron);
		Item.BLOCK_ITEMS.put(cauldron, Items.CAULDRON);
	}
}