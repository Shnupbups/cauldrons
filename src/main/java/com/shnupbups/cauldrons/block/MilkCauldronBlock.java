package com.shnupbups.cauldrons.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class MilkCauldronBlock extends AbstractCauldronBlock {

	public MilkCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	protected double getFluidHeight(BlockState state) {
		return 0.9375D;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			if(livingEntity.clearStatusEffects() && (!(livingEntity instanceof PlayerEntity) || !((PlayerEntity)livingEntity).isCreative())) {
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
			}
		}
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return 1;
	}
}
