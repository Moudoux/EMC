package me.deftware.mixin.mixins;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinGuiMainMenu {

    @Inject(method = "render", at = @At("RETURN"))
    public void render(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "MAIN_MENU_OVERLAY", true)) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(FrameworkConstants.toDataString(), 2, 2, 0xFFFFFF);
        }
    }

}
