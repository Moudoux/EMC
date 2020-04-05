package me.deftware.mixin.mixins;

import me.deftware.client.framework.wrappers.gui.DebugHudWrapper;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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
		for (DebugHudWrapper.StringModifier modifier : DebugHudWrapper.modifiers) {
			stringData = modifier.apply(stringData);
		}
		return stringData;
	}

}
