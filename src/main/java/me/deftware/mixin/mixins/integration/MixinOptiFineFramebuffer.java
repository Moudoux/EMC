package me.deftware.mixin.mixins.integration;

import net.minecraft.client.gl.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@SuppressWarnings("ShadowTarget")
@Mixin(Framebuffer.class)
public class MixinOptiFineFramebuffer {

    @Shadow(remap = false)
    private boolean stencilEnabled;

    @Inject(method = "resize", at = @At("HEAD"))
    public void resize(int width, int height, boolean getError, CallbackInfo ci) {
        stencilEnabled = true;
    }


}
