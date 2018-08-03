package me.deftware.client.framework.wrappers.item.items;

import me.deftware.client.framework.wrappers.item.IItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class IItemArmor extends IItem {

	private ItemArmor armor;

	public IItemArmor(Item armor) {
		super(armor);
		this.armor = (ItemArmor) getItem();
	}


	public int getDamageReduceAmount() {
		return armor.getDamageReduceAmount();
	}

	public int getTypeOrdinal() {
		return armor.getEquipmentSlot().ordinal();
	}

}
