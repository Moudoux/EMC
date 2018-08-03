package me.deftware.mixin.components;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;

public class InternalGuiTextField extends GuiTextField {

	private String overlay = "";

	public InternalGuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width,
								int par6Height) {
		super(componentId, fontrendererObj, x, y, par5Width, par6Height);
		EventChatboxType event = new EventChatboxType(getText(), overlay).send();
		overlay = event.getOverlay();
	}

	@Override
	public void func_195608_a(int mouseX, int mouseY, float partialTicks) {
		super.func_195608_a(mouseX, mouseY, partialTicks);
		String currentText = getText();
		if (Bootstrap.isTrigger(currentText).equals("") && !currentText.startsWith("#") && !currentText.equals("")
				|| currentText.contains(" ")) {
			return;
		}
		int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(currentText);
		int l = getEnableBackgroundDrawing() ? x + 4 : x;
		int i1 = getEnableBackgroundDrawing() ? y + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : y;
		((IMixinGuiTextField) this).getFontRendererInstance().drawStringWithShadow(overlay, l + currentWidth, i1, Color.GRAY.getRGB());
	}

	@Override
	public boolean charTyped(char typedChar, int keyCode) {
		boolean state = super.charTyped(typedChar, keyCode);
		if (state) {
			EventChatboxType event = new EventChatboxType(getText(), overlay).send();
			overlay = event.getOverlay();
		}
		return state;
	}

}
