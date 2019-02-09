package me.deftware.client.framework.wrappers.item.items;

import me.deftware.client.framework.wrappers.item.IItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class IItemArmor extends IItem {

    private ArmorItem armor;

    public IItemArmor(Item armor) {
        super(armor);
        this.armor = (ArmorItem) getItem();
    }

    public int getDamageReduceAmount() {
        return armor.getProtection();
    }

    public int getTypeOrdinal() {
        return armor.getSlotType().ordinal();
    }

}
