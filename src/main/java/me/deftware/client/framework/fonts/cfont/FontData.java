package me.deftware.client.framework.fonts.cfont;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * Stores font information which is used within a font renderer.
 * Created by Brandon on 9/26/2016.
 */
public final class FontData {

	private final CharacterData[] characterBounds = new CharacterData[256];

	private int texId = -1;

	private int fontHeight = 0;

	private int textureWidth, textureHeight;

	public ArrayList<Integer> vbos = new ArrayList<>(), textures = new ArrayList<>();

	/**
	 * Creates a font image and the character locations within the font image.
	 */
	public FontData setFont(Font font, boolean antialias) {

		return setFont(font, antialias, antialias, 16, 2);
	}

	/**
	 * Creates a font image and the character locations within the font image.
	 */
	private FontData setFont(Font font, boolean antialias, boolean fractionalmetrics, int characterCount, int padding) {

		if (texId == -1)
			texId = getTexture();

		// Font metrics can be created from the font without having to create a graphics object.
		FontMetrics fontMetrics = new Canvas().getFontMetrics(font);

		int charHeight = 0, positionX = 0, positionY = 0; //, textureWidth = 0, textureHeight = 0

		// We'll be generating the character bounds as well as an appropriate texture width and height for the font to be rendered onto.
		for (int i = 0; i < characterBounds.length; i++) {
			char character = (char) i;

			int height = fontMetrics.getHeight();
			int width = fontMetrics.charWidth(character);

			if (i != 0 && i % characterCount == 0) {
				positionX = padding;
				positionY += charHeight + padding;
				charHeight = 0;
			}

			if (height > charHeight) {
				charHeight = height;
				if (charHeight > fontHeight)
					fontHeight = charHeight;
			}

			characterBounds[i] = new CharacterData(positionX, positionY, width, height);

			positionX += width + padding;

			// Ensure that our texture can fit the characters.
			if (positionX + width + padding > textureWidth)
				textureWidth = positionX + width + padding;

			if (positionY + height + padding > textureHeight)
				textureHeight = positionY + height + padding;
		}

		// Image we'll use to store our font onto for rendering.
		BufferedImage bufferedImage = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
		graphics2D.setFont(font);
		fontMetrics = graphics2D.getFontMetrics(font);

		// Give blank background
		graphics2D.setColor(new Color(255, 255, 255, 0));
		graphics2D.fillRect(0, 0, textureWidth, textureHeight);

		// Set color to white for rendering the font onto the texture.
		graphics2D.setColor(Color.WHITE);

		// Set render hints
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalmetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antialias ? RenderingHints.VALUE_TEXT_ANTIALIAS_GASP : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, antialias ? RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY : RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

		for (int i = 0; i < characterBounds.length; i++) {
			// Draw the char onto the final image we'll be using to render this font.
			graphics2D.drawString(String.valueOf((char) i), characterBounds[i].x, characterBounds[i].y + fontMetrics.getAscent());
		}

		applyTexture(texId, bufferedImage, antialias ? GL_LINEAR : GL_NEAREST, GL_REPEAT);
		return this;
	}

	public int getTexture() {
		int textureID = glGenTextures();
		textures.add(textureID);
		return textureID;
	}

	/**
	 * @param filter determines how the texture will interpolate when scaling up / down. <br>
	 *               GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
	 * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
	 *               GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
	 */
	public static int applyTexture(int texId, BufferedImage image, int filter, int wrap) {

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();
		applyTexture(texId, image.getWidth(), image.getHeight(), buffer, filter, wrap);
		return texId;
	}

	/**
	 * @param filter determines how the texture will interpolate when scaling up / down. <br>
	 *               GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
	 * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
	 *               GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
	 */
	public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, int filter, int wrap) {

		glBindTexture(GL_TEXTURE_2D, texId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		glBindTexture(GL_TEXTURE_2D, 0);
		return texId;
	}

	/**
	 * Binds the font texture.
	 */
	public void bind() {

		GlStateManager.bindTexture(texId);
	}

	/**
	 * @return The bounds of the character within the font image.
	 */
	public CharacterData getCharacterBounds(char character) {

		return characterBounds[character];
	}

	/**
	 * @return The total width of each character within the string.
	 */
	public int getStringWidth(String text) {

		int width = 0;
		for (char c : text.toCharArray()) {
			width += characterBounds[c].width;
		}
		return width;
	}

	/**
	 * @return True if the character has been mapped in this font.
	 */
	public boolean hasBounds(char character) {

		return character >= 0 && character < 256;
	}

	/**
	 * @return True if the font has not been set.
	 */
	public boolean hasFont() {

		return texId != -1;
	}

	public int getFontHeight() {

		return fontHeight;
	}

	public int getTextureWidth() {

		return textureWidth;
	}

	public int getTextureHeight() {

		return textureHeight;
	}

	/**
	 * Character information regarding it's position within the font texture and the character's width/height within the font.
	 */
	public class CharacterData {

		public final int x;

		public final int y;

		public final int width;

		public final int height;

		public CharacterData(int x, int y, int width, int height) {

			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

}