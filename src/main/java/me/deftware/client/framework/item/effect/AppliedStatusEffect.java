package me.deftware.client.framework.item.effect;

import net.minecraft.entity.effect.StatusEffectInstance;

/**
 * @author Deftware
 */
public class AppliedStatusEffect {

	private final StatusEffect effect;
	private final StatusEffectInstance instance;

	public AppliedStatusEffect(StatusEffectInstance instance) {
		this.effect = new StatusEffect(instance.getEffectType());
		this.instance = instance;
	}

	public StatusEffectInstance getMinecraftStatusEffectInstance() {
		return instance;
	}

	public StatusEffect getEffect() {
		return effect;
	}

	public int getDuration() {
		return instance.getDuration();
	}

	public int getAmplifier() {
		return instance.getAmplifier();
	}

	public boolean isAmbient() {
		return instance.isAmbient();
	}

}
