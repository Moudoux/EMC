package me.deftware.client.framework.item.effect;

import net.minecraft.entity.effect.StatusEffectCategory;
import me.deftware.client.framework.registry.Identifiable;
import net.minecraft.util.registry.Registry;

/**
 * @author Deftware
 */
public class StatusEffect implements Identifiable {

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

	@Override
	public String getTranslationKey() {
		return statusEffect.getTranslationKey();
	}

	@Override
	public String getIdentifierKey() {
		return Registry.STATUS_EFFECT.getId(statusEffect).getPath();
	}

}
