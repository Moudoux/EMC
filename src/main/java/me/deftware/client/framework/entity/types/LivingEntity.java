package me.deftware.client.framework.entity.types;

import me.deftware.client.framework.entity.Entity;

/**
 * @author Deftware
 */
public class LivingEntity extends Entity {

	public LivingEntity(net.minecraft.entity.Entity entity) {
		super(entity);
	}

	@Override
	public net.minecraft.entity.LivingEntity getMinecraftEntity() {
		return (net.minecraft.entity.LivingEntity) entity;
	}

	public float getHealth() {
		return getMinecraftEntity().getHealth();
	}

	public float getHealthPercentage() {
		return (getHealth() * 100) / getMaxHealth();
	}

	public float getMaxHealth() {
		return getMinecraftEntity().getMaxHealth();
	}

	public void setMovementMultiplier(float multiplier) {
		getMinecraftEntity().flyingSpeed = multiplier;
	}

	public float getMovementMultiplier() {
		return getMinecraftEntity().flyingSpeed;
	}

	public int getHurtTime() {
		return getMinecraftEntity().hurtTime;
	}

	public boolean isClimbing() {
		return getMinecraftEntity().isClimbing();
	}

	public float getMoveStrafing() {
		return getMinecraftEntity().sidewaysSpeed;
	}

	public float getMoveForward() {
		return getMinecraftEntity().upwardSpeed;
	}

	public void setAlive(boolean flag) {
		getMinecraftEntity().removed = false;
		getMinecraftEntity().setHealth(20f);
		getMinecraftEntity().updatePosition(getPosX(), getPosY(), getPosZ());
	}
	
}
