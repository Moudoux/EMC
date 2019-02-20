package me.deftware.mixin.imp;

import me.deftware.client.framework.fonts.EMCFont;
import net.minecraft.client.font.TextRenderer;

public interface IMixinGuiTextField {

    int getHeight();

    void setHeight(int height);

    TextRenderer getFontRendererInstance();

    int getCursorCounter();

    int getSelectionEnd();

    int getLineScrollOffset();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    void setWidth(int width);

    void setUseMinecraftScaling(boolean state);

    void setUseCustomFont(boolean state);

    void setCustomFont(EMCFont font);

}
