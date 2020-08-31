package me.deftware.client.framework.item.types;

import me.deftware.client.framework.item.Item;

import java.util.Objects;

/**
 * @author Deftware
 */
public class FoodItem extends Item {

	public FoodItem(net.minecraft.item.Item item) {
		super(item);
	}

	public int getHunger() {
		return Objects.requireNonNull(getMinecraftItem().getFoodComponent()).getHunger();
	}

	public float getSaturation() {
		return Objects.requireNonNull(getMinecraftItem().getFoodComponent()).getSaturationModifier();
	}

}
