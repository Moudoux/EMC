package me.deftware.client.framework.main.validation;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.path.LocationUtil;
import me.deftware.client.framework.utils.HashUtils;
import me.deftware.client.framework.utils.WebUtils;

import java.io.File;

/**
 * Making sure EMC is up to date
 *
 * @author Deftware
 */
public class Validator {

	public static String CACHED_LOCAL_HASH = "";

	public static String getLocalEMCHash() throws Exception {
		if (!CACHED_LOCAL_HASH.isEmpty()) {
			return CACHED_LOCAL_HASH;
		}
		LocationUtil emc = LocationUtil.getEMC();
		File physicalFile = emc.toFile();
		if (physicalFile == null || !physicalFile.exists()) {
			throw new Exception("EMC jar file not found! This should be impossible.");
		}
		CACHED_LOCAL_HASH = HashUtils.getSha1(physicalFile);
		return CACHED_LOCAL_HASH;
	}

	public static String getRemoteEMCHash() throws Exception {
		return WebUtils.get(WebUtils.getMavenUrl(FrameworkConstants.getFrameworkMaven(), FrameworkConstants.FRAMEWORK_MAVEN_URL) + ".sha1");
	}

	public static boolean isValidEMCInstance() {
		try {
			return getLocalEMCHash().trim().equalsIgnoreCase(getRemoteEMCHash().trim());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
