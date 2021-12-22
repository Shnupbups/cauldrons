package com.shnupbups.cauldrons.block;

import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.cauldronlib.block.ThreeLeveledCauldronBlock;

public class DragonBreathCauldronBlock extends ThreeLeveledCauldronBlock {
	public DragonBreathCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity)) {
			entity.damage(DamageSource.DRAGON_BREATH, 2.5f);
		}
	}
}
