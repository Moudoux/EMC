package me.deftware.client.framework.render.camera;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.world.World;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;

/**
 * @author Deftware
 */
public class GameCamera {

	public Camera getMinecraftCamera() {
		return MinecraftClient.getInstance().getEntityRenderDispatcher().camera;
	}

	public Vector3d getCameraPosition() {
		return new Vector3d(getMinecraftCamera().getPos());
	}

	public float getRotationPitch() {
		return getMinecraftCamera().getPitch();
	}

	public float getRotationYaw() {
		return getMinecraftCamera().getYaw();
	}

	public Entity getFocusedEntity() {
		return World.getEntityById(getMinecraftCamera().getFocusedEntity().getId());
	}

	public double getRenderPosX() {
		return getMinecraftCamera().getPos().x;
	}

	public double getRenderPosY() {
		return getMinecraftCamera().getPos().y;
	}

	public double getRenderPosZ() {
		return getMinecraftCamera().getPos().z;
	}

}
