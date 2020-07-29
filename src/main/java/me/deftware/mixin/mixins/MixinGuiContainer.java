package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
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

    @Inject(method = "drawMouseoverTooltip", at = @At("RETURN"))
    private void drawMouseoverTooltip(MatrixStack matrices, int x, int y, CallbackInfo ci) {
        new EventGuiScreenPostDraw((Screen) (Object) this, x, y).broadcast();
    }

}
