package me.deftware.client.framework.wrappers;

import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.mixin.imp.IMixinGuiChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

public class IChat {

	public static void sendChatMessage(String message) {
		Minecraft.getInstance().player.sendChatMessage(message);
	}

	public static void sendClientMessage(String message) {
		ChatProcessor.printClientMessage(message);
	}

	public static void sendClientMessage(String message, String prefix) {
		ChatProcessor.printClientMessage(prefix + " " + message, false);
	}

	public static void clearMessages() {
		Minecraft.getInstance().ingameGUI.getChatGUI().clearChatMessages(true);
	}

	public static String getCurrentChatText() {
		if (Minecraft.getInstance().currentScreen instanceof GuiChat) {
			return ((IMixinGuiChat) Minecraft.getInstance().currentScreen).getCurrentText();
		}
		return "";
	}

}
