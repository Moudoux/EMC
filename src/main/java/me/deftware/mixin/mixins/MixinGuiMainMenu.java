package me.deftware.mixin.mixins;

import me.deftware.client.framework.FrameworkConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.MainMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenuScreen.class)
public class MixinGuiMainMenu {

    @Inject(method = "draw", at = @At("RETURN"))
    public void draw(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        MinecraftClient.getInstance().fontRenderer.drawWithShadow(
                FrameworkConstants.FRAMEWORK_NAME + " v" + FrameworkConstants.VERSION, 2, 2, 0xFFFFFF);
    }

}
