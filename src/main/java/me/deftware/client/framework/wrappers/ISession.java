package me.deftware.client.framework.wrappers;

import net.minecraft.client.Minecraft;

public class ISession {

	public static String getISessionID() {
		return Minecraft.getMinecraft().getSession().getSessionID();
	}

	public static String getIPlayerID() {
		return Minecraft.getMinecraft().getSession().getPlayerID();
	}

	public static String getIUsername() {
		return Minecraft.getMinecraft().getSession().getUsername();
	}

	public static String getIToken() {
		return Minecraft.getMinecraft().getSession().getToken();
	}

}
