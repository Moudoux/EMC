package me.deftware.mixin.mixins.input;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(KeyboardInput.class)
public class MixinMovementInputFromOptions {

    @ModifyVariable(method = "tick", at = @At("HEAD"))
    public boolean onTick(boolean slowDown) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Sneak);
        event.broadcast();
        return !event.isCanceled() && slowDown;
    }
}
