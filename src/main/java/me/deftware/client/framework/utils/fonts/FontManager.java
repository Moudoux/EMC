package me.deftware.client.framework.utils.fonts;

import java.util.HashMap;

public class FontManager {

    private static HashMap<String, DynamicFont> fontStore = new HashMap<>();

    public static DynamicFont getFont(String name, int size, int modifiers) {
        String key = name + size + modifiers;
        if (fontStore.containsKey(key)) {
            return fontStore.get(key);
        }
        fontStore.put(key, new DynamicFont(name, size, modifiers));
        return fontStore.get(key);
    }

    public static void removeFont(String name, int size, int modifiers) {
        if (fontStore.containsKey(name + size + modifiers)) {
            fontStore.remove(name + size + modifiers);
        }
    }

    public static void clearCache() {
        fontStore.clear();
    }

}
