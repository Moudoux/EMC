package me.deftware.client.framework.wrappers.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IEnchantment {

    private Enchantment item;

    public IEnchantment(String name) {
        item = getByName(name);
    }

    public IEnchantment(Enchantment item) {
        this.item = item;
    }

    public Enchantment getEnchantment() {
        return item;
    }

    public String getName() {
        return item.getName(1).getString();
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

    protected static Enchantment getByName(String id) {
        Identifier resourceLocation = new Identifier(id);
        if (Registry.ENCHANTMENT.containsId(resourceLocation)) {
            return Registry.ENCHANTMENT.get(resourceLocation);
        }
        return null;
    }

}
