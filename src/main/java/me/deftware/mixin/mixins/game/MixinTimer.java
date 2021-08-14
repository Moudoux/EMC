package me.deftware.mixin.mixins.game;

import me.deftware.client.framework.world.WorldTimer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderTickCounter.class)
public class MixinTimer implements WorldTimer {

    @Final
    @Shadow
    private float tickTime;

    private float speed = 1;

    @Redirect(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;tickTime:F", opcode = 180))
    private float getTickLength(RenderTickCounter self) {
        return tickTime / speed;
    }

    @Override
    public float getTimerSpeed() {
        return speed;
    }

    @Override
    public void setTimerSpeed(float speed) {
        this.speed = speed;
    }

}
