package com.shnupbups.cauldrons;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class ModThreeLeveledCauldronBlock extends AbstractCauldronBlock {
	public static final IntProperty LEVEL = Properties.LEVEL_3;

	public ModThreeLeveledCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
	}

	protected double getFluidHeight(BlockState state) {
		return (6.0D + (double)state.get(LEVEL) * 3.0D) / 16.0D;
	}

	public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
		int i = state.get(LEVEL) - 1;
		world.setBlockState(pos, i == 0 ? Blocks.CAULDRON.getDefaultState() : state.with(LEVEL, i));
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(LEVEL);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}
}
