package me.deftware.mixin.mixins.input;

import me.deftware.client.framework.event.events.EventKeyAction;
import me.deftware.client.framework.event.events.EventMouseClick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouseListener {
    
    @Shadow
    private boolean cursorLocked;
    
    // target the best place for no screen or overlay open, similar to {@Link MixinKeyboardListener}
    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorLocked:Z"), method = "onMouseButton")
    public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        // if mouse is already locked and button sanity check
        if (cursorLocked && button >= 0 && button <= 7) {
            new EventKeyAction(button, action, mods).broadcast();
        }
    }
    
    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void mouseButtonCallback(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer == MinecraftClient.getInstance().getWindow().getHandle() || MinecraftClient.getInstance().currentScreen != null) {
            new EventMouseClick(button, action, modifiers).broadcast();
        }
    }
    
    @Inject(method = "onMouseScroll", at = @At("HEAD"))
    private void scrollCallback(long windowPointer, double horizontal, double vertical, CallbackInfo ci) {
        if (windowPointer == MinecraftClient.getInstance().getWindow().getHandle()) {
            me.deftware.client.framework.input.Mouse.onScroll(horizontal, vertical);
        }
    }
}
