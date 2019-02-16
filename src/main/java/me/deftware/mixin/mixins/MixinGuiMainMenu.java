package me.deftware.mixin.mixins;

import me.deftware.client.framework.FrameworkConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {

    @Inject(method = "render", at = @At("RETURN"))
    public void render(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Minecraft.getInstance().fontRenderer.drawStringWithShadow(
                FrameworkConstants.FRAMEWORK_NAME + " v" + FrameworkConstants.VERSION + "."
                        + FrameworkConstants.PATCH, 2, 2, 0xFFFFFF);
    }

}
