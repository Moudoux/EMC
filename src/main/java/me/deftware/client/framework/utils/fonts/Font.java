package me.deftware.client.framework.utils.fonts;


public interface Font {
    int generateString(String text);

    int prepareForRendering();

    int drawString(int x, int y, String text);

    int drawCenteredString(int x, int y, String text);

    int drawOnScreen(int x, int y);

    int drawOnScreenDirectly(int x, int y);

    int getStringWidth(String text);

    int getStringHeight(String text);

    int getLastRenderedHeight();

    int getLastRenderedWidth();

}
