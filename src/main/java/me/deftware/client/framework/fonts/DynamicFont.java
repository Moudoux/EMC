package me.deftware.client.framework.fonts;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.texture.GraphicsUtil;
import me.deftware.client.framework.render.texture.Texture;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@SuppressWarnings("ALL")
@Deprecated
public class DynamicFont implements EMCFont {

    protected Texture textTexture;
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
    protected java.awt.Font stdFont;

    protected HashMap<String, Texture> textureStore = new HashMap<>();

    public DynamicFont(String fontName, int fontSize, int modifiers) {
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

        textTexture = null;
        lastRenderedWidth = 0;
        lastRenderedHeight = 0;
    }

    protected void prepareStandardFont() {
        if (!bold && !italics) {
            this.stdFont = new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize);
        } else {
            if (bold && italics) {
                this.stdFont = new java.awt.Font(fontName, java.awt.Font.BOLD | java.awt.Font.ITALIC, fontSize);
            } else if (!bold) { //One of them must be false by now so we have to check only one
                this.stdFont = new java.awt.Font(fontName, java.awt.Font.ITALIC, fontSize);
            } else {
                this.stdFont = new java.awt.Font(fontName, java.awt.Font.BOLD, fontSize);
            }
        }
    }

    protected int prepareForRendering() {
        if (textTexture != null) {
            textTexture.updateTexture();
            textTexture.bind(GL11.GL_ONE_MINUS_SRC_ALPHA);
        } else
            return 1;
        return 0;
    }

    @Override
    public int initialize(Color color, String extras) {
        return 0;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public int generateString(String text, Color color) {
        String key = text + color.getRGB() + bold + fontName;
        int textwidth = getStringWidthNonScaled(text);
        int textheight = getStringHeightNonScaled(text);
        if (!memorysaving && textureStore.containsKey(key)) {
            textTexture = textureStore.get(key);
        } else {
            BufferedImage premadeTexture = new BufferedImage(textwidth, textheight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = premadeTexture.createGraphics();
            graphics.setFont(stdFont);
            graphics.setColor(color);
            if (antialiased) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            }
            graphics.drawString(text, 1, textheight - textheight / 4);
            graphics.dispose();
            textTexture = new Texture(textwidth, textheight, true);
            textTexture.fillFromBufferedImageFlip(premadeTexture);
            textTexture.update();
            if (!memorysaving)
                textureStore.put(key, textTexture);
        }
        lastRenderedWidth = textwidth;
        lastRenderedHeight = textheight;
        return 0;
    }

    @Override
    public int drawString(int x, int y, String text) {
        return drawString(x, y, text, Color.white, Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
    }

    @Override
    public int drawStringWithShadow(int x, int y, String text) {
        return drawString(x, y, text, Color.white, Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
    }

    @Override
    public int drawString(int x, int y, String text, boolean shadow) {
        return drawString(x, y, text, Color.white, shadow);
    }

    @Override
    public int drawStringWithShadow(int x, int y, String text, Color color) {
        return drawString(x, y, text, color, Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
    }

    @Override
    public int drawString(int x, int y, String text, Color color, boolean shadow) {
        if (shadow) {
            generateString(text, Color.black);
            drawOnScreen(x + shadowSize, y + shadowSize);
        }
        generateString(text, color);
        drawOnScreen(x, y);
        return 0;
    }

    @Override
    public int drawString(int x, int y, String text, Color color) {
        generateString(text, color);
        drawOnScreen(x - 1, y);
        return 0;
    }

    @Override
    public int drawCenteredString(int x, int y, String text) {
        return drawCenteredString(x, y, text, Color.white, Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
    }

    @Override
    public int drawCenteredStringWithShadow(int x, int y, String text) {
        return drawCenteredString(x, y, text, Color.white, Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
    }

    @Override
    public int drawCenteredString(int x, int y, String text, boolean shadow) {
        return drawCenteredString(x, y, text, Color.white, shadow);
    }
    
    @Override
    public int drawCenteredStringWithShadow(int x, int y, String text, Color color) {
        return drawCenteredString(x, y, text, color, Bootstrap.EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
    }

    @Override
    public int drawCenteredString(int x, int y, String text, Color color, boolean shadow) {
        if (shadow) {
            drawCenteredString(x + shadowSize, y + shadowSize, text, Color.black);
        }
        drawCenteredString(x, y, text, color);
        return 0;
    }

    @Override
    public int drawCenteredString(int x, int y, String text, Color color) {
        generateString(text, color);
        drawOnScreen(x - getLastRenderedWidth() / 2, y - getLastRenderedHeight() / 2);
        return 0;
    }

    @Override
    public int drawOnScreen(int x, int y) {
        GL11.glPushMatrix();
        GraphicsUtil.prepareMatrix(getLastRenderedWidth(), getLastRenderedHeight());
        prepareForRendering(); //BINDING
        GraphicsUtil.drawQuads(x, y, getLastRenderedWidth(), getLastRenderedHeight());
        GL11.glPopMatrix();
        RenderStack.reloadMinecraftMatrix();
        return 0;
    }

    @Override
    public int getStringWidth(String text) {
        if (!scaling) {
            return getStringWidthNonScaled(text);
        }
        FontMetrics fontMetrics = new Canvas().getFontMetrics(stdFont);
        return (int) (fontMetrics.charsWidth(text.toCharArray(), 0, text.length()) / RenderStack.getScale());
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
        return (int) (fontMetrics.getHeight() / RenderStack.getScale());
    }

    public int getStringHeightNonScaled(String text) {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(stdFont);
        return fontMetrics.getHeight();
    }

    @Override
    public int getLastRenderedWidth() {
        return lastRenderedWidth;
    }

    @Override
    public int getLastRenderedHeight() {
        return lastRenderedHeight;
    }

    @Override
    public void clearCache() {
        for (String key : textureStore.keySet()) {
            textureStore.get(key).destroy();
        }
        textureStore.clear();
    }

    @Override
    public void destroy() {
        clearCache();
        textTexture.destroy();
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
    public void setScaled(boolean state) {
        scaling = state;
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
