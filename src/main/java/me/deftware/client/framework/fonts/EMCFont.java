package me.deftware.client.framework.fonts;

import java.awt.*;

public interface EMCFont {

    int initialize (Color color, String extras);

    int generateString(String text, Color color);

    int drawString(int x, int y, String text);

    int drawString(int x, int y, String text, Color color);

    int drawStringWithShadow(int x, int y, String text);

    int drawStringWithShadow(int x, int y, String text, Color color);

    int drawCenteredString(int x, int y, String text);

    int drawCenteredString(int x, int y, String text, Color color);

    int drawCenteredStringWithShadow(int x, int y, String text);

    int drawCenteredStringWithShadow(int x, int y, String text, Color color);

    int drawOnScreen(int x, int y);

    int getStringWidth(String text);

    int getStringHeight(String text);

    int getLastRenderedHeight();

    int getLastRenderedWidth();

    void clearCache();

    void destroy();

    //Expose getters end setters

    String getFontName();

    void setFontName(String fontName);

    int getFontSize();
        
    void setFontSize(int fontSize);

    int getShadowSize();
        
    void setShadowSize(int shadowSize);

    boolean isBold();

    void setBold(boolean bold);

    boolean isItalics();

    void setItalics(boolean italics);

    boolean isUnderlined();

    void setUnderlined(boolean underlined);

    boolean isStriked();

    void setStriked(boolean striked);

    boolean isMoving();

    void setMoving(boolean moving);

    boolean isAntialiased();

    void setAntialiased(boolean antialiased);
    
    boolean isMemorysaving();

    void setMemorysaving(boolean memorysaving);

    public static class Modifiers {
        public static byte NONE = 0b00000000;
        public static byte BOLD = 0b00000001;
        public static byte ITALICS = 0b00000010;
        public static byte UNDERLINED = 0b00000100;
        public static byte STRIKED = 0b00001000;
        public static byte MOVING = 0b00010000;
        public static byte ANTIALIASED = 0b00100000;
        public static byte MEMORYSAVING = 0b01000000;
    }
}
