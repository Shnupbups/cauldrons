package com.shnupbups.cauldrons.block;

import java.util.Map;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;

import com.shnupbups.cauldronlib.block.AbstractLeveledCauldronBlock;

public class BeetrootSoupCauldronBlock extends AbstractLeveledCauldronBlock {
	public static final IntProperty LEVEL = IntProperty.of("level", 1, 18);

	public BeetrootSoupCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public IntProperty getLevelProperty() {
		return LEVEL;
	}

	@Override
	public int getMaxLevel() {
		return 18;
	}
}
