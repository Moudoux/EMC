package me.deftware.client.framework.Wrappers.Entity;

import net.minecraft.entity.Entity;

public class IMob {

	private Entity mob;

	public IMob(Entity mob) {
		this.mob = mob;
	}

	public double getPosX() {
		return mob.posX;
	}

	public double getPosY() {
		return mob.posY;
	}

	public double getPosZ() {
		return mob.posZ;
	}

	public double getLastTickPosX() {
		return mob.lastTickPosX;
	}

	public double getLastTickPosY() {
		return mob.lastTickPosY;
	}

	public double getLastTickPosZ() {
		return mob.lastTickPosZ;
	}

	public float getHeight() {
		return mob.height;
	}

	public Entity getMob() {
		return mob;
	}

	public void setGlowing(boolean state) {
		mob.setGlowing(state);
	}

}
