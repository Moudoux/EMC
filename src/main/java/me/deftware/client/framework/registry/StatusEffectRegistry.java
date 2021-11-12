package me.deftware.client.framework.registry;

import me.deftware.client.framework.item.effect.StatusEffect;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public enum StatusEffectRegistry implements IRegistry.IdentifiableRegistry<StatusEffect, net.minecraft.entity.effect.StatusEffect> {

	INSTANCE;

	private final HashMap<String, StatusEffect> items = new HashMap<>();

	@Override
	public Stream<StatusEffect> stream() {
		return items.values().stream();
	}

	@Override
	public void register(String id, net.minecraft.entity.effect.StatusEffect object) {
		items.putIfAbsent(id, new StatusEffect(object));
	}

}
