package me.deftware.client.framework.fonts;

import me.deftware.client.framework.main.EMCMod;

import java.awt.*;
import java.util.HashMap;

public class Fonts {

	public static HashMap<String, IFontRendererObject> fontCache = new HashMap<>();

	public static IFontRendererObject segoe18 = null, segoe15 = null, inherit110 = null;

	/**
	 * Loads some common fonts
	 */
	public static void initialize() {
		if (!fontCache.isEmpty()) { return; }
		registerFont("segoe15", "Arial",30);
		registerFont("segoe18", "Arial",36);
		registerFont("inherit110", "Arial",200);
	}

	/**
	 * Returns a font renderer from the font cache, your mod should register any customs fonts used in the {@link EMCMod#initialize()} method
	 *
	 * @param fontType Format for the font key is [font name][font size], example: segoe15
	 * @return IFontRendererObject
	 */
	public static IFontRendererObject getFont(String fontType) {
		return fontCache.get(fontType);
	}

	public static IFontRendererObject getFont(CommonFonts fontType) {
		return getFont(fontType.name());
	}

	/**
	 * Registers a font
	 *
	 * @param name The name of the font, e.g Arial
	 * @param internalName The name of the font and size, e.g arial15
	 * @param internalSize The size of the internal rendering for the font, font size 15 = 30
	 */
	public static void registerFont(String name, String internalName, int internalSize) {
		if (!fontCache.containsKey(name)) {
			fontCache.put(internalName, new IFontRendererObject(new Font(name, Font.PLAIN, internalSize), true, 8));
		}
	}

	/**
	 * Enum of common and pre-registered fonts for quick access
	 */
	public enum CommonFonts {
		segoe15, segoe18, inherit110
	}

}
