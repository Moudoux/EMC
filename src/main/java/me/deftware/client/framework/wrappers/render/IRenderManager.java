package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.MinecraftClient;

public class IRenderManager {

    public static double getRenderPosX() {
        return 0;
    }

    public static double getRenderPosY() {
        return 0;
    }

    public static double getRenderPosZ() {
        return 0;
    }

    public static float getPlayerViewY() {
        return MinecraftClient.getInstance().getEntityRenderManager().cameraYaw;
    }

    public static float getPlayerViewX() {
        return MinecraftClient.getInstance().getEntityRenderManager().cameraPitch;
    }

}
