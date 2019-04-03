package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerScreen.class)
public class MixinGuiContainer extends MixinGuiScreen implements IMixinGuiContainer {

    @Shadow
    private Slot focusedSlot;

    @Override
    public Slot getHoveredSlot() {
        return focusedSlot;
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        this.shouldSendPostRenderEvent = false;
    }

    @Inject(method = "render", at = @At("RETURN"), remap = false)
    public void render_return(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenPostDraw((Screen) (Object) this, x, y).broadcast();
    }

}
