package me.deftware.client.framework.entity.types.objects;

import me.deftware.client.framework.entity.Entity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.FishingBobberEntity;

import java.util.Objects;

/**
 * @author Deftware
 */
public class EntityFishHook extends Entity {

	private static FishingBobberEntity fishHookPtr;
	private static EntityFishHook fishHook;

	public static boolean hasFish() {
		return Objects.requireNonNull(MinecraftClient.getInstance().player).fishHook != null;
	}

	public static EntityFishHook getInstance() {
		if (fishHook == null || Objects.requireNonNull(MinecraftClient.getInstance().player).fishHook != fishHookPtr) {
			fishHookPtr = Objects.requireNonNull(MinecraftClient.getInstance().player).fishHook;
			fishHook = new EntityFishHook(fishHookPtr);
		}
		return fishHook;
	}

	public EntityFishHook(net.minecraft.entity.Entity entity) {
		super(entity);
	}

}
