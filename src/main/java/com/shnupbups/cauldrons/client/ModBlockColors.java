package com.shnupbups.cauldrons.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import net.minecraft.client.color.block.BlockColorProvider;

import com.shnupbups.cauldrons.block.entity.DyedWaterCauldronBlockEntity;
import com.shnupbups.cauldrons.registry.ModBlocks;

public class ModBlockColors {
	public static void init() {
		ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
			if(world != null && pos != null && world.getBlockEntity(pos) != null) {
				return ((DyedWaterCauldronBlockEntity)world.getBlockEntity(pos)).getColor();
			}
			return -1;
		}), ModBlocks.DYED_WATER_CAULDRON);
	}
}
