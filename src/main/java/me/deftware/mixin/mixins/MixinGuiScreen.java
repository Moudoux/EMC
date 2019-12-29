package me.deftware.mixin.mixins;

import com.google.common.collect.Lists;
import me.deftware.client.framework.event.events.EventGetItemToolTip;
import me.deftware.client.framework.event.events.EventGuiScreenDraw;
import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.client.framework.wrappers.item.IItem;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.main.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
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

    public boolean shouldSendPostRenderEvent = true;

    @Shadow
    protected TextRenderer font;

    @Shadow
    @Final
    private List<AbstractButtonWidget> buttons;

    @Shadow
    @Final
    private List<Element> children;

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

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public List<String> getTooltipFromItem(ItemStack itemStack_1) {
        List<Text> list_1 = itemStack_1.getTooltip(MinecraftClient.getInstance().player, MinecraftClient.getInstance().options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL);
        List<String> list_2 = Lists.newArrayList();
        Iterator var4 = list_1.iterator();

        while (var4.hasNext()) {
            Text textComponent_1 = (Text) var4.next();
            list_2.add(textComponent_1.asFormattedString());
        }

        EventGetItemToolTip event = new EventGetItemToolTip(list_2, new IItem(itemStack_1.getItem()));
        event.broadcast();
        return event.getList();
    }

}
