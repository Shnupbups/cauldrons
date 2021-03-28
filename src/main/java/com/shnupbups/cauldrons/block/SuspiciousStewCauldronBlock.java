package com.shnupbups.cauldrons.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.cauldrons.block.entity.SuspiciousStewCauldronBlockEntity;
import com.shnupbups.cauldrons.registry.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SuspiciousStewCauldronBlock extends MushroomStewCauldronBlock implements BlockEntityProvider {
	public SuspiciousStewCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SuspiciousStewCauldronBlockEntity(pos, state);
	}

	@Override
	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			if(!livingEntity.isSpectator()) {
				SuspiciousStewCauldronBlockEntity blockEntity = (SuspiciousStewCauldronBlockEntity) world.getBlockEntity(pos);
				blockEntity.getEffects().forEach(livingEntity::addStatusEffect);
				if(!(livingEntity instanceof PlayerEntity) || !((PlayerEntity)livingEntity).isCreative()) {
					BlockState newState = ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, state.get(MushroomStewCauldronBlock.LEVEL));
					world.setBlockState(pos, newState);
				}
			}
		}
	}
}
