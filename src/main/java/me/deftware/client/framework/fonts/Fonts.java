package me.deftware.client.framework.fonts;

import java.awt.*;

public class Fonts {

	public static IFontRendererObject segoe18 = null, segoe15 = null, inherit110 = null;

	public static void loadFonts() {
		if (Fonts.segoe15 != null) {
			return;
		}
		Fonts.segoe15 = new IFontRendererObject(new Font("Arial", Font.PLAIN, 30), true, 8);
		Fonts.segoe18 = new IFontRendererObject(new Font("Arial", Font.PLAIN, 36), true, 8);
		Fonts.inherit110 = new IFontRendererObject(new Font("inherit", Font.PLAIN, 200), true, 8);
	}

}
