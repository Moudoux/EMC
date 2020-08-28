package me.deftware.mixin.mixins.item;

import me.deftware.client.framework.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantments.class)
public class MixinEnchantments {

	@Inject(method = "register", at = @At("HEAD"))
	private static void register(String name, Enchantment enchantment, CallbackInfoReturnable<Enchantment> ci) {
		EnchantmentRegistry.INSTANCE.register(name, enchantment);
	}

}
