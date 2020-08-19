package me.deftware.client.framework.item.types;

import me.deftware.client.framework.item.Item;

/**
 * @author Deftware
 */
public class SwordItem extends Item {

	public SwordItem(net.minecraft.item.Item item) {
		super(item);
	}

	public float getAttackDamage() {
		return ((net.minecraft.item.SwordItem) item).getAttackDamage() + 3.0F;
	}

}
