package me.deftware.client.framework.registry;

import me.deftware.client.framework.item.enchantment.Enchantment;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public enum EnchantmentRegistry implements IRegistry<Enchantment, net.minecraft.enchantment.Enchantment> {

	INSTANCE;

	private final HashMap<String, Enchantment> enchantments = new HashMap<>();

	@Override
	public Stream<Enchantment> stream() {
		return enchantments.values().stream();
	}

	@Override
	public void register(String id, net.minecraft.enchantment.Enchantment object) {
		enchantments.putIfAbsent(id, new Enchantment(object));
	}

	@Override
	public Optional<Enchantment> find(String id) {
		return stream().filter(enchantment ->
				enchantment.getTranslationKey().equalsIgnoreCase(id)
		).findFirst();
	}

}
