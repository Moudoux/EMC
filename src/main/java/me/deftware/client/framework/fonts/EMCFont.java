package me.deftware.client.framework.fonts;

import java.awt.*;

public interface EMCFont {

    int generateString(String text, Color color);

    int prepareForRendering();

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

}
