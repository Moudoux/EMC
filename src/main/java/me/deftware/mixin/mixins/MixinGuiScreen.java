package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGetItemToolTip;
import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.item.IItem;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Screen.class)
public class MixinGuiScreen implements IMixinGuiScreen {

    public boolean shouldSendPostRenderEvent = true;

    @Shadow
    protected TextRenderer textRenderer;

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
        return textRenderer;
    }

    @Override
    public List<Element> getEventList() {
        return children;
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenDraw((Screen) (Object) this, x, y).broadcast();
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render_return(MatrixStack matrixStack, int x, int y, float p_render_3_, CallbackInfo ci) {
        if (shouldSendPostRenderEvent) {
            new EventGuiScreenPostDraw((Screen) (Object) this, x, y).broadcast();
        }
    }

    @Inject(method = "getTooltipFromItem", at = @At(value = "TAIL"), cancellable = true)
    private void onGetTooltipFromItem(ItemStack stack, CallbackInfoReturnable<List<Text>> cir) {
        List<String> list = new ArrayList<>();
        for (Text text : cir.getReturnValue()) {
            list.add(ChatProcessor.getStringFromText(text));
        }
        EventGetItemToolTip event = new EventGetItemToolTip(list, new IItem(stack.getItem()));
        event.broadcast();
        List<Text> modifiedTextList = new ArrayList<>();
        for (String text : event.getList()) {
            modifiedTextList.add(ChatProcessor.getLiteralText(text));
        }
        cir.setReturnValue(modifiedTextList);
    }

}
