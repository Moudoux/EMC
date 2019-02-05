package me.deftware.mixin.mixins;

import com.google.common.collect.Lists;
import me.deftware.client.framework.event.events.EventGetItemToolTip;
import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.client.framework.wrappers.item.IItem;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(GuiScreen.class)
public class MixinGuiScreen implements IMixinGuiScreen {

    @Shadow
    protected FontRenderer fontRenderer;
    @Shadow
    private List<GuiButton> buttons;
    @Shadow
    @Final
    private List<IGuiEventListener> children;

    @Override
    public List<GuiButton> getButtonList() {
        return buttons;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    @Override
    public List<IGuiEventListener> getEventList() {
        return children;
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void render(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenDraw((GuiScreen) (Object) this, x, y).send();
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render_return(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenPostDraw((GuiScreen) (Object) this, x, y).send();
    }

    @Overwrite
    public List<String> getItemToolTip(ItemStack p_getItemToolTip_1_) {
        List<ITextComponent> lvt_2_1_ = p_getItemToolTip_1_.getTooltip(Minecraft.getInstance().player, Minecraft.getInstance().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
        List<String> lvt_3_1_ = Lists.newArrayList();
        Iterator var4 = lvt_2_1_.iterator();
        while (var4.hasNext()) {
            ITextComponent lvt_5_1_ = (ITextComponent) var4.next();
            lvt_3_1_.add(lvt_5_1_.getFormattedText());
        }
        EventGetItemToolTip event = new EventGetItemToolTip(lvt_3_1_, new IItem(p_getItemToolTip_1_.getItem())).send();
        return event.getList();
    }

}
