package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinTimer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(RenderTickCounter.class)
public class MixinTimer implements IMixinTimer {

    @Shadow
    private float tickTime;

    private float speed = 1;

    @Redirect(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;tickTime:F", opcode = GETFIELD))
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
