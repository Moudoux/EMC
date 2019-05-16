package me.deftware.mixin.components;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.awt.*;
import java.lang.ref.WeakReference;

public class InternalGuiTextField extends TextFieldWidget {

    private String overlay = "";

    public InternalGuiTextField(TextRenderer fontrendererObj, int x, int y, int width, int height) {
        super(fontrendererObj, x, y, width, height, "");
    }

    @SuppressWarnings("Duplicates") @Override
    public void render(int int_1, int int_2, float float_1) {
        super.render(int_1, int_2, float_1);
        String currentText = getText();
        int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(currentText);
        int x = isFocused() ? ((IMixinGuiTextField) this).getX() + 4 : ((IMixinGuiTextField) this).getX();
        int y = isFocused() ? ((IMixinGuiTextField) this).getY() + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : ((IMixinGuiTextField) this).getY();
        ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(overlay, x + currentWidth - 3, y - 2, Color.GRAY.getRGB());
        WeakReference<EventChatboxType> event = new WeakReference<>(new EventChatboxType(getText(), overlay));
        event.get().broadcast();
        overlay = event.get().getOverlay();
    }

}
