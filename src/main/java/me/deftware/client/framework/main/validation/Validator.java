package me.deftware.client.framework.main.validation;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.path.LocationUtil;
import me.deftware.client.framework.utils.HashUtils;
import me.deftware.client.framework.utils.WebUtils;

import java.io.File;

/**
 * Validation util for the running instance of EMC
 *
 * @author Deftware
 */
public class Validator {

	private static String cachedLocalChecksum, cachedRemoteChecksum;

	public static String getLocalChecksum() throws Exception {
		if (cachedLocalChecksum != null && !cachedLocalChecksum.isEmpty()) {
			return cachedLocalChecksum;
		}
		LocationUtil emc = LocationUtil.getEMC();
		File physicalFile = emc.toFile();
		if (physicalFile == null || !physicalFile.exists()) {
			throw new Exception("EMC jar file not found! This should be impossible.");
		}
		return cachedLocalChecksum = HashUtils.getSha1(physicalFile).trim().toLowerCase();
	}

	public static String getRemoteChecksum() throws Exception {
		if (cachedRemoteChecksum != null && !cachedRemoteChecksum.isEmpty()) {
			return cachedRemoteChecksum;
		}
		return cachedRemoteChecksum = WebUtils.get(WebUtils.getMavenUrl(FrameworkConstants.getFrameworkMaven(), FrameworkConstants.FRAMEWORK_MAVEN_URL) + ".sha1").trim().toLowerCase();
	}

	public static boolean isValidInstance() {
		try {
			return getLocalChecksum().equalsIgnoreCase(getRemoteChecksum());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
