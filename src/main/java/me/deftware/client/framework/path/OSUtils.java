package me.deftware.client.framework.path;

import javax.annotation.Nullable;
import java.io.File;

public class OSUtils {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OSUtils.OS.contains("win"));
	}

	public static boolean isMac() {
		return (OSUtils.OS.contains("darwin")) || (OSUtils.OS.contains("mac"));
	}

	public static boolean isLinux() {
		return (OSUtils.OS.contains("nux"));
	}

	@Nullable
	public static String getMCDir() {
		if (OSUtils.isWindows()) {
			return System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator;
		} else if (OSUtils.isLinux()) {
			return System.getProperty("user.home") + File.separator + ".minecraft" + File.separator;
		} else if (OSUtils.isMac()) {
			return System.getProperty("user.home") + File.separator + "Library" + File.separator
					+ "Application Support" + File.separator + "minecraft" + File.separator;
		}
		return null;
	}

}
