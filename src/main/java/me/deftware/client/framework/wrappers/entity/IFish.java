package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.FishingBobberEntity;

public class IFish {

    private static FishingBobberEntity getEntity() {
        return MinecraftClient.getInstance().player.fishHook;
    }

    public static boolean isNull() {
        return IFish.getEntity() == null;
    }

    public static int getPosY() {
        return (int) IFish.getEntity().getY();
    }

}
