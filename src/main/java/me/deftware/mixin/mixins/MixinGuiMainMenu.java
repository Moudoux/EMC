package me.deftware.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.FrameworkConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {

	@Inject(method = "drawScreen", at = @At("RETURN"))
	public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(
				FrameworkConstants.FRAMEWORK_NAME + " v" + FrameworkConstants.VERSION, 2, 2, 0xFFFFFF);
	}

}
