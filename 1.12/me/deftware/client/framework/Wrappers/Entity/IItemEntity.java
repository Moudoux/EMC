package me.deftware.client.framework.Wrappers.Entity;

import net.minecraft.entity.Entity;

public class IItemEntity {

	private Entity item;

	public IItemEntity(Entity item) {
		this.item = item;
	}

	public double getPosX() {
		return item.posX;
	}

	public double getPosY() {
		return item.posY;
	}

	public double getPosZ() {
		return item.posZ;
	}

	public double getLastTickPosX() {
		return item.lastTickPosX;
	}

	public double getLastTickPosY() {
		return item.lastTickPosY;
	}

	public double getLastTickPosZ() {
		return item.lastTickPosZ;
	}

	public float getHeight() {
		return item.height;
	}

	public Entity getItem() {
		return item;
	}

	public void setGlowing(boolean state) {
		this.item.setGlowing(state);
	}

}
