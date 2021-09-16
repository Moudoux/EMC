package me.deftware.client.framework.item.effect;


import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * @author Deftware
 */
public class StatusEffect {

	private final net.minecraft.entity.effect.StatusEffect statusEffect;
	private final EffectType effectType;

	public StatusEffect(net.minecraft.entity.effect.StatusEffect statusEffect) {
		this.statusEffect = statusEffect;
		this.effectType = statusEffect.getCategory() == StatusEffectCategory.BENEFICIAL ?
				EffectType.BENEFICIAL : statusEffect.getCategory() == StatusEffectCategory.HARMFUL ?
				EffectType.HARMFUL : EffectType.NEUTRAL;
	}

	public net.minecraft.entity.effect.StatusEffect getMinecraftStatusEffect() {
		return statusEffect;
	}

	public EffectType getEffectType() {
		return effectType;
	}

	public String getTranslationKey() {
		return statusEffect.getTranslationKey();
	}

}
