package com.shnupbups.cauldrons;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.shnupbups.cauldrons.registry.ModBlockEntityTypes;
import com.shnupbups.cauldrons.registry.ModBlocks;
import com.shnupbups.cauldrons.registry.ModCauldronBehavior;

public class Cauldrons implements ModInitializer {
	public static final String MOD_ID = "cauldrons";

	@Override
	public void onInitialize() {
		ModCauldronBehavior.init();
		ModBlocks.init();
		ModBlockEntityTypes.init();
	}

	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}
}
