package me.deftware.client.framework.fonts;

import me.deftware.client.framework.utils.Texture;
import me.deftware.client.framework.wrappers.IMinecraft;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.BitSet;
import java.util.HashMap;

public class DynamicFont implements EMCFont {

    private Texture textTexture;
    private int lastRenderedWidth;
    private int lastRenderedHeight;
    private int rendererTaskId;
    private AffineTransform affineTransform;
    private FontRenderContext renderContext;

    private String fontName;
    private int fontSize, shadowSize = 1;
    private boolean bold;
    private boolean italics;
    private boolean underlined;
    private boolean striked;
    private boolean moving;
    private boolean antialiased;
    private java.awt.Font stdFont;

    private HashMap<String, Texture> textureStore = new HashMap<>();

    public DynamicFont(@Nonnull String fontName, int fontSize, int modifiers) {
        this.fontName = fontName;
        this.fontSize = fontSize;

        this.bold = new BitSet(modifiers).get(7);
        this.italics = new BitSet(modifiers).get(6);
        this.underlined = new BitSet(modifiers).get(5);
        this.striked = new BitSet(modifiers).get(4);
        this.moving = new BitSet(modifiers).get(3);
        this.antialiased = new BitSet(modifiers).get(2);

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

        textTexture = null;
        rendererTaskId = 0;
        lastRenderedWidth = 0;
        lastRenderedHeight = 0;
        affineTransform = new AffineTransform();
        renderContext = new FontRenderContext(affineTransform, false, true);
    }

    public void clearCache() {
        textureStore.clear();
    }

    @Override
    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setAntialiased(boolean antialiased) {
        this.antialiased = antialiased;
    }

    public void prepareAndPushMatrix() {
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST); //3008 alpha test
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void renderAndPopMatrix(int x, int y, int width, int height, boolean autoAdjust) {
        int scale = 2;

        //Counter the default scaling
        if (autoAdjust)
            scale = IMinecraft.getGuiScale();

        if (scale == 0)
            scale = 2;

        //Draw quad counterclockwise
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2d((x) * (scale * 0.25), (y) * (scale * 0.25)); //top-left
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2d((x) * (scale * 0.25), (y + height) * (scale * 0.25)); //down-left aka height
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2d((x + width) * (scale * 0.25), (y + height) * (scale * 0.25)); //down-right aka width
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2d((x + width) * (scale * 0.25), (y) * (scale * 0.25)); //top-right
        }
        GL11.glEnd();

        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    private FontRenderContext getRenderContext() {
        return this.renderContext;
    }

    @Override
    public int generateString(String text, Color color) {
        String key = text + color.getRGB() + bold + fontName;
        int textwidth = getStringWidth(text);
        int textheight = getStringHeight(text);
        if (textureStore.containsKey(key)) {
            textTexture = textureStore.get(key);
        } else {
            BufferedImage premadeTexture = new BufferedImage(textwidth, textheight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = premadeTexture.createGraphics();
            graphics.setColor(color);
            graphics.setFont(stdFont);
            if (antialiased)
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.drawString(text, 1, textheight - 8);
            graphics.dispose();
            textTexture = new Texture(textwidth, textheight, true);
            textTexture.fillFromBufferedImageFlip(premadeTexture);
            textTexture.update();
            textureStore.put(key, textTexture);
        }
        lastRenderedWidth = textwidth;
        lastRenderedHeight = textheight;
        return 0;
    }

    @Override
    public int prepareForRendering() {
        if (textTexture != null) {
            textTexture.updateTexture();
            textTexture.bind(GL11.GL_ONE_MINUS_SRC_ALPHA);
        } else
            return 1;
        return 0;
    }

    @Override
    public int drawString(int x, int y, String text) {
        return drawString(x, y, text, Color.white);
    }

    @Override
    public int drawString(int x, int y, String text, Color color) {
        generateString(text, color);
        drawOnScreen(x, y);
        return 0;
    }

    @Override
    public int drawStringWithShadow(int x, int y, String text) {
        return drawStringWithShadow(x, y, text, Color.white);
    }

    @Override
    public int drawStringWithShadow(int x, int y, String text, Color color) {
        generateString(text, Color.black);
        drawOnScreen(x + shadowSize, y + shadowSize);
        generateString(text, color);
        drawOnScreen(x, y);
        return 0;
    }

    @Override
    public int drawCenteredString(int x, int y, String text) {
        generateString(text, Color.white);
        drawOnScreen(x - getLastRenderedWidth() / 2, y - getLastRenderedHeight() / 2);
        return 0;
    }

    @Override
    public int drawCenteredString(int x, int y, String text, Color color) {
        generateString(text, color);
        drawOnScreen(x - getLastRenderedWidth() / 2, y - getLastRenderedHeight() / 2);
        return 0;
    }

    @Override
    public int drawCenteredStringWithShadow(int x, int y, String text) {
        return drawCenteredStringWithShadow(x, y, text, Color.white);
    }

    @Override
    public int drawCenteredStringWithShadow(int x, int y, String text, Color color) {
        drawCenteredString(x + shadowSize, y + shadowSize, text, Color.black);
        drawCenteredString(x, y, text, color);
        return 0;
    }

    @Override
    public int drawOnScreen(int x, int y) {
        prepareAndPushMatrix(); //GL PART
        prepareForRendering(); //BINDING
        renderAndPopMatrix(x, y, getLastRenderedWidth(), getLastRenderedHeight(), true); //GL PART
        return 0;
    }

    @Override
    public int drawOnScreenDirectly(int x, int y) {
        prepareAndPushMatrix();
        prepareForRendering();
        renderAndPopMatrix(x, y, getLastRenderedWidth(), getLastRenderedHeight(), false);
        return 0;
    }

    @Override
    public int getStringWidth(String text) {
        return (int) (stdFont.getStringBounds(text, getRenderContext()).getWidth()) + 2;
        //+1px margins
    }

    @Override
    public int getStringHeight(String text) {
        return (int) (stdFont.getStringBounds(text, getRenderContext()).getHeight()) + 10;
        //+1px margins + 8px for tails
    }

    @Override
    public int getLastRenderedWidth() {
        return lastRenderedWidth;
    }

    @Override
    public int getLastRenderedHeight() {
        return lastRenderedHeight;
    }

    public static class Modifiers {
        public static byte NONE = 0b00000000;
        public static byte BOLD = 0b00000001;
        public static byte ITALICS = 0b00000010;
        public static byte UNDERLINED = 0b00000100;
        public static byte STRIKED = 0b00001000;
        public static byte MOVING = 0b00010000;
        public static byte ANTIALIASED = 0b00100000;
    }

}
