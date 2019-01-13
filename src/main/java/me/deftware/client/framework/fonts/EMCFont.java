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

    int drawStringDirectly(int x, int y, String text);

    int drawStringDirectly(int x, int y, String text, Color color);

    int drawOnScreen(int x, int y);

    void setShadowSize(int shadowSize);

    int drawOnScreenDirectly(int x, int y);

    int getStringWidth(String text);

    int getStringHeight(String text);

    int getLastRenderedHeight();

    int getLastRenderedWidth();

}
