package me.deftware.client.framework.Wrappers;

import net.minecraft.util.StringUtils;

public class IStringUtils extends StringUtils {

	public static String stripControlCodes(String text) {
		return PATTERN_CONTROL_CODE.matcher(text).replaceAll("");
	}

}
