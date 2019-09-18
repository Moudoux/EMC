package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventMouseClick;
import me.deftware.client.framework.wrappers.IMouse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouseHelper {

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void mouseButtonCallback(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer != MinecraftClient.getInstance().method_22683().getHandle() || MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        new EventMouseClick(button, action, modifiers).broadcast();
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"))
    private void scrollCallback(long windowPointer, double xoffset, double yoffset, CallbackInfo ci) {
        if (windowPointer != MinecraftClient.getInstance().method_22683().getHandle()) {
            return;
        }
        IMouse.onScroll(xoffset, yoffset);
    }


}
