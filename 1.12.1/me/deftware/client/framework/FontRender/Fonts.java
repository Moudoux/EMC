package me.deftware.client.framework.FontRender;

import java.awt.Font;

public class Fonts {

	public static IFontRendererObject segoe18 = null, segoe15 = null, inherit110 = null;

	public static void loadFonts() {
		if (segoe15 != null) {
			return;
		}
		segoe15 = new IFontRendererObject(new Font("Arial", Font.PLAIN, 30), true, 8);
		segoe18 = new IFontRendererObject(new Font("Arial", Font.PLAIN, 36), true, 8);
		inherit110 = new IFontRendererObject(new Font("inherit", Font.PLAIN, 200), true, 8);
	}

}
