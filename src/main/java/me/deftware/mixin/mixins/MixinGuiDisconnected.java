package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventDisconnected;
import net.minecraft.client.gui.GuiDisconnected;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiDisconnected.class)
public class MixinGuiDisconnected {

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        new EventDisconnected().broadcast();
    }

}
