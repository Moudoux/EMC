package me.deftware.mixin.mixins;

import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashScreen.class)
public class MixinSplashScreen {

    @Inject(method = "init", at = @At("HEAD"))
    private static void init(MinecraftClient minecraftClient_1, CallbackInfo ci) {
        if (!Bootstrap.initialized) {
            Bootstrap.init();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getMeasuringTimeMs()J", ordinal = 1))
    private long render(int int_1, int int_2, float float_1) {
        if (!Bootstrap.initialized) {
            Bootstrap.initialized = true;
            Bootstrap.getMods().values().forEach(EMCMod::postInit);
        }
        return Util.getMeasuringTimeMs();
    }

}
