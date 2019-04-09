package me.deftware.client.framework.fonts;

import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.utils.render.GraphicsUtil;
import me.deftware.client.framework.utils.render.NonScaledRenderer;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class BitmapFont implements EMCFont {

    protected int lastRenderedWidth;
    protected int lastRenderedHeight;

    protected String fontName;
    protected int fontSize, shadowSize = 1;
    protected boolean bold;
    protected boolean italics;
    protected boolean underlined;
    protected boolean striked;
    protected boolean scaling;
    protected boolean antialiased;
    protected boolean memorysaving;
    protected Font stdFont;

    protected HashMap<Character, Integer> textureIDStore = new HashMap<>();
    protected HashMap<Character, int[]> textureDimensionsStore = new HashMap<>();

    public BitmapFont(String fontName, int fontSize, int modifiers) {
        this.fontName = fontName;
        this.fontSize = fontSize;

        this.bold = ((modifiers & 1) != 0);
        this.italics = ((modifiers & 2) != 0);
        this.underlined = ((modifiers & 4) != 0);
        this.striked = ((modifiers & 8) != 0);
        this.scaling = ((modifiers & 16) != 0);
        this.antialiased = ((modifiers & 32) != 0);
        this.memorysaving = ((modifiers & 64) != 0);

        prepareStandardFont();

        lastRenderedWidth = 0;
        lastRenderedHeight = 0;
    }

    protected void prepareStandardFont() {
        if (!bold && !italics) {
            if (FontManager.customFonts.containsKey(fontName)) {
                this.stdFont = FontManager.customFonts.get(fontName).deriveFont(Font.PLAIN, fontSize);
            } else {
                this.stdFont = new Font(fontName, Font.PLAIN, fontSize);
            }
        } else {
            if (bold && italics) {
                this.stdFont = new Font(fontName, Font.BOLD | java.awt.Font.ITALIC, fontSize);
            } else if (!bold) { //One of them must be false by now so we have to check only one
                this.stdFont = new Font(fontName, Font.ITALIC, fontSize);
            } else {
                this.stdFont = new Font(fontName, Font.BOLD, fontSize);
            }
        }
    }

    public void setScaled(boolean state) {
        scaling = state;
    }

    @Override
    public int initialize(Color color, String extras) {
        if (extras == null)
            extras = "";
        char[] additionalCharacters = extras.toCharArray();

        //Lowercase alphabet
        for (char lowercaseAlphabet = 'a'; lowercaseAlphabet <= 'z'; lowercaseAlphabet++) {
            characterGenerate(lowercaseAlphabet, color);
        }

        //Uppercase alphabet
        for (char uppercaseAlphabet = 'A'; uppercaseAlphabet <= 'Z'; uppercaseAlphabet++) {
            characterGenerate(uppercaseAlphabet, color);
        }

        //Numbers
        for (char numeric = 48; numeric <= 57; numeric++) { //0 - 9 in ASCII
            characterGenerate(numeric, color);
        }

        char specialCharacters[] = {'!', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
                ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~', '"'};

        if (additionalCharacters.length > 0)
            specialCharacters = ArrayUtils.addAll(specialCharacters, additionalCharacters);

        //Additional and special characters
        for (int additional = 0; additional < specialCharacters.length; additional++) { //0 - 9 in ASCII
            characterGenerate(specialCharacters[additional], color);
        }
        return 0;
    }

    protected void characterGenerate(char character, Color color) {
        String letterBuffer = String.valueOf(character);
        int textwidth = getStringWidthNonScaled(letterBuffer);
        int textheight = getStringHeightNonScaled(letterBuffer);

        BufferedImage characterTexture = new BufferedImage(textwidth, textheight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = characterTexture.createGraphics();
        graphics.setFont(stdFont);
        graphics.setColor(color);
        if (antialiased) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        }
        graphics.drawString(letterBuffer, 0, textheight - textheight / 4);
        graphics.dispose();

        textureIDStore.put(character, GraphicsUtil.loadTextureFromBufferedImage(characterTexture));
        textureDimensionsStore.put(character, new int[]{characterTexture.getWidth(), characterTexture.getHeight()});
    }

    /**
     * Unimplemented in BitmapFont
     *
     * @param text
     * @param color
     * @return 0
     */
    @Override
    public int generateString(String text, Color color) {
        return 0;
    }

    @Override
    public int drawString(int x, int y, String text) {
        drawString(x, y, text, null);
        return 0;
    }

    @Override
    public int drawString(int x, int y, String text, Color color) {
        if (scaling) {
            x *= NonScaledRenderer.getScale();
            y *= NonScaledRenderer.getScale();
        }

        char[] buffer = text.toCharArray();

        GL11.glPushMatrix();
        GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
        int offset = 0;
        for (int character = 0; character < buffer.length; character++) {

            if (buffer[character] == ' ') {
                offset += getStringWidthNonScaled(" ");
                continue;
            } else if (!textureIDStore.containsKey(buffer[character])) {
                buffer[character] = '?';
            }
            if (color != null) {
                float red = color.getRed() > 0 ? color.getRed() * (1f / 255f) : 0;
                float green = color.getGreen() > 0 ? color.getGreen() * (1f / 255f) : 0;
                float blue = color.getBlue() > 0 ? color.getBlue() * (1f / 255f) : 0;
                float alpha = color.getAlpha() > 0 ? color.getAlpha() * (1f / 255f) : 0;
                GL11.glColor4f(red, green, blue, alpha);
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIDStore.get(buffer[character]));
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            int width = textureDimensionsStore.get(buffer[character])[0];
            int height = textureDimensionsStore.get(buffer[character])[1];
            GraphicsUtil.drawQuads(x + offset, y, width, height); //GL PART //8745

            offset += width;
        }

        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        IMinecraft.triggerGuiRenderer();

        lastRenderedWidth = offset;
        return 0;
    }

    @Override
    public int drawStringWithShadow(int x, int y, String text) {
        drawStringWithShadow(x, y, text, Color.white);
        return 0;
    }

    @Override
    public int drawStringWithShadow(int x, int y, String text, Color color) {
        drawString(x + shadowSize, y + shadowSize, text, Color.black);
        drawString(x, y, text, color);
        return 0;
    }

    @Override
    public int drawCenteredString(int x, int y, String text) {
        drawCenteredString(x, y, text, Color.white);
        return 0;
    }

    @Override
    public int drawCenteredString(int x, int y, String text, Color color) {
        drawString(x - (getStringWidthNonScaled(ChatColor.stripColor(text)) / 2), y - (getStringHeightNonScaled(ChatColor.stripColor(text)) / 2), text, color);
        return 0;
    }

    @Override
    public int drawCenteredStringWithShadow(int x, int y, String text) {
        drawCenteredStringWithShadow(x, y, text, Color.white);
        return 0;
    }

    @Override
    public int drawCenteredStringWithShadow(int x, int y, String text, Color color) {
        drawCenteredString(x + shadowSize, y + shadowSize, text, Color.black);
        drawCenteredString(x, y, text, color);
        return 0;
    }

    @Override
    public int drawOnScreen(int x, int y) {
        return 0;
    }

    @Override
    public int getStringWidth(String text) {
        if (!scaling) {
            return getStringWidthNonScaled(text);
        }
        FontMetrics fontMetrics = new Canvas().getFontMetrics(stdFont);
        return (int) (fontMetrics.charsWidth(text.toCharArray(), 0, text.length()) / NonScaledRenderer.getScale());
    }

    public int getStringWidthNonScaled(String text) {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(stdFont);
        return fontMetrics.charsWidth(text.toCharArray(), 0, text.length());
    }

    @Override
    public int getStringHeight(String text) {
        if (!scaling) {
            return getStringHeightNonScaled(text);
        }
        FontMetrics fontMetrics = new Canvas().getFontMetrics(stdFont);
        return (int) (fontMetrics.getHeight() / NonScaledRenderer.getScale());
    }

    public int getStringHeightNonScaled(String text) {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(stdFont);
        return fontMetrics.getHeight();
    }

    @Override
    public int getLastRenderedHeight() {
        return lastRenderedHeight;
    }

    @Override
    public int getLastRenderedWidth() {
        return lastRenderedWidth;
    }

    @Override
    public void clearCache() {
        Bootstrap.logger.error("Calling clearCache() on BitmapFont is forbidden!");
    }

    @Override
    public void destroy() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); //Bind texture to 0 - unbind everything
        for (Character key : textureIDStore.keySet()) {
            GL11.glDeleteTextures(textureIDStore.get(key));
        }
        textureIDStore.clear();
        textureDimensionsStore.clear();
    }

    @Override
    public String getFontName() {
        return fontName;
    }

    @Override
    public void setFontName(String fontName) {
        this.fontName = fontName;
        prepareStandardFont();
    }

    @Override
    public int getFontSize() {
        return fontSize;
    }

    @Override
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        prepareStandardFont();
    }

    @Override
    public int getShadowSize() {
        return shadowSize;
    }

    @Override
    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
    }

    @Override
    public boolean isBold() {
        return bold;
    }

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    @Override
    public boolean isItalics() {
        return italics;
    }

    @Override
    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    @Override
    public boolean isUnderlined() {
        return underlined;
    }

    @Override
    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    @Override
    public boolean isStriked() {
        return striked;
    }

    @Override
    public void setStriked(boolean striked) {
        this.striked = striked;
    }

    @Override
    public boolean isAntialiased() {
        return antialiased;
    }

    @Override
    public void setAntialiased(boolean antialiased) {
        this.antialiased = antialiased;
    }

    @Override
    public boolean isMemorysaving() {
        return memorysaving;
    }

    @Override
    public void setMemorysaving(boolean memorysaving) {
        this.memorysaving = memorysaving;
    }
}
