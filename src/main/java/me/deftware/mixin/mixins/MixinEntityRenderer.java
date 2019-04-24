package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.event.events.EventHurtcam;
import me.deftware.client.framework.event.events.EventRender2D;
import me.deftware.client.framework.event.events.EventRender3D;
import me.deftware.client.framework.event.events.EventWeather;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.mixin.imp.IMixinEntityRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(GameRenderer.class)
public abstract class MixinEntityRenderer implements IMixinEntityRenderer {

    @Shadow
    private boolean renderHand;
    private float partialTicks = 0;

    @Shadow
    public abstract void loadShader(Identifier p_loadShader_1_);

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        EventHurtcam event = new EventHurtcam();
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/hud/InGameHud.draw(F)V"))
    private void onRender2D(CallbackInfo cb) {
        ChatProcessor.sendMessages();
        new EventRender2D(0f).broadcast();
    }

    @Inject(method = "renderRain", at = @At("HEAD"), cancellable = true)
    private void addRainParticles(CallbackInfo ci) {
        EventWeather event = new EventWeather(EventWeather.WeatherType.Rain);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void renderRainSnow(float partialTicks, CallbackInfo ci) {
        EventWeather event = new EventWeather(EventWeather.WeatherType.Rain);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/platform/GlStateManager.enableDepthTest()V"))
    private void renderWorld(CallbackInfo ci) {
        if (!((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "WORLD_DEPTH", true))) {
            GlStateManager.disableDepthTest();
        }
    }

    @Inject(method = "renderCenter", at = @At("HEAD"))
    private void updateCameraAndRender(float partialTicks, long finishTimeNano, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @Redirect(method = "renderCenter", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = GETFIELD))
    private boolean updateCameraAndRender_renderHand(GameRenderer self) {
        new EventRender3D(partialTicks).broadcast();
        return renderHand;
    }

    @Override
    public void loadCustomShader(IResourceLocation location) {
        loadShader(location);
    }

}
