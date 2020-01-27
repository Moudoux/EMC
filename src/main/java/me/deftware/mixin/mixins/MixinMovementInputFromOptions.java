package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(KeyboardInput.class)
public class MixinMovementInputFromOptions {

    @Shadow
    @Final
    public GameOptions settings;


    @ModifyVariable(method = "tick", at = @At("HEAD"))
    public boolean onTick(boolean bl) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Sneak);
        event.broadcast();
        return event.isCanceled() ? false : bl;
    }
}
