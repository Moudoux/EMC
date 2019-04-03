package me.deftware.client.framework.wrappers.render;

import me.deftware.mixin.imp.IMixinRenderManager;
import net.minecraft.client.MinecraftClient;

public class IRenderManager {

    public static double getRenderPosX() {
        return ((IMixinRenderManager) MinecraftClient.getInstance().getEntityRenderManager()).getRenderPosX();
    }

    public static double getRenderPosY() {
        return ((IMixinRenderManager) MinecraftClient.getInstance().getEntityRenderManager()).getRenderPosY();
    }

    public static double getRenderPosZ() {
        return ((IMixinRenderManager) MinecraftClient.getInstance().getEntityRenderManager()).getRenderPosZ();
    }

    public static float getPlayerViewY() {
        return MinecraftClient.getInstance().getEntityRenderManager().cameraYaw;
    }

    public static float getPlayerViewX() {
        return MinecraftClient.getInstance().getEntityRenderManager().cameraPitch;
    }

}
