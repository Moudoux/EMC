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

	@Inject(method = "mouseButtonCallback", at = @At("HEAD"))
	private void mouseButtonCallback(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
		if (windowPointer != Minecraft.getInstance().mainWindow.getHandle() || Minecraft.getInstance().currentScreen != null) {
			return;
		}
		new EventMouseClick(button, action, modifiers).send();
	}

	@Inject(method = "scrollCallback", at = @At("HEAD"))
	private void scrollCallback(long windowPointer, double xoffset, double yoffset, CallbackInfo ci) {
		if (windowPointer != Minecraft.getInstance().mainWindow.getHandle()) {
			return;
		}
		IMouse.onScroll(xoffset, yoffset);
	}


}
