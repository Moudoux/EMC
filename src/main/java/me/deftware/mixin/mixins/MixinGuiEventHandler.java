package me.deftware.mixin.mixins;

import net.minecraft.client.gui.DrawableContainer;
import net.minecraft.client.gui.GuiEventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DrawableContainer.class)
public abstract class MixinGuiEventHandler {

    @Shadow
    protected abstract List<? extends GuiEventListener> getEntries();

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    public void mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_, CallbackInfoReturnable<Boolean> ci) {
        this.getEntries().forEach(
                (listener) -> listener.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_));
    }

}
