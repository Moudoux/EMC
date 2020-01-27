package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class MixinGuiContainer extends MixinGuiScreen implements IMixinGuiContainer {

    @Shadow
    protected Slot focusedSlot;

    @Override
    public Slot getHoveredSlot() {
        return focusedSlot;
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        this.shouldSendPostRenderEvent = false;
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render_return(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenPostDraw((Screen) (Object) this, x, y).broadcast();
    }

}
