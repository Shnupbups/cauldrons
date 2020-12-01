package com.shnupbups.cauldrons;

import net.fabricmc.api.ClientModInitializer;

import com.shnupbups.cauldrons.client.ModBlockColors;

public class CauldronsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModBlockColors.init();
	}
}
