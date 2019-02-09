package me.deftware.client.framework.wrappers;

import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.client.MinecraftClient;

public class IChat {

    public static void sendChatMessage(String message) {
        MinecraftClient.getInstance().player.sendChatMessage(message);
    }

    public static void sendClientMessage(String message) {
        ChatProcessor.printClientMessage(message);
    }

    public static void sendClientMessage(String message, String prefix) {
        ChatProcessor.printClientMessage(prefix + " " + message, false);
    }

    public static void clearMessages() {
        MinecraftClient.getInstance().inGameHud.getHudChat().clear(true);
    }

}
