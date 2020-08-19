package me.deftware.client.framework.item.types;

import me.deftware.client.framework.item.Item;
import net.minecraft.item.ArmorItem;

/**
 * @author Deftware
 */
public class ArmourItem extends Item {

	public ArmourItem(net.minecraft.item.Item item) {
		super(item);
	}

	@Override
	public ArmorItem getMinecraftItem() {
		return (ArmorItem) item;
	}

	public int getDamageReduceAmount() {
		return getMinecraftItem().getProtection();
	}

	public int getTypeOrdinal() {
		return getMinecraftItem().getSlotType().ordinal();
	}

}
