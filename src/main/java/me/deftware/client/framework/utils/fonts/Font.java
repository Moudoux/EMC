package me.deftware.client.framework.utils.fonts;


public interface Font {
    int drawString(int x, int y, String text);
    int getStringWidth(String text);
    int getStringHeight(String text);

}
