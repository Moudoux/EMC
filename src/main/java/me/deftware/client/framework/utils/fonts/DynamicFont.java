package me.deftware.client.framework.utils.fonts;

import java.awt.*;

public class DynamicFont implements Font{
    private java.awt.Font stdFont;
    private String fontName;
    private int fontSize;
    private Color fontColor;
    private boolean bold;

    public DynamicFont(String fontName, int fontSize, Color fontColor, int modifiers) {
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.stdFont = new java.awt.Font(fontName, 0, fontSize);
        //int mod = DynamicFont.Modifiers.NONE | DynamicFont.Modifiers.BOLD;
    }

    @Override
    public int drawString(int x, int y, String text) {
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

    public static class Modifiers{
        public static byte NONE = 0b00000000;
        public static byte  BOLD = 0b00000001;
        public static int ITALICS =0b00000010;
        public static int UNDERLINED = 0b00000100;
        public static int STRIKED = 0b00001000;
        public static int MOVING = 0b00010000;
        public static int MEMORYSAVING = 0b00100000;
    }
}
