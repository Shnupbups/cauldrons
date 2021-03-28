package com.shnupbups.cauldrons.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class HoneyCauldronBlock extends ThreeLeveledCauldronBlock {
	public HoneyCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			if(livingEntity.removeStatusEffect(StatusEffects.POISON) && (!(livingEntity instanceof PlayerEntity) || !((PlayerEntity)livingEntity).isCreative())) {
				decrementFluidLevel(state, world, pos);
			}
		}
	}
}
