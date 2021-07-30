package me.deftware.mixin.mixins.input;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = KeyboardInput.class, priority = 999)
public class MixinMovementInputFromOptions {

    @Unique
    private final EventSlowdown eventSlowdown = new EventSlowdown();

    @ModifyVariable(method = "tick", at = @At("HEAD"))
    public boolean onTick(boolean slowDown) {
        eventSlowdown.create(EventSlowdown.SlowdownType.Sneak, 1);
        eventSlowdown.broadcast();
        return !eventSlowdown.isCanceled() && slowDown;
    }

}
