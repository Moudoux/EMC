package me.deftware.mixin.mixins.gui;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.widgets.Button;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deftware
 */
@Mixin(ClickableWidget.class)
public class MixinGuiButton implements Button {

    @Shadow
    protected int height;

    @Override
    public int getPositionX() {
        return ((ClickableWidget) (Object) this).x;
    }

    @Override
    public int getPositionY() {
        return ((ClickableWidget) (Object) this).y;
    }

    @Override
    public int getComponentWidth() {
        return ((ClickableWidget) (Object) this).getWidth();
    }

    @Override
    public int getComponentHeight() {
        return ((ClickableWidget) (Object) this).getHeight();
    }

    @Override
    public boolean isActive() {
        return ((ClickableWidget) (Object) this).active;
    }

    @Override
    public void setPositionX(int x) {
        ((ClickableWidget) (Object) this).x = x;
    }

    @Override
    public void setPositionY(int y) {
        ((ClickableWidget) (Object) this).y = y;
    }

    @Override
    public void setComponentWidth(int width) {
        ((ClickableWidget) (Object) this).setWidth(width);
    }

    @Override
    public void setComponentHeight(int height) {
        this.height = height;
    }

    @Override
    public void setActive(boolean state) {
        ((ClickableWidget) (Object) this).active = state;
    }

    @Override
    public ChatMessage getComponentLabel() {
        return new ChatMessage().fromText(
                ((ClickableWidget) (Object) this).getMessage()
        );
    }

    @Override
    public Button setComponentLabel(ChatMessage text) {
        ((ClickableWidget) (Object) this).setMessage(text.build());
        return this;
    }

    @Unique
    private List<TooltipComponent> tooltipComponents;

    @Override
    public Button _setTooltip(ChatMessage... tooltip) {
        this.tooltipComponents = Arrays.stream(tooltip)
                .map(ChatMessage::build)
                .map(Text::asOrderedText)
                .map(TooltipComponent::of)
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public List<TooltipComponent> _getTooltip() {
        return tooltipComponents;
    }

}
