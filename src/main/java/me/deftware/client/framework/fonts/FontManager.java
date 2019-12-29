package me.deftware.client.framework.fonts;

import me.deftware.client.framework.main.bootstrap.Bootstrap;

import java.awt.*;
import java.util.HashMap;

public class FontManager {

    private static HashMap<String, EMCFont> fontStore = new HashMap<>();
    public static HashMap<String, Font> customFonts = new HashMap<>();

    public static EMCFont getFont(String name, int size, int modifiers, Class<?> type) {
        String key = name + size + modifiers;
        if (fontStore.containsKey(key)) {
            return fontStore.get(key);
        }
        if (type == ColoredDynamicFont.class) {
            fontStore.put(key, new ColoredDynamicFont(name, size, modifiers));
        } else if (type == BitmapFont.class) {
            fontStore.put(key, new BitmapFont(name, size, modifiers));
        } else if (type == ColoredBitmapFont.class) {
            fontStore.put(key, new ColoredBitmapFont(name, size, modifiers));
        } else {
            fontStore.put(key, new DynamicFont(name, size, modifiers));
        }
        return fontStore.get(key);
    }

    public static EMCFont getFont(String name, int size, int modifiers) {
        return getFont(name, size, modifiers, DynamicFont.class);
    }

    public static void registerCustomFont(Font font) {
        customFonts.putIfAbsent(font.getFontName(), font);
        Bootstrap.logger.info("Registered custom font " + font.getFontName());
    }

    public static void removeFont(String name, int size, int modifiers) {
        fontStore.remove(name + size + modifiers);
    }

    public static void clearCache() {
        fontStore.clear();
    }

}
