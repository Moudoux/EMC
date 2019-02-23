package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventRenderHotbar;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Inject(method = "renderAttackIndicator", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.blendFuncSeparate(Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;)V"), cancellable = true)
	private void crosshairEvent(float partialTicks, CallbackInfo ci) {
		if (!((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "CROSSHAIR", true))) {
			GlStateManager.enableAlphaTest();
			ci.cancel();
		}
	}

	@Inject(method = "renderHotbar", at = @At("HEAD"))
	private void renderHotbar(float partialTicks, CallbackInfo ci) {
		new EventRenderHotbar().broadcast();
	}

}
