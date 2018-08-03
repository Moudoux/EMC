package me.deftware.client.framework.fonts;

import me.deftware.client.framework.fonts.cfont.CFont;
import me.deftware.client.framework.main.EMCMod;

import java.awt.*;
import java.util.HashMap;

public class Fonts {

	public static HashMap<String, CFont> fontCache = new HashMap<>();

	/**
	 * Loads some common fonts
	 */
	public static void initialize() {
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
	public static CFont getFont(String fontType) {
		return fontCache.get(fontType);
	}

	public static CFont getFont(CommonFonts fontType) {
		return getFont(fontType.name().toLowerCase());
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
			CFont fontRenderer = new CFont();
			fontRenderer.setFont(new Font(internalName, Font.TRUETYPE_FONT, internalSize), true);
			fontCache.put(name, fontRenderer);
		}
	}

	/**
	 * Enum of common and pre-registered fonts for quick access
	 */
	public enum CommonFonts {
		segoe15, segoe18, inherit110
	}

}
