package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.event.events.EventRenderHotbar;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinGuiIngame {

    @Inject(method = "renderCrosshair", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/platform/GlStateManager.blendFuncSeparate(Lcom/mojang/blaze3d/platform/GlStateManager$SourceFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DestFactor;Lcom/mojang/blaze3d/platform/GlStateManager$SourceFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DestFactor;)V", remap = false), cancellable = true)
    private void crosshairEvent(float partialTicks, CallbackInfo ci) {
        if (!((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "CROSSHAIR", true))) {
            GlStateManager.enableAlphaTest();
            ci.cancel();
        }
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void renderHotbar(float partialTicks, CallbackInfo ci) {
        new EventRenderHotbar().send();
    }

}
