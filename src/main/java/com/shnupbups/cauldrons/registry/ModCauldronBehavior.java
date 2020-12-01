package com.shnupbups.cauldrons.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;

import com.shnupbups.cauldrons.block.BeetrootSoupCauldronBlock;
import com.shnupbups.cauldrons.block.DyedWaterCauldronBlock;
import com.shnupbups.cauldrons.block.ExperienceCauldronBlock;
import com.shnupbups.cauldrons.block.ModThreeLeveledCauldronBlock;
import com.shnupbups.cauldrons.block.MushroomStewCauldronBlock;
import com.shnupbups.cauldrons.block.entity.DyedWaterCauldronBlockEntity;
import com.shnupbups.cauldrons.block.entity.SuspiciousStewCauldronBlockEntity;

import java.util.Map;

public interface ModCauldronBehavior extends CauldronBehavior {
	Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> HONEY_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> DRAGON_BREATH_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> EXPERIENCE_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> MUSHROOM_STEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> RABBIT_STEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> BEETROOT_SOUP_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> SUSPICIOUS_STEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	Map<Item, CauldronBehavior> DYED_WATER_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();

	CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> {
		return CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.MILK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
	};
	CauldronBehavior ADD_MUSHROOM_TO_EMPTY = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient) {
			stack.decrement(1);
			player.incrementStat(Stats.USE_CAULDRON);
			world.setBlockState(pos, ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState());
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}

		return ActionResult.success(world.isClient);
	};
	CauldronBehavior ADD_MUSHROOM_TO_STEW = (state, world, pos, player, hand, stack) -> {
		if (state.get(MushroomStewCauldronBlock.LEVEL) != 6) {
			if (!world.isClient) {
				stack.decrement(1);
				player.incrementStat(Stats.USE_CAULDRON);
				MushroomStewCauldronBlock.incrementFluidLevel(state, world, pos, 1);
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		} else {
			return ActionResult.PASS;
		}
	};
	CauldronBehavior ADD_DYE_TO_WATER = (state, world, pos, player, hand, stack) -> {
		Item item = stack.getItem();
		if (item instanceof DyeItem) {
			DyeItem dyeItem = (DyeItem) item;
			if (!state.isOf(ModBlocks.DYED_WATER_CAULDRON)) {
				world.setBlockState(pos, ModBlocks.DYED_WATER_CAULDRON.getDefaultState().with(ModThreeLeveledCauldronBlock.LEVEL, state.get(LeveledCauldronBlock.LEVEL)));
			}
			if (world.getBlockEntity(pos) != null) {
				boolean added = ((DyedWaterCauldronBlockEntity) world.getBlockEntity(pos)).addColor(dyeItem.getColor().getFireworkColor());
				if (added) {
					stack.decrement(1);
					world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return ActionResult.success(world.isClient);
				}
			}
		}
		return ActionResult.PASS;
	};

	static void init() {
		EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
		EMPTY_CAULDRON_BEHAVIOR.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.HONEY_CAULDRON.getDefaultState());
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.DRAGON_BREATH, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.DRAGON_BREATH_CAULDRON.getDefaultState());
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.EXPERIENCE_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.EXPERIENCE_CAULDRON.getDefaultState().with(ExperienceCauldronBlock.LEVEL, world.getRandom().nextInt(8)+3));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.MUSHROOM_STEW, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, 2));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.BROWN_MUSHROOM, ADD_MUSHROOM_TO_EMPTY);
		EMPTY_CAULDRON_BEHAVIOR.put(Items.RED_MUSHROOM, ADD_MUSHROOM_TO_EMPTY);
		EMPTY_CAULDRON_BEHAVIOR.put(Items.RABBIT_STEW, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.RABBIT_STEW_CAULDRON.getDefaultState());
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.BEETROOT_SOUP, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.BEETROOT_SOUP_CAULDRON.getDefaultState().with(BeetrootSoupCauldronBlock.LEVEL, 6));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.BEETROOT, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				stack.decrement(1);
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.BEETROOT_SOUP_CAULDRON.getDefaultState());
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		EMPTY_CAULDRON_BEHAVIOR.put(Items.SUSPICIOUS_STEW, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
				player.incrementStat(Stats.USE_CAULDRON);
				world.setBlockState(pos, ModBlocks.SUSPICIOUS_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, 2));
				if(world.getBlockEntity(pos) != null) {
					((SuspiciousStewCauldronBlockEntity)world.getBlockEntity(pos)).addEffectsFromStew(stack);
				}
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});

		WATER_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
		WATER_CAULDRON_BEHAVIOR.put(Items.WHITE_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.ORANGE_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.MAGENTA_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_BLUE_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.YELLOW_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.LIME_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.PINK_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.GRAY_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_GRAY_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.CYAN_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.PURPLE_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.BLUE_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.BROWN_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.GREEN_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.RED_DYE, ADD_DYE_TO_WATER);
		WATER_CAULDRON_BEHAVIOR.put(Items.BLACK_DYE, ADD_DYE_TO_WATER);

		LAVA_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);

		POWDER_SNOW_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);

		MILK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
			return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), (statex) -> {
				return true;
			}, SoundEvents.ITEM_BUCKET_FILL);
		});
		MILK_CAULDRON_BEHAVIOR.put(Items.WATER_BUCKET, FILL_WITH_WATER);
		MILK_CAULDRON_BEHAVIOR.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
		MILK_CAULDRON_BEHAVIOR.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);

		HONEY_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.HONEY_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				ModThreeLeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		HONEY_CAULDRON_BEHAVIOR.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (state.get(ModThreeLeveledCauldronBlock.LEVEL) != 3) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					world.setBlockState(pos, state.cycle(ModThreeLeveledCauldronBlock.LEVEL));
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		addStandardBehaviors(HONEY_CAULDRON_BEHAVIOR);

		DRAGON_BREATH_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.DRAGON_BREATH)));
				player.incrementStat(Stats.USE_CAULDRON);
				ModThreeLeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		DRAGON_BREATH_CAULDRON_BEHAVIOR.put(Items.DRAGON_BREATH, (state, world, pos, player, hand, stack) -> {
			if (state.get(ModThreeLeveledCauldronBlock.LEVEL) != 3) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					world.setBlockState(pos, state.cycle(ModThreeLeveledCauldronBlock.LEVEL));
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		DRAGON_BREATH_CAULDRON_BEHAVIOR.put(Items.SPLASH_POTION, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, PotionUtil.setPotion(new ItemStack(Items.LINGERING_POTION), PotionUtil.getPotion(stack))));
				player.incrementStat(Stats.USE_CAULDRON);
				ModThreeLeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		addStandardBehaviors(DRAGON_BREATH_CAULDRON_BEHAVIOR);

		EXPERIENCE_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (state.get(ExperienceCauldronBlock.LEVEL) >= 10) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.EXPERIENCE_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					ExperienceCauldronBlock.decrementFluidLevel(state, world, pos, 10);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		EXPERIENCE_CAULDRON_BEHAVIOR.put(Items.EXPERIENCE_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (state.get(ExperienceCauldronBlock.LEVEL) != 30) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					ExperienceCauldronBlock.incrementFluidLevel(state, world, pos, world.getRandom().nextInt(8)+3);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		addStandardBehaviors(EXPERIENCE_CAULDRON_BEHAVIOR);

		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (state.get(MushroomStewCauldronBlock.LEVEL) >= 2) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.MUSHROOM_STEW)));
					player.incrementStat(Stats.USE_CAULDRON);
					MushroomStewCauldronBlock.decrementFluidLevel(state, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.MUSHROOM_STEW, (state, world, pos, player, hand, stack) -> {
			if (state.get(MushroomStewCauldronBlock.LEVEL) != 6) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					MushroomStewCauldronBlock.incrementFluidLevel(state, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.BROWN_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.RED_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		MUSHROOM_STEW_CAULDRON_BEHAVIOR.put(Items.SUSPICIOUS_STEW, (state, world, pos, player, hand, stack) -> {
			if (state.get(MushroomStewCauldronBlock.LEVEL) != 6) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					BlockState newState = ModBlocks.SUSPICIOUS_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, state.get(MushroomStewCauldronBlock.LEVEL));
					world.setBlockState(pos, newState);
					MushroomStewCauldronBlock.incrementFluidLevel(newState, world, pos, 2);
					if(world.getBlockEntity(pos) != null) {
						((SuspiciousStewCauldronBlockEntity)world.getBlockEntity(pos)).addEffectsFromStew(stack);
					}
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		addStandardBehaviors(MUSHROOM_STEW_CAULDRON_BEHAVIOR);

		RABBIT_STEW_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.RABBIT_STEW)));
				player.incrementStat(Stats.USE_CAULDRON);
				ModThreeLeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		RABBIT_STEW_CAULDRON_BEHAVIOR.put(Items.RABBIT_STEW, (state, world, pos, player, hand, stack) -> {
			if (state.get(ModThreeLeveledCauldronBlock.LEVEL) != 3) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					world.setBlockState(pos, state.cycle(ModThreeLeveledCauldronBlock.LEVEL));
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		addStandardBehaviors(RABBIT_STEW_CAULDRON_BEHAVIOR);

		BEETROOT_SOUP_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (state.get(BeetrootSoupCauldronBlock.LEVEL) >= 6) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BEETROOT_SOUP)));
					player.incrementStat(Stats.USE_CAULDRON);
					BeetrootSoupCauldronBlock.decrementFluidLevel(state, world, pos, 6);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		BEETROOT_SOUP_CAULDRON_BEHAVIOR.put(Items.BEETROOT_SOUP, (state, world, pos, player, hand, stack) -> {
			if (state.get(BeetrootSoupCauldronBlock.LEVEL) != 18) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
					player.incrementStat(Stats.USE_CAULDRON);
					BeetrootSoupCauldronBlock.incrementFluidLevel(state, world, pos, 6);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		BEETROOT_SOUP_CAULDRON_BEHAVIOR.put(Items.BEETROOT, (state, world, pos, player, hand, stack) -> {
			if (state.get(BeetrootSoupCauldronBlock.LEVEL) != 18) {
				if (!world.isClient) {
					stack.decrement(1);
					player.incrementStat(Stats.USE_CAULDRON);
					BeetrootSoupCauldronBlock.incrementFluidLevel(state, world, pos, 1);
					world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		addStandardBehaviors(BEETROOT_SOUP_CAULDRON_BEHAVIOR);

		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.BOWL, (state, world, pos, player, hand, stack) -> {
			if (state.get(MushroomStewCauldronBlock.LEVEL) >= 2) {
				if (!world.isClient) {
					ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
					if(world.getBlockEntity(pos) != null) {
						((SuspiciousStewCauldronBlockEntity)world.getBlockEntity(pos)).addEffectsToStew(stew);
					}
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, stew));
					player.incrementStat(Stats.USE_CAULDRON);
					BlockState newState = ModBlocks.MUSHROOM_STEW_CAULDRON.getDefaultState().with(MushroomStewCauldronBlock.LEVEL, state.get(MushroomStewCauldronBlock.LEVEL));
					world.setBlockState(pos, newState);
					MushroomStewCauldronBlock.decrementFluidLevel(newState, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.SUSPICIOUS_STEW, (state, world, pos, player, hand, stack) -> {
			if (state.get(MushroomStewCauldronBlock.LEVEL) != 6) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.BOWL)));
					if(world.getBlockEntity(pos) != null) {
						((SuspiciousStewCauldronBlockEntity)world.getBlockEntity(pos)).addEffectsFromStew(stack);
					}
					player.incrementStat(Stats.USE_CAULDRON);
					MushroomStewCauldronBlock.incrementFluidLevel(state, world, pos, 2);
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.BROWN_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		SUSPICIOUS_STEW_CAULDRON_BEHAVIOR.put(Items.RED_MUSHROOM, ADD_MUSHROOM_TO_STEW);
		addStandardBehaviors(SUSPICIOUS_STEW_CAULDRON_BEHAVIOR);

		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
			return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.WATER_BUCKET), (blockState) -> {
				return blockState.get(ModThreeLeveledCauldronBlock.LEVEL) == 3;
			}, SoundEvents.ITEM_BUCKET_FILL);
		});
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if (!world.isClient) {
				ItemStack bottle = PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);
				if(world.getBlockEntity(pos) != null) {
					bottle.getTag().putInt("CustomPotionColor", ((DyedWaterCauldronBlockEntity)world.getBlockEntity(pos)).getColor());
				}
				player.setStackInHand(hand, ItemUsage.method_30012(stack, player, bottle));
				player.incrementStat(Stats.USE_CAULDRON);
				ModThreeLeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResult.success(world.isClient);
		});
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
			if (state.get(ModThreeLeveledCauldronBlock.LEVEL) != 3 && PotionUtil.getPotion(stack) == Potions.WATER) {
				if (!world.isClient) {
					player.setStackInHand(hand, ItemUsage.method_30012(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.USE_CAULDRON);
					world.setBlockState(pos, state.cycle(ModThreeLeveledCauldronBlock.LEVEL));
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResult.success(world.isClient);
			} else {
				return ActionResult.PASS;
			}
		});
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.WHITE_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.ORANGE_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.MAGENTA_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_BLUE_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.YELLOW_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.LIME_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.PINK_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.GRAY_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_GRAY_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.CYAN_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.PURPLE_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.BLUE_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.BROWN_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.GREEN_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.RED_DYE, ADD_DYE_TO_WATER);
		DYED_WATER_CAULDRON_BEHAVIOR.put(Items.BLACK_DYE, ADD_DYE_TO_WATER);
		addStandardBehaviors(DYED_WATER_CAULDRON_BEHAVIOR);
	}

	static void addStandardBehaviors(Map<Item, CauldronBehavior> behaviorMap) {
		behaviorMap.put(Items.WATER_BUCKET, FILL_WITH_WATER);
		behaviorMap.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
		behaviorMap.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);
		behaviorMap.put(Items.MILK_BUCKET, FILL_WITH_MILK);
	}
}
