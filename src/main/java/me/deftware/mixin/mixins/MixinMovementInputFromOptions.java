package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("ALL")
@Mixin(KeyboardInput.class)
public class MixinMovementInputFromOptions {

    @Shadow
    @Final
    public GameOptions settings;

    @Overwrite
    public void tick() {
        ((KeyboardInput) (Object) this).pressingForward = this.settings.keyForward.isPressed();
        ((KeyboardInput) (Object) this).pressingBack = this.settings.keyBack.isPressed();
        ((KeyboardInput) (Object) this).pressingLeft = this.settings.keyLeft.isPressed();
        ((KeyboardInput) (Object) this).pressingRight = this.settings.keyRight.isPressed();
        ((KeyboardInput) (Object) this).movementForward = ((KeyboardInput) (Object) this).pressingForward == ((KeyboardInput) (Object) this).pressingBack ? 0.0F : (float) (((KeyboardInput) (Object) this).pressingForward ? 1 : -1);
        ((KeyboardInput) (Object) this).movementSideways = ((KeyboardInput) (Object) this).pressingLeft == ((KeyboardInput) (Object) this).pressingRight ? 0.0F : (float) (((KeyboardInput) (Object) this).pressingLeft ? 1 : -1);
        ((KeyboardInput) (Object) this).jumping = this.settings.keyJump.isPressed();
        ((KeyboardInput) (Object) this).sneaking = this.settings.keySneak.isPressed();
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Sneak);
        event.broadcast();
        if (((KeyboardInput) (Object) this).sneaking && !event.isCanceled()) {
            ((KeyboardInput) (Object) this).movementSideways = (float) ((double) ((KeyboardInput) (Object) this).movementSideways * 0.3D);
            ((KeyboardInput) (Object) this).movementForward = (float) ((double) ((KeyboardInput) (Object) this).movementForward * 0.3D);
        }
    }

}
