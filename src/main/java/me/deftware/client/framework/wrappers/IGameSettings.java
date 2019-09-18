package me.deftware.client.framework.wrappers;


import net.minecraft.client.MinecraftClient;

public class IGameSettings {

    public static int getLimitFramerate() {
        return MinecraftClient.getInstance().options.maxFps;
    }

    public static void setLimitFramerate(int framerate) {
        MinecraftClient.getInstance().options.maxFps = framerate;
        MinecraftClient.getInstance().method_22683().setFramerateLimit(framerate);
    }

}
