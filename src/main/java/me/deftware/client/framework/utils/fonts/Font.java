package me.deftware.client.framework.utils.fonts;

import java.awt.*;

public interface Font {

    int generateString(String text, Color color);

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
