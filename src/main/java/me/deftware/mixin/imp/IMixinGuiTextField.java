package me.deftware.mixin.imp;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;

import java.util.function.BiFunction;

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

    int getMaxTextLength();

    boolean getHasBorder();

    boolean getIsEditable();

    BiFunction<String, Integer, OrderedText> getRenderTextProvider();

    String getSuggestion();

    int getCursorMax();

    void setOverlay(boolean flag);

    void setPasswordField(boolean flag);

}
