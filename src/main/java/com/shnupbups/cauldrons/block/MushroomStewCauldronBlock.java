package com.shnupbups.cauldrons.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.cauldrons.registry.ModBlocks;
import com.shnupbups.cauldrons.block.entity.SuspiciousStewCauldronBlockEntity;

import java.util.Map;

public class MushroomStewCauldronBlock extends AbstractLeveledCauldronBlock {
	public static final IntProperty LEVEL = IntProperty.of("level", 1, 6);

	public MushroomStewCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
		super(settings, behaviorMap);
	}

	@Override
	public IntProperty getLevelProperty() {
		return LEVEL;
	}

	@Override
	public int getMaxLevel() {
		return 6;
	}

	@Override
	protected double getFluidHeight(BlockState state) {
		return (6.0D + (double)state.get(LEVEL) * 1.5D) / 16.0D;
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(LEVEL);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		if(stack.isIn(ItemTags.SMALL_FLOWERS) && stack.getItem() instanceof BlockItem) {
			BlockItem blockItem = (BlockItem)stack.getItem();
			if(blockItem.getBlock() instanceof FlowerBlock) {
				if(!world.getBlockState(pos).isOf(ModBlocks.SUSPICIOUS_STEW_CAULDRON)) {
					world.setBlockState(pos, ModBlocks.SUSPICIOUS_STEW_CAULDRON.getDefaultState().with(LEVEL, state.get(LEVEL)));
				}
				if(world.getBlockEntity(pos) != null) {
					if(((SuspiciousStewCauldronBlockEntity)world.getBlockEntity(pos)).addEffect((FlowerBlock)blockItem.getBlock())) {
						stack.decrement(1);
						world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
						return ActionResult.success(world.isClient);
					}
				} else {
					System.out.println("oh gosh oh heck");
				}
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
