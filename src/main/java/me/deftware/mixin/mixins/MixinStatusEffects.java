package me.deftware.mixin.mixins;

import me.deftware.client.framework.registry.StatusEffectRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffects.class)
public class MixinStatusEffects {

	@Inject(method = "register", at = @At("HEAD"))
	private static void register(int rawId, String id, StatusEffect entry, CallbackInfoReturnable<StatusEffect> ci) {
		StatusEffectRegistry.INSTANCE.register(id, entry);
	}

}
