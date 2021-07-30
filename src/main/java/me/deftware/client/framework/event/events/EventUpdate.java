package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered when players' position and/or logic is being updated
 */
public class EventUpdate extends Event {

	protected double posX, posY, posZ;
	protected float rotationYaw, rotationPitch;
	protected boolean onGround;

	public EventUpdate() { }

	public EventUpdate(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, boolean onGround) {
		create(posX, posY, posZ, rotationYaw, rotationPitch, onGround);
	}

	public EventUpdate create(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, boolean onGround) {
		setCanceled(false);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
		this.onGround = onGround;
		return this;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public float getRotationYaw() {
		return rotationYaw;
	}

	public float getRotationPitch() {
		return rotationPitch;
	}

	public boolean isOnGround() {
		return onGround;
	}

}
