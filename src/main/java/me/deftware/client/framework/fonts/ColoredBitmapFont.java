package me.deftware.client.framework.fonts;

import me.deftware.client.framework.utils.ColorUtil;
import me.deftware.client.framework.utils.TexUtil;
import me.deftware.client.framework.utils.Texture;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class ColoredBitmapFont extends BitmapFont {

    public ColoredBitmapFont(@Nonnull String fontName, int fontSize, int modifiers) {
        super(fontName, fontSize, modifiers);
    }

    @Override
    public int drawString(int x, int y, String text, Color color) {
        int textheight = getStringHeight(text);

        text = text.replace("&l", "")
                .replace("&m", "")
                .replace("&n", "")
                .replace("&o", "");

        text = text.replace("§l", "")
                .replace("§m", "")
                .replace("§n", "")
                .replace("§o", "");

        String currentText = "", drawnText = "";
        boolean skip = false;
        for (String character : text.split("")) {
            if (skip) {
                color = ColorUtil.convertCharToColor(character);
                skip = false;
                currentText = "";
            } else {
                if (character.equalsIgnoreCase("&") || character.equalsIgnoreCase("§")) {
                    // Next char will be a color code
                    skip = true;
                    if (!currentText.equals("")) {
                        super.drawString(x + getStringWidth(drawnText), y, currentText, color);
                        drawnText += currentText;
                    }
                } else {
                    currentText += character;
                }
            }
        }
        if (!currentText.equals("")) {
            super.drawString(x + getStringWidth(drawnText), y, currentText, color);
        }


        return 0;
    }
}
