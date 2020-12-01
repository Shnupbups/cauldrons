package com.shnupbups.cauldrons.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;

import com.shnupbups.cauldrons.registry.ModBlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

public class SuspiciousStewCauldronBlockEntity extends BlockEntity {
	protected List<StatusEffectInstance> effects = new ArrayList<>();

	public SuspiciousStewCauldronBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntityTypes.SUSPICIOUS_STEW_CAULDRON, blockPos, blockState);
	}

	public List<StatusEffectInstance> getEffects() {
		return effects;
	}

	public void addEffectsToStew(ItemStack stew) {
		CompoundTag compoundTag = stew.getOrCreateTag();
		ListTag listTag = compoundTag.getList("Effects", 9);
		for (StatusEffectInstance effect:getEffects()) {
			CompoundTag compoundTag2 = new CompoundTag();
			compoundTag2.putByte("EffectId", (byte)StatusEffect.getRawId(effect.getEffectType()));
			compoundTag2.putInt("EffectDuration", effect.getDuration());
			listTag.add(compoundTag2);
		}
		compoundTag.put("Effects", listTag);
	}

	public boolean addEffect(StatusEffect effect, int duration) {
		return addEffect(new StatusEffectInstance(effect, duration));
	}

	public boolean addEffect(FlowerBlock flower) {
		return addEffect(flower.getEffectInStew(), flower.getEffectInStewDuration());
	}

	public void addEffectsFromStew(ItemStack stew) {
		CompoundTag compoundTag = stew.getTag();
		if (compoundTag != null && compoundTag.contains("Effects", 9)) {
			ListTag listTag = compoundTag.getList("Effects", 10);

			for (int i = 0; i < listTag.size(); ++i) {
				int j = 160;
				CompoundTag compoundTag2 = listTag.getCompound(i);
				if (compoundTag2.contains("EffectDuration", 3)) {
					j = compoundTag2.getInt("EffectDuration");
				}

				StatusEffect statusEffect = StatusEffect.byRawId(compoundTag2.getByte("EffectId"));
				if (statusEffect != null) {
					addEffect(statusEffect, j);
				}
			}
		}
	}

	public boolean addEffect(StatusEffectInstance effect) {
		for(StatusEffectInstance e:getEffects()) {
			if (e.getEffectType().equals(effect.getEffectType())) {
				if(e.getDuration() < effect.getDuration()) {
					removeEffect(e);
				} else {
					return false;
				}
			}
		}
		effects.add(effect);
		return true;
	}

	public boolean removeEffect(StatusEffect effect) {
		for(StatusEffectInstance e:getEffects()) {
			if (e.getEffectType().equals(effect)) {
				removeEffect(e);
				return true;
			}
		} return false;
	}

	public boolean removeEffect(StatusEffectInstance effect) {
		return effects.remove(effect);
	}

	@Override
	public void fromTag(CompoundTag compoundTag) {
		super.fromTag(compoundTag);
		if (compoundTag != null && compoundTag.contains("Effects", 9)) {
			ListTag listTag = compoundTag.getList("Effects", 10);

			for (int i = 0; i < listTag.size(); ++i) {
				int j = 160;
				CompoundTag compoundTag2 = listTag.getCompound(i);
				if (compoundTag2.contains("EffectDuration", 3)) {
					j = compoundTag2.getInt("EffectDuration");
				}

				StatusEffect statusEffect = StatusEffect.byRawId(compoundTag2.getByte("EffectId"));
				if (statusEffect != null) {
					addEffect(statusEffect, j);
				}
			}
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
		super.toTag(compoundTag);
		ListTag listTag = compoundTag.getList("Effects", 9);
		for (StatusEffectInstance effect:getEffects()) {
			CompoundTag compoundTag2 = new CompoundTag();
			compoundTag2.putByte("EffectId", (byte)StatusEffect.getRawId(effect.getEffectType()));
			compoundTag2.putInt("EffectDuration", effect.getDuration());
			listTag.add(compoundTag2);
		}
		compoundTag.put("Effects", listTag);
		return compoundTag;
	}
}
