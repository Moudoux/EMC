package me.deftware.client.framework.Utils;

import java.io.File;

public class OSUtils {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("darwin") >= 0) || (OS.indexOf("mac") >= 0);
	}

	public static boolean isLinux() {
		return (OS.indexOf("nux") >= 0);
	}

	public static String getMCDir() {
		String minecraft = "";
		if (OSUtils.isWindows()) {
			minecraft = System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator;
		} else if (OSUtils.isLinux()) {
			minecraft = System.getProperty("user.home") + File.separator + ".minecraft" + File.separator;
		} else if (OSUtils.isMac()) {
			minecraft = System.getProperty("user.home") + File.separator + "Library" + File.separator
					+ "Application Support" + File.separator + "minecraft" + File.separator;
		}
		return minecraft;
	}

}
