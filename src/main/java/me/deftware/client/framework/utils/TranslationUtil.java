package me.deftware.client.framework.utils;

import net.minecraft.util.text.translation.LanguageMap;

public class TranslationUtil {

    public static String translate(String key) {
        return LanguageMap.getInstance().translateKey(key);
    }

}
