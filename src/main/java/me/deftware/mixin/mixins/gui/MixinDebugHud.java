package me.deftware.mixin.mixins.gui;

import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(DebugHud.class)
public class MixinDebugHud {

	@Inject(method = "getLeftText", at = @At("TAIL"), cancellable = true)
	protected void retrieveLeftText(CallbackInfoReturnable<List<String>> cir) {
		cir.setReturnValue(getModifiedList(cir.getReturnValue()));
	}

	@Inject(method = "getRightText", at = @At("TAIL"), cancellable = true)
	protected void retrieveRightText(CallbackInfoReturnable<List<String>> cir) {
		cir.setReturnValue(getModifiedList(cir.getReturnValue()));
	}

	private List<String> getModifiedList(List<String> stringData) {
		for (Function<List<String>, List<String>> modifier : Minecraft.getMinecraftGame().getDebugModifiers()) {
			stringData = modifier.apply(stringData);
		}
		return stringData;
	}

}
