package com.shnupbups.cauldrons.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.cauldrons.registry.ModBlockEntityTypes;

public class DyedWaterCauldronBlockEntity extends BlockEntity {
	protected int color = -1;

	public DyedWaterCauldronBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntityTypes.DYED_WATER_CAULDRON, blockPos, blockState);
	}

	public int getColor() {
		return color;
	}

	public boolean setColor(int color) {
		if(color == this.color) return false;
		else {
			this.color = color;
			return true;
		}
	}

	public boolean addColor(int color) {
		if(this.color < 0) {
			return setColor(color);
		} else {
			int red = (getRed(getColor()) + getRed(color)) / 2;
			int green = (getGreen(getColor()) + getGreen(color)) / 2;
			int blue = (getBlue(getColor()) + getBlue(color)) / 2;
			return setColor(getHex(red, green, blue));
		}
	}

	@Override
	public void fromTag(CompoundTag compoundTag) {
		super.fromTag(compoundTag);
		if (compoundTag != null && compoundTag.contains("Color", 3)) {
			int color = compoundTag.getInt("Color");
			setColor(color);
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
		super.toTag(compoundTag);
		compoundTag.putInt("Color", getColor());
		return compoundTag;
	}

	public static int getRed(int hex) {
		return (hex & 0xFF0000) >> 16;
	}

	public static int getGreen(int hex) {
		return (hex & 0xFF00) >> 8;
	}

	public static int getBlue(int hex) {
		return hex & 0xFF;
	}

	public static int getHex(int red, int green, int blue) {
		return (red << 16) + (green << 8) + blue;
	}
}
