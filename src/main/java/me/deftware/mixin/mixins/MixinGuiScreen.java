package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGetItemToolTip;
import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.client.framework.wrappers.item.IItem;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public class MixinGuiScreen implements IMixinGuiScreen {

    public boolean shouldSendPostRenderEvent = true;

    @Shadow
    protected TextRenderer font;

    @Shadow
    @Final
    protected List<AbstractButtonWidget> buttons;

    @Shadow
    @Final
    protected List<Element> children;

    @Override
    public List<AbstractButtonWidget> getButtonList() {
        return buttons;
    }

    @Override
    public TextRenderer getFont() {
        return font;
    }

    @Override
    public List<Element> getEventList() {
        return children;
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void render(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenDraw((Screen) (Object) this, x, y).broadcast();
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render_return(int x, int y, float p_render_3_, CallbackInfo ci) {
        if (shouldSendPostRenderEvent) {
            new EventGuiScreenPostDraw((Screen) (Object) this, x, y).broadcast();
        }
    }


    @Inject(method = "getTooltipFromItem", at = @At(value = "TAIL"), cancellable = true)
    private void onGetTooltipFromItem(ItemStack stack, CallbackInfoReturnable<List<String>> cir) {
        EventGetItemToolTip event = new EventGetItemToolTip(cir.getReturnValue(), new IItem(stack.getItem()));
        event.broadcast();
        cir.setReturnValue(event.getList());
    }

}
