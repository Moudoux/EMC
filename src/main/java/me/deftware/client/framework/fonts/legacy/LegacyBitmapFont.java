package me.deftware.client.framework.fonts.legacy;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.registry.font.TTFRegistry;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.texture.GraphicsUtil;
import me.deftware.client.framework.util.path.OSUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Legacy bitmap font used for now
 *
 * @author Deftware, Ananas
 */
public class  LegacyBitmapFont {

	public final HashMap<Character, Integer> textureIDStore = new HashMap<>();
	public final HashMap<Character, int[]> textureDimensionsStore = new HashMap<>();

	protected Font stdFont;
	public String fontName;
	protected int fontSize;
	public boolean scaled;

	public @Setter @Getter int shadow = 1;

	private FontMetrics metrics;

	public LegacyBitmapFont(String fontName, int fontSize, boolean scaled) {
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.scaled = scaled;
		RenderStack.scaleChangeCallback.add(() -> {
			setupFont();
			initialize();
		});
		setupFont();
	}

	public LegacyBitmapFont(String fontName, int fontSize) {
		this(fontName, fontSize, true);
	}

	public void setupFont() {
		Font fallback = new Font(this.fontName, Font.PLAIN, this.fontSize);
		if (OSUtils.isWindows()) {
			File font = Paths.get(System.getenv("LOCALAPPDATA"), "Microsoft", "Windows", "Fonts", fontName + ".ttf").toFile();
			if (font.exists()) {
				try {
					fallback = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(font.getAbsolutePath()));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		this.stdFont = TTFRegistry.getFont(this.fontName, fallback)
				.deriveFont(Font.PLAIN, fontSize * (scaled ? RenderStack.getScale() : 1f));
		this.metrics = new Canvas().getFontMetrics(this.stdFont);
	}

	public void initialize() {
		// Lowercase alphabet
		for (char lowercaseAlphabet = 'a'; lowercaseAlphabet <= 'z'; lowercaseAlphabet++) {
			characterGenerate(lowercaseAlphabet);
		}
		// Uppercase alphabet
		for (char uppercaseAlphabet = 'A'; uppercaseAlphabet <= 'Z'; uppercaseAlphabet++) {
			characterGenerate(uppercaseAlphabet);
		}
		// Numbers
		for (char numeric = 48; numeric <= 57; numeric++) { // 0 - 9 in ASCII
			characterGenerate(numeric);
		}
		// Additional and special characters
		char[] specialCharacters = {'!', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
				':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~', '"'};
		for (char specialCharacter : specialCharacters) {
			characterGenerate(specialCharacter);
		}
	}

	public int getStringWidth(String text) {
		return metrics.charsWidth(text.toCharArray(), 0, text.length());
	}

	public int getStringHeight() {
		return metrics.getHeight();
	}

	protected void characterGenerate(char character) {
		String letterBuffer = String.valueOf(character);
		int textWidth = getStringWidth(letterBuffer), textHeight = getStringHeight();
		if (textHeight > 0 && textWidth > 0 && !letterBuffer.isEmpty()) {
			BufferedImage characterTexture = new BufferedImage(textWidth, textHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = characterTexture.createGraphics();
			graphics.setFont(stdFont);
			graphics.setColor(Color.white);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			graphics.drawString(letterBuffer, 0, textHeight - textHeight / 4);
			graphics.dispose();
			textureIDStore.put(character, GraphicsUtil.loadTextureFromBufferedImage(characterTexture));
			textureDimensionsStore.put(character, new int[]{characterTexture.getWidth(), characterTexture.getHeight()});
		}
	}

	public void destroy() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textureIDStore.values().forEach(GL11::glDeleteTextures);
		textureIDStore.clear();
		textureDimensionsStore.clear();
	}

}
