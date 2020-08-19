package me.deftware.client.framework.item;

import net.minecraft.item.Item;

/**
 * An implementation could either be an item or a block
 * 
 * @author Deftware
 */
public interface IItem {

	Item getAsItem();

	String getIdentifierKey();

}
