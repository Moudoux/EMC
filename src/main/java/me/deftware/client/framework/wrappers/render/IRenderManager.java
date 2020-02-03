package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.utils.render.RenderUtils;
import me.deftware.mixin.imp.IMixinGameRenderer;
import net.minecraft.client.MinecraftClient;

public class IRenderManager {

    public static double getRenderPosX() {
        return (RenderUtils.getRenderManager()).getRenderPosX();
    }

    public static double getRenderPosY() {
        return (RenderUtils.getRenderManager()).getRenderPosY();
    }

    public static double getRenderPosZ() {
        return (RenderUtils.getRenderManager()).getRenderPosZ();
    }

    public static float getPlayerViewY() {
        return MinecraftClient.getInstance().getEntityRenderManager().camera.getYaw();
    }

    public static float getPlayerViewX() {
        return MinecraftClient.getInstance().getEntityRenderManager().camera.getPitch();
    }

    public static float getPlayerFovMultiplier() {
        return ((IMixinGameRenderer) MinecraftClient.getInstance().gameRenderer).getFovMultiplier();
    }

    public static void updatePlayerFovMultiplier(float newValue) {
        ((IMixinGameRenderer) MinecraftClient.getInstance().gameRenderer).updateFovMultiplier(newValue);
    }

}
