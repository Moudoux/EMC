package me.deftware.client.framework.render.batching.font;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.chat.ChatSection;
import me.deftware.client.framework.chat.style.ChatStyle;
import me.deftware.client.framework.fonts.legacy.LegacyBitmapFont;
import me.deftware.client.framework.registry.font.IFontProvider;
import me.deftware.client.framework.render.batching.RenderStack;
import net.minecraft.client.render.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;

/**
 * @author Deftware
 */
public class FontRenderStack extends RenderStack<FontRenderStack> {

	private int offset = 0;
	private @Setter boolean scaled;
	private final LegacyBitmapFont font;

	public FontRenderStack(IFontProvider font) {
		this.font = font.getFont();
		this.scaled = this.font.scaled;
	}

	@Override
	public FontRenderStack begin() {
		// Bind texture
		int glId = font.getGlId();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
		RenderSystem.setShaderTexture(0, glId);
		// Set up buffer
		return super.begin(GL11.GL_QUADS);
	}

	@Override
	public void end() {
		super.end();
		RenderSystem.disableTexture();
	}

	@Override
	protected VertexFormat getFormat() {
		return VertexFormats.POSITION_TEXTURE_COLOR;
	}

	@Override
	protected void setShader() {
		// POSITION_TEXTURE_COLOR
		RenderSystem.setShader(GameRenderer::method_34543);
	}

	@Override
	protected VertexConsumer vertex(double x, double y, double z) {
		return builder.vertex(getModel(), (float) x,  (float) y,  (float) z);
	}

	public FontRenderStack drawString(double x, double y, String message) {
		return drawString((int) x, (int) y, message);
	}

	public FontRenderStack drawString(int x, int y, String message) {
		renderCharBuffer(message.toCharArray(), x, y, lastColor);
		offset = 0;
		return this;
	}

	public FontRenderStack drawString(double x, double y, ChatMessage message) {
		return drawString((int) x, (int) y, message);
	}

	public FontRenderStack drawString(int x, int y, ChatMessage message) {
		for (ChatSection section : message.getSectionList()) {
			ChatStyle style = section.getStyle();
			Color color = Color.white;
			if (style.getColor() != null)
				color = style.getColor().getColor();
			renderCharBuffer(section.getText().toCharArray(), x, y, color);
		}
		offset = 0;
		return this;
	}

	private void renderCharBuffer(char[] buffer, int x, int y, Color color) {
		// Scale position
		if (scaled) {
			x *= RenderStack.getScale();
			y *= RenderStack.getScale();
		}

		// Get font data
		int shadow = font.getShadow();
		Map<Character, LegacyBitmapFont.CharData> characterMap = font.getCharacterMap();

		for (int character = 0; character < buffer.length; character++) {
			// Skip spaces
			if (buffer[character] == ' ') {
				offset += font.getStringWidth(" ");
				continue;
			}

			// Replace unknown characters with a question mark
			if (!font.characterMap.containsKey(buffer[character]))
				buffer[character] = '?';

			// Get character data
			LegacyBitmapFont.CharData data = characterMap.get(buffer[character]);

			// Draw shadow
			if (shadow > 0) {
				// RenderSystem.setShaderColor(0f, 0f, 0f, this.alpha);
				glColor(Color.black);
				drawCharacter(x + offset + shadow, y + shadow, data);
			}

			// Draw text
			// RenderSystem.setShaderColor(this.red, this.green, this.blue, this.alpha);
			glColor(color, 255f);
			drawCharacter(x + offset, y, data);
			offset += data.getWidth();
		}

		// Reset shader color
		// RenderSystem.setShaderColor(1, 1, 1,1);
	}

	public int getFontHeight() {
		return (int) (font.getStringHeight() / (scaled ? RenderStack.getScale() : 1f));
	}

	public int getStringWidth(String text) {
		return (int) (font.getStringWidth(text) / (scaled ? RenderStack.getScale() : 1f));
	}

	public int getStringWidth(ChatMessage text) {
		return getStringWidth(text.toString(false));
	}

	private void drawCharacter(int x, int y, LegacyBitmapFont.CharData data) {
		// Size
		int width = data.getWidth(), height = data.getHeight();
		// Offsets
		int u = data.getU(), v = data.getV();
		// Draw
		drawTexture(x, x + width, y, y + height, 0, width, height, u, v, font.getTextureWidth(), font.getTextureHeight());
	}

	/*
		Draw
	 */

	private void drawTexture(int x0, int x1, int y0, int y1, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
		drawTexturedQuad(x0, x1, y0, y1, z, u / textureWidth, (u + regionWidth) / textureWidth, v / textureHeight, (v + regionHeight) / textureHeight);
	}

	private void drawTexturedQuad(int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
		vertex(x0, y1, z).texture(u0, v1).color(red, green, blue, alpha).next();
		vertex(x1, y1, z).texture(u1, v1).color(red, green, blue, alpha).next();
		vertex(x1, y0, z).texture(u1, v0).color(red, green, blue, alpha).next();
		vertex(x0, y0, z).texture(u0, v0).color(red, green, blue, alpha).next();
	}

}
