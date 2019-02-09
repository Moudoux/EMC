package me.deftware.client.framework;

import net.minecraft.SharedConstants;

public class FrameworkConstants {

	public static double VERSION = 13.7;
	public static int PATCH = 5;
	public static boolean FORGE_MODE = false;

	public static String AUTHOR = "Deftware";
	public static String FRAMEWORK_NAME = "EMC";

	/*
		Minecraft info
	 */

	public static String MINECRAFT_VERSION = SharedConstants.getGameVersion().getName();
	public static int MINECRAFT_PROTOCOL = SharedConstants.getGameVersion().getProtocolVersion();

}
