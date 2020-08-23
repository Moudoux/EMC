package me.deftware.client.framework.registry.font;

import me.deftware.client.framework.main.bootstrap.Bootstrap;

import java.awt.*;
import java.util.HashMap;

/**
 * @author Deftware
 */
public class TTFRegistry {

	private static final HashMap<String, Font> fonts = new HashMap<>();

	public static void registerCustomFont(Font font) {
		fonts.putIfAbsent(font.getFontName(), font);
		Bootstrap.logger.info("Registered custom font " + font.getFontName());
	}

	public static Font getFont(String name, Font _default) {
		return fonts.getOrDefault(name, _default);
	}

}
