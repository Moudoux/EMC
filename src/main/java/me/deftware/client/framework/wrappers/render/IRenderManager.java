package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.utils.render.RenderUtils;
import me.deftware.mixin.imp.IMixinEntityRenderer;
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
        return MinecraftClient.getInstance().getEntityRenderDispatcher().camera.getYaw();
    }

    public static float getPlayerViewX() {
        return MinecraftClient.getInstance().getEntityRenderDispatcher().camera.getPitch();
    }

    public static float getPlayerFovMultiplier() {
        return ((IMixinEntityRenderer) MinecraftClient.getInstance().gameRenderer).getFovMultiplier();
    }

    public static void updatePlayerFovMultiplier(float newValue) {
        ((IMixinEntityRenderer) MinecraftClient.getInstance().gameRenderer).updateFovMultiplier(newValue);
    }

}
