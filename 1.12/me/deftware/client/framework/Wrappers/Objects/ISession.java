package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.Minecraft;

public class ISession {

	public static String getISessionID() {
		return Minecraft.getMinecraft().session.getSessionID();
	}

	public static String getIPlayerID() {
		return Minecraft.getMinecraft().session.getPlayerID();
	}

	public static String getIUsername() {
		return Minecraft.getMinecraft().session.getUsername();
	}

	public static String getIToken() {
		return Minecraft.getMinecraft().session.getToken();
	}
	
}
