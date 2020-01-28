package me.deftware.client.framework.wrappers.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IEnchantment {

    private Enchantment item;

    public IEnchantment(String name) {
        item = getByName(name).item;
    }

    public IEnchantment(Enchantment item) {
        this.item = item;
    }

    public Enchantment getEnchantment() {
        return item;
    }

    public String getName() {
        return new TranslatableText(item.getTranslationKey()).asString();
    }

    public String getTranslationKey() {
        return item.getTranslationKey();
    }

    public String getEnchantmentKey() {
        String key = getTranslationKey();
        if (key.startsWith("enchantment.minecraft")) {
            key = key.substring("enchantment.minecraft.".length());
        }
        return key;
    }

    public boolean isValidEnchant() {
        return item != null;
    }

    public static IEnchantment getByName(String id) {
        Identifier resourceLocation = new Identifier(id);
        if (Registry.ENCHANTMENT.containsId(resourceLocation)) {
            return new IEnchantment(Registry.ENCHANTMENT.get(resourceLocation));
        } else {
            for (Enchantment enchantment : Registry.ENCHANTMENT) {
                IEnchantment enchantmentObj = new IEnchantment(enchantment);
                if (enchantmentObj.getEnchantmentKey().equalsIgnoreCase(id)) {
                    return enchantmentObj;
                }
            }
        }
        return null;
    }

}
