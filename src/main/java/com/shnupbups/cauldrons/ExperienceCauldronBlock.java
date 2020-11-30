package com.shnupbups.cauldrons;

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

public class ExperienceCauldronBlock extends AbstractCauldronBlock {
	public static final IntProperty LEVEL = IntProperty.of("level", 1, 30);;

	public ExperienceCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
	}

	protected double getFluidHeight(BlockState state) {
		return (6.0D + ((double)state.get(LEVEL)/10) * 3.0D) / 16.0D;
	}

	public static void decrementFluidLevel(BlockState state, World world, BlockPos pos, int amount) {
		int i = state.get(LEVEL) - amount;
		world.setBlockState(pos, i <= 0 ? Blocks.CAULDRON.getDefaultState() : state.with(LEVEL, i));
	}

	public static void incrementFluidLevel(BlockState state, World world, BlockPos pos, int amount) {
		int i = Math.min(state.get(LEVEL) + amount, 30);
		world.setBlockState(pos, state.with(LEVEL, i));
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return Math.max(1, state.get(LEVEL)/2);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (this.isEntityTouchingFluid(state, pos, entity) && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entity;
			player.addExperience(1);
			decrementFluidLevel(state, world, pos, 1);
		}
	}
}
