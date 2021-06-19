package me.deftware.client.framework.render.batching.font;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.chat.ChatSection;
import me.deftware.client.framework.chat.style.ChatStyle;
import me.deftware.client.framework.fonts.legacy.LegacyBitmapFont;
import me.deftware.client.framework.registry.font.IFontProvider;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.batching.VertexConstructor;
import net.minecraft.client.render.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;

/**
 * @author Deftware
 */
public class FontRenderStack extends RenderStack<FontRenderStack> {

	private int offset = 0;
	private final LegacyBitmapFont font;

	@Setter
	private int maxLigatureSize = 5;

	@Setter
	private boolean ligatures;

	public FontRenderStack(IFontProvider font) {
		this.font = font.getFont();
		this.scaled = this.font.scaled;
		this.ligatures = this.font.isLigatures();
	}

	@Override
	public FontRenderStack begin() {
		// Bind texture
		RenderSystem.enableTexture();
		font.getTextureAtlas().bind();
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
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
	}

	@Override
	public VertexConstructor vertex(double x, double y, double z) {
		builder.vertex(getModel(), (float) x,  (float) y,  (float) z);
		return this;
	}

	public FontRenderStack drawString(double x, double y, String message) {
		return drawString((int) x, (int) y, message);
	}

	public FontRenderStack drawString(int x, int y, String message) {
		renderCharBuffer(message.split(""), x, y, lastColor);
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
			renderCharBuffer(section.getText().split(""), x, y, color);
		}
		offset = 0;
		return this;
	}

	private void renderCharBuffer(String[] buffer, int x, int y, Color color) {
		// Scale position
		if (scaled) {
			x *= RenderStack.getScale();
			y *= RenderStack.getScale();
		}

		// Get font data
		int shadow = font.getShadow();
		Map<String, LegacyBitmapFont.CharData> characterMap = font.getCharacterMap();

		for (int character = 0; character < buffer.length; character++) {
			// Skip empty characters
			if (buffer[character].isEmpty())
				continue;

			// Skip spaces
			if (buffer[character].equalsIgnoreCase(" ")) {
				offset += font.getStringWidth(" ");
				continue;
			}

			int index = character;

			if (ligatures) {
				// Check for ligatures
				String tempString = buffer[character];
				for (int i = 1; i <= maxLigatureSize; i++) {
					if (character + i >= buffer.length)
						break;
					tempString += buffer[character + i];
					if (characterMap.containsKey(tempString)) {
						// Found ligature
						buffer[index] = tempString;
						character += i;
						break;
					}
				}
			}

			// Replace unknown characters with a question mark
			if (!characterMap.containsKey(buffer[index])) {
				buffer[index] = "?";
			}

			// Get character data
			LegacyBitmapFont.CharData data = characterMap.get(buffer[index]);

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
