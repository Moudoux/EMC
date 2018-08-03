package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventCharacter;
import me.deftware.client.framework.event.events.EventKeyAction;
import net.minecraft.client.KeyboardListener;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardListener.class)
public class MixinKeyboardListener {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(method = "onKeyPressed", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputMappings.isKeyDown(I)Z", ordinal = 4))
	private void onKeyPressed(long windowPointer, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
		new EventKeyAction(keyCode, action, modifiers).send();
	}

	@Inject(method = "onCharEvent", at = @At("HEAD"))
	private void onCharEvent(long windowPointer, int codePoint, int modifiers, CallbackInfo ci) {
		if (windowPointer != minecraft.mainWindow.getWindowPointer() || minecraft.currentScreen != null) {
			return;
		} else {
			if (Character.charCount(codePoint) == 1) {
				new EventCharacter((char) codePoint, modifiers).send();
			} else {
				for (char character : Character.toChars(codePoint)) {
					new EventCharacter(character, modifiers).send();
				}
			}
		}
	}



}
