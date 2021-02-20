package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import net.minecraft.client.render.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(BufferBuilder.class)
public class MixinBufferBuilder {

    @ModifyVariable(method = "vertex(FFFFFFFFFIIFFF)V", at = @At("HEAD"), index = 7)
    private float alpha(float alpha) {
        if (Bootstrap.blockProperties.isActive() && Bootstrap.blockProperties.isOpacityMode())
            return getCustomAlpha();
        return alpha;
    }

    @Unique
    private float getCustomAlpha() {
        return Bootstrap.blockProperties.getOpacity() / 255f;
    }

}

