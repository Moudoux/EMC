package me.deftware.mixin.components;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.awt.*;

public class InternalGuiTextField extends TextFieldWidget {

    private String overlay = "";

    public InternalGuiTextField(TextRenderer fontrendererObj, int x, int y, int width, int height) {
        super(fontrendererObj, x, y, width, height);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        String currentText = getText();
        int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(currentText);
        int l = hasBorder() ? ((IMixinGuiTextField) this).getX() + 4 : ((IMixinGuiTextField) this).getX();
        int i1 = hasBorder() ? ((IMixinGuiTextField) this).getY() + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : ((IMixinGuiTextField) this).getY();
        ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(overlay, l + currentWidth, i1, Color.GRAY.getRGB());
        EventChatboxType event = new EventChatboxType(getText(), overlay).send();
        overlay = event.getOverlay();
    }

}
