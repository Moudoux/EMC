package me.deftware.mixin.imp;

import me.deftware.client.framework.fonts.EMCFont;
import net.minecraft.client.font.FontRenderer;

public interface IMixinGuiTextField {

    int getHeight();

    FontRenderer getFontRendererInstance();

    int getCursorCounter();

    int getSelectionEnd();

    int getLineScrollOffset();

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    int getWidth();

    void setHeight(int height);

    void setWidth(int width);

    void setUseMinecraftScaling(boolean state);

    void setUseCustomFont(boolean state);

    void setCustomFont(EMCFont font);

}
