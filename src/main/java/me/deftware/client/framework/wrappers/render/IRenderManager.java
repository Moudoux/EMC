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
        return MinecraftClient.getInstance().getEntityRenderManager().field_4679;
    }

    public static float getPlayerViewX() {
        return MinecraftClient.getInstance().getEntityRenderManager().field_4677;
    }

    public static double getViewerX() {
        return MinecraftClient.getInstance().getEntityRenderManager().field_4695;
    }

    public static double getViewerY() {
        return MinecraftClient.getInstance().getEntityRenderManager().field_4694;
    }

    public static double getViewerZ() {
        return MinecraftClient.getInstance().getEntityRenderManager().field_4693;
    }

}
