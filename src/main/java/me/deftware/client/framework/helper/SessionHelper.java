package me.deftware.client.framework.helper;

import net.minecraft.client.MinecraftClient;

/**
 * @author Deftware
 */
public class SessionHelper {

	public static String getSessionId() {
		return MinecraftClient.getInstance().getSession().getSessionId();
	}

	public static String getPlayerUUID() {
		return MinecraftClient.getInstance().getSession().getUuid();
	}

	public static String getPlayerUsername() {
		return MinecraftClient.getInstance().getSession().getUsername();
	}

	public static String getAccessToken() {
		return MinecraftClient.getInstance().getSession().getAccessToken();
	}

}
