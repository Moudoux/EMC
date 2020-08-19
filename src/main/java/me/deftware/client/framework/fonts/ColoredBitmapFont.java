package me.deftware.client.framework.fonts;

import me.deftware.client.framework.chat.style.ChatStyle;
import net.minecraft.util.Formatting;

import java.awt.*;

@SuppressWarnings("Duplicates")
@Deprecated
public class ColoredBitmapFont extends BitmapFont {

    public ColoredBitmapFont(String fontName, int fontSize, int modifiers) {
        super(fontName, fontSize, modifiers);
    }

    @Override
    public int drawString(int x, int y, String text, Color color) {
        if (color == Color.black) {
            return super.drawString(x, y, Formatting.strip(text), color);
        }
        if (text == null) {
            return 0;
        }

        text = text.replaceAll( ChatStyle.getFormattingChar() + "+[l-o]", "");
        text = text.replaceAll(ChatStyle.getFormattingChar() + "+[l-o]", "");

        String currentText = "", drawnText = "";
        boolean skip = false;
        for (String character : text.split("")) {
            if (skip) {
                color = ColorUtil.convertCharToColor(character);
                skip = false;
                currentText = "";
            } else {
                if (character.equalsIgnoreCase("&") || character.equalsIgnoreCase(Character.toString(ChatStyle.getFormattingChar()))) {
                    // Next char will be a color code
                    skip = true;
                    if (!currentText.equals("")) {
                        super.drawString(x + getStringWidthNonScaled(drawnText), y, currentText, color);
                        drawnText += currentText;
                    }
                } else {
                    currentText += character;
                }
            }
        }
        if (!currentText.equals("")) {
            super.drawString(x + getStringWidthNonScaled(drawnText), y, currentText, color);
        }


        return 0;
    }
}
