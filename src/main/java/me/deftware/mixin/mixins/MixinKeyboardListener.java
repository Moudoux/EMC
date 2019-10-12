package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventCharacter;
import me.deftware.client.framework.event.events.EventKeyAction;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboardListener {

    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5))
    private void onKeyEvent(long windowPointer, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        new EventKeyAction(keyCode, action, modifiers).broadcast();
    }

    @Inject(method = "onChar", at = @At("HEAD"))
    private void onCharEvent(long windowPointer, int codePoint, int modifiers, CallbackInfo ci) {
        if (windowPointer != MinecraftClient.getInstance().getWindow().getHandle() || MinecraftClient.getInstance().currentScreen != null) {
            return;
        } else {
            if (Character.charCount(codePoint) == 1) {
                new EventCharacter((char) codePoint, modifiers).broadcast();
            } else {
                for (char character : Character.toChars(codePoint)) {
                    new EventCharacter(character, modifiers).broadcast();
                }
            }
        }
    }


}
