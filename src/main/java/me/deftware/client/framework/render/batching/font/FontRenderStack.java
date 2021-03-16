package me.deftware.client.framework.render.batching.font;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.chat.ChatSection;
import me.deftware.client.framework.chat.style.ChatStyle;
import me.deftware.client.framework.fonts.legacy.LegacyBitmapFont;
import me.deftware.client.framework.registry.font.IFontProvider;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.gl.GLX;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Deftware
 */
public class FontRenderStack extends RenderStack<FontRenderStack> {

	private int offset = 0;
	private @Setter boolean scaled, matrix = true;
	private final LegacyBitmapFont font;

	public FontRenderStack(IFontProvider font) {
		this.font = font.getFont();
		this.scaled = this.font.scaled;
	}

	@Override
	public FontRenderStack begin() {
		glColor(Color.white, 255f); // Default text color
		return this; /* Not used in this stack */
	}

	@Override
	protected void drawBuffer() {
		/* Not used in this stack */
	}

	public FontRenderStack glMatrix(boolean flag) {
		this.matrix = flag;
		return this;
	}

	public FontRenderStack drawString(double x, double y, String message) {
		return drawString((int) x, (int) y, message);
	}

	public FontRenderStack drawString(int x, int y, String message) {
		renderCharBuffer(message.toCharArray(), x, y);
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
			if (style.getColor() != null) color = style.getColor().getColor();
			glColor(color, 255f);
			renderCharBuffer(section.getText().toCharArray(), x, y);
		}
		offset = 0;
		return this;
	}

	private void renderCharBuffer(char[] buffer, int x, int y) {
		if (scaled) {
			x *= RenderStack.getScale();
			y *= RenderStack.getScale();
		}
		for (int character = 0; character < buffer.length; character++) {
			if (buffer[character] == ' ') {
				offset += font.getStringWidth(" ");
				continue;
			}
			if (!font.textureIDStore.containsKey(buffer[character]))
				buffer[character] = '?';
			int glId = font.textureIDStore.get(buffer[character]);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderSystem.setShaderTexture(0, glId);
			int width = font.textureDimensionsStore.get(buffer[character])[0],
					height = font.textureDimensionsStore.get(buffer[character])[1],
					shadow = font.getShadow();
			if (shadow > 0) {
				RenderSystem.setShaderColor(0f, 0f, 0f, this.alpha);
				drawQuads(x + offset + shadow, y + shadow, width, height);
			}
			RenderSystem.setShaderColor(this.red, this.green, this.blue, this.alpha);
			drawQuads(x + offset, y, width, height);
			offset += width;
		}
	}

	private void drawQuads(int x, int y, int width, int height) {
		Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, 0, 0, width, height, width, height);
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

}
