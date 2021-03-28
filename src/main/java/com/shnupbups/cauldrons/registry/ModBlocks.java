package com.shnupbups.cauldrons.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import com.shnupbups.cauldrons.Cauldrons;
import com.shnupbups.cauldrons.block.BeetrootSoupCauldronBlock;
import com.shnupbups.cauldrons.block.DragonBreathCauldronBlock;
import com.shnupbups.cauldrons.block.ExperienceCauldronBlock;
import com.shnupbups.cauldrons.block.HoneyCauldronBlock;
import com.shnupbups.cauldrons.block.MilkCauldronBlock;
import com.shnupbups.cauldrons.block.ThreeLeveledCauldronBlock;
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
		Registry.register(Registry.BLOCK, Cauldrons.id("milk_cauldron"), MILK_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("honey_cauldron"), HONEY_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("dragon_breath_cauldron"), DRAGON_BREATH_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("experience_cauldron"), EXPERIENCE_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("mushroom_stew_cauldron"), MUSHROOM_STEW_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("rabbit_stew_cauldron"), RABBIT_STEW_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("beetroot_soup_cauldron"), BEETROOT_SOUP_CAULDRON);
		Registry.register(Registry.BLOCK, Cauldrons.id("suspicious_stew_cauldron"), SUSPICIOUS_STEW_CAULDRON);
	}
}