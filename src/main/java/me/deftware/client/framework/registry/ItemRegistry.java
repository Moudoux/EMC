package me.deftware.client.framework.registry;

import me.deftware.client.framework.item.Item;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public enum ItemRegistry implements IRegistry<Item, net.minecraft.item.Item> {

	INSTANCE;

	private final HashMap<String, Item> items = new HashMap<>();

	@Override
	public Stream<Item> stream() {
		return items.values().stream();
	}

	@Override
	public void register(String id, net.minecraft.item.Item object) {
		items.putIfAbsent(id, Item.newInstance(object));
	}

	@Override
	public Optional<Item> find(String id) {
		return stream().filter(item ->
				item.getTranslationKey().equalsIgnoreCase(id) ||
						item.getTranslationKey().substring("item.minecraft:".length()).equalsIgnoreCase(id) ||
						item.getTranslationKey().substring("block.minecraft:".length()).equalsIgnoreCase(id)
		).findFirst();
	}

}
