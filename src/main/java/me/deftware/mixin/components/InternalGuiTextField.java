package me.deftware.mixin.components;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.font.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.awt.*;

public class InternalGuiTextField extends TextFieldWidget {

    private String overlay = "";

    public InternalGuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width,
                                int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        String currentText = getText();
        int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(currentText);
        int l = hasBorder() ? ((IMixinGuiTextField) this).getX() + 4 : ((IMixinGuiTextField) this).getX();
        int i1 = hasBorder() ? ((IMixinGuiTextField) this).getY() + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : ((IMixinGuiTextField) this).getY();
        ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(overlay, l + currentWidth, i1, Color.GRAY.getRGB());
        EventChatboxType event = new EventChatboxType(getText(), overlay).send();
        overlay = event.getOverlay();
    }

}
