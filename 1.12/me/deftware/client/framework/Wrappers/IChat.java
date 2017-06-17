package me.deftware.client.framework.Wrappers;

import me.deftware.client.framework.Utils.Chat.ChatProcessor;
import net.minecraft.client.Minecraft;

public class IChat {

	public static void sendChatMessage(String message) {
		Minecraft.getMinecraft().player.sendChatMessage(message);
	}
	
	public static void sendClientMessage(String message) {
		ChatProcessor.printClientMessage(message);
	}
	
	public static void sendClientMessage(String message, String prefix) {
		ChatProcessor.printClientMessage(prefix + " " + message, false);
	}
	
	public static void clearMessages() {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(true);
	}

}
