package com.shnupbups.cauldrons.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
		List<StatusEffectInstance> effects = new ArrayList<>();
		this.effects.forEach((effect) -> effects.add(new StatusEffectInstance(effect)));
		return effects;
	}

	public void addEffectsToStew(ItemStack stew) {
		NbtCompound nbtCompound = stew.getOrCreateTag();
		NbtList nbtList = nbtCompound.getList("Effects", 9);
		for (StatusEffectInstance effect:getEffects()) {
			NbtCompound nbtCompound2 = new NbtCompound();
			nbtCompound2.putByte("EffectId", (byte)StatusEffect.getRawId(effect.getEffectType()));
			nbtCompound2.putInt("EffectDuration", effect.getDuration());
			nbtList.add(nbtCompound2);
		}
		nbtCompound.put("Effects", nbtList);
	}

	public boolean addEffect(StatusEffect effect, int duration) {
		return addEffect(new StatusEffectInstance(effect, duration));
	}

	public boolean addEffect(FlowerBlock flower) {
		return addEffect(flower.getEffectInStew(), flower.getEffectInStewDuration());
	}

	public void addEffectsFromStew(ItemStack stew) {
		NbtCompound nbtCompound = stew.getTag();
		if (nbtCompound != null && nbtCompound.contains("Effects", 9)) {
			NbtList nbtList = nbtCompound.getList("Effects", 10);

			for (int i = 0; i < nbtList.size(); ++i) {
				int j = 160;
				NbtCompound nbtCompound2 = nbtList.getCompound(i);
				if (nbtCompound2.contains("EffectDuration", 3)) {
					j = nbtCompound2.getInt("EffectDuration");
				}

				StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getByte("EffectId"));
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
	public void readNbt(NbtCompound nbtCompound) {
		super.readNbt(nbtCompound);
		if (nbtCompound != null && nbtCompound.contains("Effects", 9)) {
			NbtList nbtList = nbtCompound.getList("Effects", 10);

			for (int i = 0; i < nbtList.size(); ++i) {
				int j = 160;
				NbtCompound nbtCompound2 = nbtList.getCompound(i);
				if (nbtCompound2.contains("EffectDuration", 3)) {
					j = nbtCompound2.getInt("EffectDuration");
				}

				StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getByte("EffectId"));
				if (statusEffect != null) {
					addEffect(statusEffect, j);
				}
			}
		}
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbtCompound) {
		super.writeNbt(nbtCompound);
		NbtList nbtList = nbtCompound.getList("Effects", 9);
		for (StatusEffectInstance effect:getEffects()) {
			NbtCompound nbtCompound2 = new NbtCompound();
			nbtCompound2.putByte("EffectId", (byte)StatusEffect.getRawId(effect.getEffectType()));
			nbtCompound2.putInt("EffectDuration", effect.getDuration());
			nbtList.add(nbtCompound2);
		}
		nbtCompound.put("Effects", nbtList);
		return nbtCompound;
	}
}
