package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu {

	@Inject(method = "render", at = @At("RETURN"))
	private void render(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		new EventGuiScreenDraw((GuiScreen) (Object) this).send();
	}

}
