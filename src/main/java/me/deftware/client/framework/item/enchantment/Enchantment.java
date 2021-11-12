package me.deftware.client.framework.item.enchantment;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.registry.Identifiable;
import net.minecraft.util.registry.Registry;

/**
 * @author Deftware
 */
public class Enchantment implements Identifiable {

	protected final net.minecraft.enchantment.Enchantment enchantment;

	public Enchantment(net.minecraft.enchantment.Enchantment enchantment) {
		this.enchantment = enchantment;
	}

	public net.minecraft.enchantment.Enchantment getMinecraftEnchantment() {
		return enchantment;
	}

	@Override
	public String getTranslationKey() {
		return enchantment.getTranslationKey();
	}

	@Override
	public String getIdentifierKey() {
		return Registry.ENCHANTMENT.getId(enchantment).getPath();
	}

	public int getMinLevel() {
		return enchantment.getMinLevel();
	}

	public int getMaxLevel() {
		return enchantment.getMaxLevel();
	}

	public ChatMessage getName(int level) {
		return new ChatMessage().fromText(enchantment.getName(level));
	}

}
