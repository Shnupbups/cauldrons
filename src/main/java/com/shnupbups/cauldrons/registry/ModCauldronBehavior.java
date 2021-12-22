package com.shnupbups.cauldrons.registry;

import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;

import com.shnupbups.cauldronlib.CauldronLib;
import com.shnupbups.cauldrons.block.BeetrootSoupCauldronBlock;
import com.shnupbups.cauldrons.block.ExperienceCauldronBlock;
import com.shnupbups.cauldrons.block.MushroomStewCauldronBlock;
import com.shnupbups.cauldrons.block.entity.SuspiciousStewCauldronBlockEntity;

public interface ModCauldronBehavior extends CauldronBehavior {
	Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> HONEY_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> DRAGON_BREATH_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> EXPERIENCE_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> MUSHROOM_STEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> RABBIT_STEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> BEETROOT_SOUP_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> SUSPICIOUS_STEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();

	CauldronBehavior ADD_MUSHROOM_TO_EMPTY = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient) {
			Item item = stack.getItem();
			if (!player.isCreative()) stack.decrement(1);
			player.incrementStat(Stats.USE_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			world.setBlockState(pos, ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState());
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
		}

		return ActionResult.success(world.isClient);
	};
	CauldronBehavior ADD_MUSHROOM_TO_STEW = (state, world, pos, player, hand, stack) -> {
		if (CauldronLib.canIncrementFluidLevel(state)) {
			if (!world.isClient) {
				Item item = stack.getItem();
				if (!player.isCreative()) stack.decrement(1);
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				CauldronLib.incrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		} else {
			return ActionResult.PASS;
		}
	};

	static void init() {
		// Register fill from bucket behaviors
		CauldronLib.registerFillFromBucketBehavior(Items.MILK_BUCKET, ModBlocks.MILK_CAULDRON);

		// Register empty cauldron behaviors
		EMPTY_CAULDRON_BEHAVIOR.put(Items.HONEY_BOTTLE, CauldronLib.createFillFromBottleBehavior(ModBlocks.HONEY_CAULDRON));
		EMPTY_CAULDRON_BEHAVIOR.put(Items.DRAGON_BREATH, CauldronLib.createFillFromBottleBehavior(ModBlocks.DRAGON_BREATH_CAULDRON));
		EMPTY_CAULDRON_BEHAVIOR.put(Items.EXPERIENCE_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, ModBlocks.EXPERIENCE_CAULDRON.getDefaultState().with(ExperienceCauldronBlock.LEVEL, world.getRandom().nextInt(8) + 3));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.MUSHROOM_STEW, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, 2));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.BROWN_MUSHROOM, ADD_MUSHROOM_TO_EMPTY);
		EMPTY_CAULDRON_BEHAVIOR.put(Items.RED_MUSHROOM, ADD_MUSHROOM_TO_EMPTY);
		EMPTY_CAULDRON_BEHAVIOR.put(Items.RABBIT_STEW, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, ModBlocks.RABBIT_STEW_CAULDRON.getDefaultState());
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.BEETROOT_SOUP, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, ModBlocks.BEETROOT_SOUP_CAULDRON.getDefaultState().with(BeetrootSoupCauldronBlock.LEVEL, 6));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.BEETROOT, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				if (!player.isCreative()) stack.decrement(1);
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, ModBlocks.BEETROOT_SOUP_CAULDRON.getDefaultState());
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.SUSPICIOUS_STEW, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, ModBlocks.SUSPICIOUS_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, 2));
				if (world.getBlockEntity(pos) instanceof SuspiciousStewCauldronBlockEntity blockEntity) {
					blockEntity.addEffectsFromStew(stack);
				}
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});

		// Register milk cauldron behaviors
		MILK_CAULDRON_BEHAVIOR.put(Items.BUCKET, CauldronLib.createEmptyIntoBucketBehavior(Items.MILK_BUCKET));

		// Register honey cauldron behaviors
		HONEY_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.HONEY_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				CauldronLib.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		HONEY_CAULDRON_BEHAVIOR.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});

		// Register dragon breath cauldron behaviors
		DRAGON_BREATH_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.DRAGON_BREATH)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				CauldronLib.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		DRAGON_BREATH_CAULDRON_BEHAVIOR.put(Items.DRAGON_BREATH, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		DRAGON_BREATH_CAULDRON_BEHAVIOR.put(Items.SPLASH_POTION, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, PotionUtil.setPotion(new ItemStack(Items.LINGERING_POTION), PotionUtil.getPotion(stack))));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				CauldronLib.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});

		// Register experience cauldron behaviors
		EXPERIENCE_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canDecrementFluidLevel(state, 10)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.EXPERIENCE_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.decrementFluidLevel(state, world, pos, 10);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		EXPERIENCE_CAULDRON_BEHAVIOR.put(Items.EXPERIENCE_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state, 3)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos, false, world.getRandom().nextInt(8) + 3);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});

		// Register mushroom stew cauldron behaviors
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canDecrementFluidLevel(state, 2)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.MUSHROOM_STEW)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.decrementFluidLevel(state, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.MUSHROOM_STEW, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state, 2)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.BROWN_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.RED_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.SUSPICIOUS_STEW, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state, 2)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					BlockState newState = ModBlocks.SUSPICIOUS_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, CauldronLib.getFluidLevel(state));
					world.setBlockState(pos, newState);
					CauldronLib.incrementFluidLevel(newState, world, pos, 2);
					if (world.getBlockEntity(pos) instanceof SuspiciousStewCauldronBlockEntity blockEntity) {
						blockEntity.addEffectsFromStew(stack);
					}
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});

		// Register rabbit stew cauldron behaviors
		RABBIT_STEW_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.RABBIT_STEW)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				CauldronLib.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return ActionResult.success(world.isClient);
		});
		RABBIT_STEW_CAULDRON_BEHAVIOR.put(Items.RABBIT_STEW, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});

		// Register beetroot soup cauldron behaviors
		BEETROOT_SOUP_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canDecrementFluidLevel(state, 6)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BEETROOT_SOUP)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.decrementFluidLevel(state, world, pos, 6);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		BEETROOT_SOUP_CAULDRON_BEHAVIOR.put(Items.BEETROOT_SOUP, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state, 6)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos, 6);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		BEETROOT_SOUP_CAULDRON_BEHAVIOR.put(Items.BEETROOT, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					if (!player.isCreative()) stack.decrement(1);
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos);
					world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});

		// Register suspicious stew cauldron behaviors
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canDecrementFluidLevel(state, 2)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
					if (world.getBlockEntity(pos) instanceof SuspiciousStewCauldronBlockEntity blockEntity) {
						blockEntity.addEffectsToStew(stew);
					}
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, stew));
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					BlockState newState = ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, CauldronLib.getFluidLevel(state));
					world.setBlockState(pos, newState);
					CauldronLib.decrementFluidLevel(newState, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.SUSPICIOUS_STEW, (state, world, pos, player, hand, stack) -> {
			if (CauldronLib.canIncrementFluidLevel(state, 2)) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BOWL)));
					if (world.getBlockEntity(pos) instanceof SuspiciousStewCauldronBlockEntity blockEntity) {
						blockEntity.addEffectsFromStew(stack);
					}
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					CauldronLib.incrementFluidLevel(state, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.BROWN_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.RED_MUSHROOM, ADD_MUSHROOM_TO_STEW);
	}
}
