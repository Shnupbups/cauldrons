package com.shnupbups.cauldrons.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import com.shnupbups.cauldrons.Cauldrons;
import com.shnupbups.cauldrons.block.entity.SuspiciousStewCauldronBlockEntity;

public class ModBlockEntityTypes {
	public static final BlockEntityType<SuspiciousStewCauldronBlockEntity> SUSPICIOUS_STEW_CAULDRON = FabricBlockEntityTypeBuilder.create(SuspiciousStewCauldronBlockEntity::new, ModBlocks.SUSPICIOUS_STEW_CAULDRON).build(null);

	public static void init() {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, Cauldrons.id("suspicious_stew_cauldron"), SUSPICIOUS_STEW_CAULDRON);
	}
}
