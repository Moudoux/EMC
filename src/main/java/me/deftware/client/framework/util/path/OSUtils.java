package me.deftware.client.framework.util.path;

/**
 * @author Deftware
 */
public class OSUtils {

	private static final String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OSUtils.OS.contains("win"));
	}

	public static boolean isMac() {
		return (OSUtils.OS.contains("darwin")) || (OSUtils.OS.contains("mac"));
	}

	public static boolean isLinux() {
		return (OSUtils.OS.contains("nux"));
	}

}
