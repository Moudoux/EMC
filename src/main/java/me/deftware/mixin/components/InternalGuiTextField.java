package me.deftware.mixin.components;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;
import java.lang.ref.WeakReference;

public class InternalGuiTextField extends GuiTextField {

	private String overlay = "";

	public InternalGuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width,
								int par6Height) {
		super(componentId, fontrendererObj, x, y, par5Width, par6Height);
	}

	@Override
	public void drawTextField(int mouseX, int mouseY, float partialTicks) {
		super.drawTextField(mouseX, mouseY, partialTicks);
		String currentText = getText();
		int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(currentText);
		int l = getEnableBackgroundDrawing() ? x + 4 : x;
		int i1 = getEnableBackgroundDrawing() ? y + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : y;
		((IMixinGuiTextField) this).getFontRendererInstance().drawStringWithShadow(overlay, l + currentWidth, i1, Color.GRAY.getRGB());
		WeakReference<EventChatboxType> event = new WeakReference<>(new EventChatboxType(getText(), overlay));
		event.get().broadcast();
		overlay = event.get().getOverlay();
	}

}
