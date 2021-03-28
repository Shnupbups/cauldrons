package com.shnupbups.cauldrons.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class ExperienceCauldronBlock extends AbstractLeveledCauldronBlock {
	public static final IntProperty LEVEL = IntProperty.of("level", 1, 30);;

	public ExperienceCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public IntProperty getLevelProperty() {
		return LEVEL;
	}

	@Override
	public int getMaxLevel() {
		return 30;
	}

	@Override
	protected double getFluidHeight(BlockState state) {
		return (6.0D + ((double)state.get(LEVEL)/10) * 3.0D) / 16.0D;
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return Math.max(1, state.get(LEVEL)/2);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entity;
			if(!player.isSpectator()) {
				player.addExperience(1);
				if(!player.isCreative())
					decrementFluidLevel(state, world, pos, 1);
			}
		}
	}
}
