package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventMouseClick;
import me.deftware.client.framework.wrappers.IMouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHelper.class)
public class MixinMouseHelper {

	@Shadow
	@Final
	private Minecraft mc;

	@Inject(method = "onButtonEvent", at = @At("HEAD"))
	private void onButtonEvent(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
		if (windowPointer != mc.mainWindow.getWindowPointer() || mc.currentScreen != null) {
			return;
		}
		new EventMouseClick(button, action, modifiers).send();
	}

	@Inject(method = "onScroll", at = @At("HEAD"))
	private void onScroll(long windowPointer, double xoffset, double yoffset) {
		if (windowPointer != mc.mainWindow.getWindowPointer()) {
			return;
		}
		IMouse.onScroll(xoffset, yoffset);
	}


}
