package me.deftware.client.framework.utils;

import net.minecraft.util.Language;

public class TranslationUtil {

    public static String translate(String key) {
        return Language.getInstance().translate(key);
    }

}
