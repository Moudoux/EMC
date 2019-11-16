package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinChatSuggestion;
import net.minecraft.class_4717;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
 public class MixinGuiChat {

    @Shadow
    public class_4717 field_21616;

    @Inject(method = "init", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        ((IMixinChatSuggestion) field_21616).setInject(true);
    }

}