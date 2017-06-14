package me.deftware.client.framework.FontRender;

import java.awt.Font;

public class Fonts {

	public static IFontRendererObject segoe18, segoe15, inherit110;

	public static void loadFonts() {
		segoe15 = new IFontRendererObject(new Font("Arial", Font.PLAIN, 30), true, 8);
		segoe18 = new IFontRendererObject(new Font("Arial", Font.PLAIN, 36), true, 8);
		inherit110 = new IFontRendererObject(new Font("inherit", Font.PLAIN, 200), true, 8);
	}

}
