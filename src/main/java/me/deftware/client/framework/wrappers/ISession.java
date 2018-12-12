package me.deftware.client.framework.wrappers;

import net.minecraft.client.Minecraft;

public class ISession {

	public static String getISessionID() {
		return Minecraft.getInstance().getSession().getSessionID();
	}

	public static String getIPlayerID() {
		return Minecraft.getInstance().getSession().getPlayerID();
	}

	public static String getIUsername() {
		return Minecraft.getInstance().getSession().getUsername();
	}

	public static String getIToken() {
		return Minecraft.getInstance().getSession().getToken();
	}

}
