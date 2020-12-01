package com.shnupbups.cauldrons.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class BeetrootSoupCauldronBlock extends AbstractCauldronBlock {
	public static final IntProperty LEVEL = IntProperty.of("level", 1, 18);

	public BeetrootSoupCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
	}

	@Override
	protected double getFluidHeight(BlockState state) {
		return (6.0D + (double)state.get(LEVEL) * 0.5D) / 16.0D;
	}

	public static void decrementFluidLevel(BlockState state, World world, BlockPos pos, int amount) {
		int i = state.get(LEVEL) - amount;
		world.setBlockState(pos, i <= 0 ? Blocks.CAULDRON.getDefaultState() : state.with(LEVEL, i));
	}

	public static void incrementFluidLevel(BlockState state, World world, BlockPos pos, int amount) {
		int i = Math.min(state.get(LEVEL) + amount, 18);
		world.setBlockState(pos, state.with(LEVEL, i));
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(LEVEL)/2;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}
}
