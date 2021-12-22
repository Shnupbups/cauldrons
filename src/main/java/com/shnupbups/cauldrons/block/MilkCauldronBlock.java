package com.shnupbups.cauldrons.block;

import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.cauldronlib.block.FullCauldronBlock;

public class MilkCauldronBlock extends FullCauldronBlock {
	public MilkCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity livingEntity) {
			if (livingEntity.clearStatusEffects() && (!(livingEntity instanceof PlayerEntity) || !((PlayerEntity) livingEntity).isCreative())) {
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
			}
		}
	}
}
