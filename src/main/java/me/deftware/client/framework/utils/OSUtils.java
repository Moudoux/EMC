package me.deftware.client.framework.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOverlayDebug;

import java.io.File;

public class OSUtils {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OSUtils.OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OSUtils.OS.indexOf("darwin") >= 0) || (OSUtils.OS.indexOf("mac") >= 0);
	}

	public static boolean isLinux() {
		return (OSUtils.OS.indexOf("nux") >= 0);
	}

	/**
	 * Returns the .minecraft directory, on all supported OSes
	 *
	 * @return
	 */
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

	public static String getVersion() {
		return Minecraft.getInstance().getVersion();
	}

	public static String getRunningFolder() {
		return getMCDir() + "versions" + File.separator + getVersion() + File.separator;
	}

}
