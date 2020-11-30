package com.shnupbups.cauldrons;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

public class Cauldrons implements ModInitializer {
	public static final String MOD_ID = "cauldrons";
	
	@Override
	public void onInitialize() {
		ModCauldronBehavior.registerBehavior();
		ModBlocks.init();
	}
	
	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}
}
