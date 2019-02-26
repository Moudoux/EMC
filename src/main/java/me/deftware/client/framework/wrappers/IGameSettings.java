package me.deftware.client.framework.wrappers;

import net.minecraft.client.Minecraft;

public class IGameSettings {

    public static void setLimitFramerate(int framerate) {
        Minecraft.getInstance().gameSettings.limitFramerate = framerate;
    }

    public static int getLimitFramerate() {
        return Minecraft.getInstance().gameSettings.limitFramerate;
    }

}
