package me.deftware.client.framework.entity.types.objects;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.item.ItemStack;

/**
 * @author Deftware
 */
public class ItemEntity extends Entity {

	private final ItemStack stack = ItemStack.EMPTY;

	public ItemEntity(net.minecraft.entity.Entity entity) {
		super(entity);
	}

	public ItemStack getStack() {
		return stack.setStack(getMinecraftEntity().getStack());
	}

	public net.minecraft.entity.ItemEntity getMinecraftEntity() {
		return (net.minecraft.entity.ItemEntity) entity;
	}

}
