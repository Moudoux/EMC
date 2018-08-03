package me.deftware.client.framework.fonts.cfont;

import net.minecraft.client.renderer.GlStateManager;

import static me.deftware.client.framework.utils.RenderUtils.glColor;

/**
 * Basic implementation of the FontRenderer interface.
 * <p>
 * Created by halalaboos.
 */
public class BasicFontRenderer implements FontRenderer {

	protected final FontData fontData = new FontData();

	protected int kerning = 0;

	public BasicFontRenderer() {

	}

	@Override
	public int drawString(FontData fontData, String text, int x, int y, int color) {

		if (!fontData.hasFont())
			return 0;
		GlStateManager.enableBlend();
		fontData.bind();
		glColor(color);
		int size = text.length();
		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);
			if (fontData.hasBounds(character)) {
				FontData.CharacterData area = fontData.getCharacterBounds(character);
				FontUtils.drawTextureRect(x, y, area.width, area.height,
						(float) area.x / fontData.getTextureWidth(),
						(float) area.y / fontData.getTextureHeight(),
						(float) (area.x + area.width) / fontData.getTextureWidth(),
						(float) (area.y + area.height) / fontData.getTextureHeight());
				x += area.width + kerning;
			}
		}
		return x;
	}

	public int getHeight() {

		return fontData.getFontHeight();
	}

	@Override
	public int drawString(String text, int x, int y, int color) {

		return drawString(fontData, text, x, y, color);
	}

	public int getKerning() {

		return kerning;
	}

	public void setKerning(int kerning) {

		this.kerning = kerning;
	}

	@Override
	public FontData getFontData() {

		return fontData;
	}

}