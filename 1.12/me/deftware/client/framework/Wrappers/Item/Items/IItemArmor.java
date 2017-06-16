package me.deftware.client.framework.Wrappers.Item.Items;

import me.deftware.client.framework.Wrappers.Item.IItem;
import net.minecraft.item.ItemArmor;

public class IItemArmor extends IItem {

	private ItemArmor armor;

	public IItemArmor(ItemArmor armor) {
		super(armor);
		this.armor = (ItemArmor) this.getItem();
	}

	/**
	 * Holds the amount of damage that the armor reduces at full durability.
	 * 
	 * @return
	 */
	public int getDamageReduceAmount() {
		return armor.damageReduceAmount;
	}

	public int getTypeOrdinal() {
		return this.armor.armorType.ordinal();
	}

}
