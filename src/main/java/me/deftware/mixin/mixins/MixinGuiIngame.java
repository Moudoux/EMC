package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventRender2D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.event.events.EventRenderHotbar;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Inject(method = "renderAttackIndicator", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.enableAlpha()V"), cancellable = true)
	private void crosshairEvent(float partialTicks, CallbackInfo ci) {
		System.out.println((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "CROSSHAIR", true));
		if (!((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "CROSSHAIR", true))) {
			GlStateManager.enableAlpha();
			ci.cancel();
		}
	}

	@Inject(method = "renderHotbar", at = @At("HEAD"))
	private void renderHotbar(float partialTicks, CallbackInfo ci) {
		new EventRenderHotbar().send();
	}

}
