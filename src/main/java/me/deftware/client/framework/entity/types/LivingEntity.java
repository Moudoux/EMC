package me.deftware.client.framework.entity.types;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.item.ItemStack;
import me.deftware.mixin.imp.IMixinEntity;
import net.minecraft.util.Hand;

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
		getMinecraftEntity().airStrafingSpeed = multiplier;
	}

	public float getMovementMultiplier() {
		return getMinecraftEntity().airStrafingSpeed;
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
		((IMixinEntity) getMinecraftEntity()).removeRemovedReason(); // TODO: Verify this
		getMinecraftEntity().setHealth(20f);
		getMinecraftEntity().updatePosition(getPosX(), getPosY(), getPosZ());
	}

	private final ItemStack main = ItemStack.getEmpty(), offhand = ItemStack.getEmpty();

	@Override
	public ItemStack getEntityHeldItem(boolean offhand) {
		if (offhand)
			return this.offhand.setStack(getMinecraftEntity().getStackInHand(Hand.OFF_HAND));
		return main.setStack(getMinecraftEntity().getStackInHand(Hand.MAIN_HAND));
	}
	
}
