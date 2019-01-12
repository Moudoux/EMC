package me.deftware.client.framework.utils.fonts;

import me.deftware.client.framework.utils.Texture;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.BitSet;

public class DynamicFont implements Font {

    private String fontName;
    private int fontSize;
    private Color fontColor;
    private boolean bold;
    private boolean italics;
    private boolean underlined;
    private boolean striked;
    private boolean moving;
    private boolean memorysaving;
    private java.awt.Font stdFont;

    Texture textTexture;
    String lastRenderedString;
    int lastRenderedWidth;
    int lastRenderedHeight;
    int rendererTaskId;
    AffineTransform affineTransform;
    FontRenderContext renderContext;

    public DynamicFont(@Nonnull String fontName, @Nonnull Color fontColor, int fontSize, int modifiers) {
        this.fontName = fontName;
        this.fontColor = fontColor;
        this.fontSize = fontSize;

        this.bold = new BitSet(modifiers).get(7);
        this.italics = new BitSet(modifiers).get(6);
        this.underlined = new BitSet(modifiers).get(5);
        this.striked = new BitSet(modifiers).get(4);
        this.moving = new BitSet(modifiers).get(3);
        this.memorysaving = new BitSet(modifiers).get(2);

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

        this.stdFont = new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize);

        textTexture = null;
        lastRenderedString = "";
        rendererTaskId = 0;
        lastRenderedWidth = 0;
        lastRenderedHeight = 0;
        affineTransform = new AffineTransform();
        renderContext = new FontRenderContext(affineTransform, false, true);
    }

    private FontRenderContext getRenderContext() {
        return this.renderContext;
    }

    @Override
    public int generateString(String text) {
        if (!text.equals(lastRenderedString)) { //Optimisation. Generate only if needed
            int textwidth = getStringWidth(text);
            int textheight = getStringHeight(text);
            BufferedImage premadeTexture = new BufferedImage(textwidth, textheight, BufferedImage.TYPE_INT_ARGB);
            //BufferedImage premadeTexture = new BufferedImage(200, 30, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = premadeTexture.createGraphics();
            graphics.setColor(fontColor);
            graphics.setFont(stdFont);
            graphics.drawString(text, 1, textheight - 1);
            graphics.dispose();
            textTexture = new Texture(textheight, textheight, true);
            textTexture.fillFromBufferedImage(premadeTexture);
            textTexture.update();

            lastRenderedString = text;
            lastRenderedWidth = textwidth;
            lastRenderedHeight = textheight;
        }
        else
            return 1;
        return 0;
    }

    @Override
    public int prepareForRendering(){
        if(textTexture != null) {
            textTexture.bind(GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
        else
            return 1;
        return 0;
    }

    @Override
    public int getStringWidth(String text) {
        return (int) (stdFont.getStringBounds(text, getRenderContext()).getWidth()) + 2;
        //Add one pixel margin at the beginning and at the end
    }

    @Override
    public int getStringHeight(String text) {
        return (int) (stdFont.getStringBounds(text, getRenderContext()).getHeight()) + 2;
    }

    @Override
    public int getLastRenderedWidth() {
        return lastRenderedWidth;
        //Add one pixel margin at the beginning and at the end
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
        public static byte MEMORYSAVING = 0b00100000;
    }
}
