package me.deftware.client.framework.utils.fonts;

import java.awt.*;

public class BitmapFont implements Font {

    private String fontName;
    private int fontSize;
    private Color fontColor;
    private boolean bold;
    private java.awt.Font stdFont;

    public BitmapFont(String fontName, int fontSize, Color fontColor) {
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.stdFont = new java.awt.Font(fontName, 0, fontSize);
        //int mod = DynamicFont.Modifiers.NONE | DynamicFont.Modifiers.BOLD;
    }

    @Override
    public int generateString(String text) {
        return 0;
    }

    @Override
    public int prepareForRendering() {
        return 0;
    }

    @Override
    public int getStringWidth(String text) {
        return 0;
    }

    @Override
    public int getStringHeight(String text) {
        return 0;
    }

    @Override
    public int getLastRenderedHeight() {
        return 0;
    }

    @Override
    public int getLastRenderedWidth() {
        return 0;
    }
}
