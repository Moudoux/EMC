package me.deftware.client.framework.Wrappers.Item.Items;

import me.deftware.client.framework.Wrappers.Item.IItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class IItemArmor extends IItem {

	private ItemArmor armor;

	public IItemArmor(Item armor) {
		super(armor);
		this.armor = (ItemArmor) this.getItem();
	}

	
	public int getDamageReduceAmount() {
		return armor.damageReduceAmount;
	}

	public int getTypeOrdinal() {
		return this.armor.armorType.ordinal();
	}

}
