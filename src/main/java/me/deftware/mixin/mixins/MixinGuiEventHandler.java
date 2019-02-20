package me.deftware.mixin.mixins;

import net.minecraft.client.gui.DrawableContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DrawableContainer.class)
public abstract class MixinGuiEventHandler {

    /* TODO: Fix this?
    @Shadow
    protected abstract List<? extends GuiEventListener> getEntries();

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    public void mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_, CallbackInfoReturnable<Boolean> ci) {
        this.getEntries().forEach(
                (listener) -> listener.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_));
    }
    */

}
