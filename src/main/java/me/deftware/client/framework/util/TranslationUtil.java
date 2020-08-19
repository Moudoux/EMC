package me.deftware.client.framework.util;

import net.minecraft.util.Language;

/**
 * @author Deftware
 */
public class TranslationUtil {

    public static String translate(String key) {
        return Language.getInstance().get(key);
    }

}
