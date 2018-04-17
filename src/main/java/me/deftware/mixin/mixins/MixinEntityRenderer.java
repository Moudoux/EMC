package me.deftware.mixin.mixins;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.event.events.EventHurtcam;
import me.deftware.client.framework.event.events.EventRender2D;
import me.deftware.client.framework.event.events.EventRender3D;
import me.deftware.client.framework.event.events.EventWeather;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

	// TODO: Override RayTraceResult, line 462, FIELD: flag

	@Shadow
	private boolean renderHand;

	private float partialTicks = 0;

	@Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
	private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
		EventHurtcam event = new EventHurtcam().send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V"))
	private void onRender2D(CallbackInfo cb) {
		new EventRender2D(0f).send();
	}

	@Inject(method = "addRainParticles", at = @At("HEAD"), cancellable = true)
	private void addRainParticles(CallbackInfo ci) {
		EventWeather event = new EventWeather(EventWeather.WeatherType.Rain).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@Inject(method = "renderRainSnow", at = @At("HEAD"), cancellable = true)
	private void renderRainSnow(float partialTicks, CallbackInfo ci) {
		EventWeather event = new EventWeather(EventWeather.WeatherType.Rain).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.enableAlpha()V"))
	private void renderWorld(CallbackInfo ci) {
		if (!((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "WORLD_DEPTH", true))) {
			GlStateManager.disableDepth();
		}
	}

	@Inject(method = "renderWorldPass", at = @At("HEAD"))
	private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
		this.partialTicks = partialTicks;
	}

	@Redirect(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", opcode = GETFIELD))
	private boolean renderWorldPass_renderHand(EntityRenderer self) {
		new EventRender3D(partialTicks).send();
		return renderHand;
	}

}
