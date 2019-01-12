package me.deftware.client.framework.utils.fonts;


public interface Font {
    int generateString(String text);
    int prepareForRendering();
    int getStringWidth(String text);
    int getStringHeight(String text);
    int getLastRenderedHeight();
    int getLastRenderedWidth();

}
