package me.deftware.mixin.mixins;

import com.google.common.collect.Lists;
import me.deftware.client.framework.event.events.EventGetItemToolTip;
import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.client.framework.wrappers.item.IItem;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontRenderer;
import net.minecraft.client.gui.GuiEventListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(Screen.class)
public class MixinGuiScreen implements IMixinGuiScreen {

    @Shadow
    protected FontRenderer fontRenderer;
    @Shadow
    private List<ButtonWidget> buttons;
    @Shadow
    @Final
    private List<GuiEventListener> listeners;

    @Override
    public List<ButtonWidget> getButtonList() {
        return buttons;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    @Override
    public List<GuiEventListener> getEventList() {
        return listeners;
    }

    @Inject(method = "draw", at = @At("HEAD"))
    public void render(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenDraw((Screen) (Object) this, x, y).send();
    }

    @Inject(method = "draw", at = @At("RETURN"))
    public void render_return(int x, int y, float p_render_3_, CallbackInfo ci) {
        new EventGuiScreenPostDraw((Screen) (Object) this, x, y).send();
    }

    @Overwrite
    public List<String> getStackTooltip(ItemStack itemStack_1) {
        List<TextComponent> list_1 = itemStack_1.getTooltipText(MinecraftClient.getInstance().player, MinecraftClient.getInstance().options.advancedItemTooltips ? TooltipOptions.Instance.ADVANCED : TooltipOptions.Instance.NORMAL);
        List<String> list_2 = Lists.newArrayList();
        Iterator var4 = list_1.iterator();

        while (var4.hasNext()) {
            TextComponent textComponent_1 = (TextComponent) var4.next();
            list_2.add(textComponent_1.getFormattedText());
        }

        EventGetItemToolTip event = new EventGetItemToolTip(list_2, new IItem(itemStack_1.getItem())).send();
        return event.getList();

    }

}
