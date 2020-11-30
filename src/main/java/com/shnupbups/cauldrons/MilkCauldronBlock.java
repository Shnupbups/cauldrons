package com.shnupbups.cauldrons;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class MilkCauldronBlock extends AbstractCauldronBlock {

	public MilkCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	protected double getFluidHeight(BlockState state) {
		return 0.9375D;
	}

	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity)entity;
			if(living.clearStatusEffects()) {
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
			}
		}
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return 1;
	}
}
