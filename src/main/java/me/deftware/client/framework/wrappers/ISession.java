package me.deftware.client.framework.wrappers;

import net.minecraft.client.MinecraftClient;

public class ISession {

    public static String getISessionID() {
        return MinecraftClient.getInstance().getSession().getSessionId();
    }

    public static String getIPlayerID() {
        return MinecraftClient.getInstance().getSession().getSessionId();
    }

    public static String getIUsername() {
        return MinecraftClient.getInstance().getSession().getUsername();
    }

    public static String getIToken() {
        return MinecraftClient.getInstance().getSession().getAccessToken();
    }

}
