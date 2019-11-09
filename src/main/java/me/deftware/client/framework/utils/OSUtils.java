package me.deftware.client.framework.utils;

import me.deftware.client.framework.wrappers.IMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.debug.DebugRenderer;

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

	public static String getMCDir() {
		return getMCDir(true);
	}


	/**
	 * Returns the .minecraft directory, on all supported OSes
	 *
	 * @param useOSOnly Check this to only use OS Detection (versions or libraries folder checks in most cases)
	 * @return .minecraft Directory
	 */
	public static String getMCDir(boolean useOSOnly) {
		String minecraft = "";

		if (OSUtils.isWindows()) {
			minecraft = System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator;
		} else if (OSUtils.isLinux()) {
			minecraft = System.getProperty("user.home") + File.separator + ".minecraft" + File.separator;
		} else if (OSUtils.isMac()) {
			minecraft = System.getProperty("user.home") + File.separator + "Library" + File.separator
					+ "Application Support" + File.separator + "minecraft" + File.separator;
		}

		if (!useOSOnly || (minecraft.equals("") || !new File(minecraft).exists())) {
			try {
				if (IMinecraft.getRunningLocation() != null) {
					String splitter = OSUtils.isMac() ? "minecraft" : "\\.minecraft";
					if (IMinecraft.getRunningLocation().contains(splitter)) {
						minecraft = IMinecraft.getRunningLocation().split(splitter, 2)[0] + splitter + File.separator;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return minecraft;
	}

	public static String getVersion() {
		return MinecraftClient.getInstance().getGameVersion();
	}

	public static String getRunningFolder() {
		if (new File(getMCDir(true) + "versions").exists()) {
			return getMCDir(true) + "versions" + File.separator + getVersion() + File.separator;
		} else {
			return MinecraftClient.getInstance().runDirectory.getAbsolutePath() + File.separator;
		}
	}


}
