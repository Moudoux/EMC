package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.minecraft.GameSetting;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class MixinLightmapTextureManager {

    @Redirect(method = "update", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;gamma:D"))
    private double getGamma(GameOptions instance) {
        return GameSetting.GAMMA.get();
    }

}
