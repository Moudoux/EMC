package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventDisconnected;
import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.menu.DisconnectedScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class MixinGuiDisconnected {

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        new EventDisconnected().broadcast();
    }

}
