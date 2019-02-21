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
        ((KeyboardInput) (Object) this).field_3907 = 0.0F;
        ((KeyboardInput) (Object) this).field_3905 = 0.0F;
        if (this.settings.keyForward.isPressed()) {
            ++((KeyboardInput) (Object) this).field_3905;
            ((KeyboardInput) (Object) this).forward = true;
        } else {
            ((KeyboardInput) (Object) this).forward = false;
        }

        if (this.settings.keyBack.isPressed()) {
            --((KeyboardInput) (Object) this).field_3905;
            ((KeyboardInput) (Object) this).back = true;
        } else {
            ((KeyboardInput) (Object) this).back = false;
        }

        if (this.settings.keyLeft.isPressed()) {
            ++((KeyboardInput) (Object) this).field_3907;
            ((KeyboardInput) (Object) this).left = true;
        } else {
            ((KeyboardInput) (Object) this).left = false;
        }

        if (this.settings.keyRight.isPressed()) {
            --((KeyboardInput) (Object) this).field_3907;
            ((KeyboardInput) (Object) this).right = true;
        } else {
            ((KeyboardInput) (Object) this).right = false;
        }

        ((KeyboardInput) (Object) this).jumping = this.settings.keyJump.isPressed();
        ((KeyboardInput) (Object) this).sneaking = this.settings.keySneak.isPressed();

        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Sneak).send();

        if (((KeyboardInput) (Object) this).sneaking && !event.isCanceled()) {
            ((KeyboardInput) (Object) this).field_3907 = (float) ((double) ((KeyboardInput) (Object) this).field_3907 * 0.3D);
            ((KeyboardInput) (Object) this).field_3905 = (float) ((double) ((KeyboardInput) (Object) this).field_3905 * 0.3D);
        }
    }

}
