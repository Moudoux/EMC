package me.deftware.client.framework.fonts;

import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.utils.render.ColorUtil;
import me.deftware.client.framework.utils.render.Texture;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.image.BufferedImage;

import static me.deftware.client.framework.utils.ChatColor.COLOR_CHAR;

public class ColoredDynamicFont extends DynamicFont {

    public ColoredDynamicFont(@Nonnull String fontName, int fontSize, int modifiers) {
        super(fontName, fontSize, modifiers);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public int generateString(String text, Color color) {
        if (color == Color.black) {
            // Assume its a shadow
            return super.generateString(ChatColor.stripColor(text), color);
        }
        String key = text + color.getRGB() + bold + fontName;
        int textwidth = getStringWidth(text);
        int textheight = getStringHeight(text);
        if (!memorysaving && textureStore.containsKey(key)) {
            textTexture = textureStore.get(key);
        } else {
            BufferedImage premadeTexture = new BufferedImage(textwidth, textheight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = premadeTexture.createGraphics();
            graphics.setFont(stdFont);
            graphics.setColor(color);
            if (antialiased) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            }

            // Remove formatting codes as we dont support those...
            text = text.replaceAll(Character.toString(COLOR_CHAR) + "+[l-o]", "");
            text = text.replaceAll(Character.toString(COLOR_CHAR) + "+[l-o]", "");

            String currentText = "", drawnText = "";
            boolean skip = false;
            for (String character : text.split("")) {
                if (skip) {
                    graphics.setColor(ColorUtil.convertCharToColor(character));
                    skip = false;
                    currentText = "";
                } else {
                    if (character.equalsIgnoreCase("&") || character.equalsIgnoreCase(Character.toString(COLOR_CHAR))) {
                        // Next char will be a color code
                        skip = true;
                        if (!currentText.equals("")) {
                            graphics.drawString(currentText, getStringWidth(drawnText) + 1, textheight - textheight / 4);
                            drawnText += currentText;
                        }
                    } else {
                        currentText += character;
                    }
                }
            }
            if (!currentText.equals("")) {
                graphics.drawString(currentText, getStringWidth(drawnText) + 1, textheight - textheight / 4);
            }

            graphics.dispose();
            textTexture = new Texture(textwidth, textheight, true);
            textTexture.fillFromBufferedImageFlip(premadeTexture);
            textTexture.update();
            if (!memorysaving)
                textureStore.put(key, textTexture);
        }
        lastRenderedWidth = textwidth;
        lastRenderedHeight = textheight;
        return 0;
    }

    @Override
    public int getStringWidth(String text){
        return super.getStringWidth(ChatColor.stripColor(text));
    }

}
