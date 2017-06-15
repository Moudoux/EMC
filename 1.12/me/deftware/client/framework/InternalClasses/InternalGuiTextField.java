package me.deftware.client.framework.InternalClasses;

import java.awt.Color;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Event.Events.EventChatboxType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class InternalGuiTextField extends GuiTextField {

	private String overlay = "";

	public InternalGuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width,
			int par6Height) {
		super(componentId, fontrendererObj, x, y, par5Width, par6Height);
		EventChatboxType event = new EventChatboxType(this.getText(), overlay);
		Event.sendEvent(event);
		overlay = event.getOverlay();
	}

	@Override
	public void drawTextBox() {
		super.drawTextBox();
		String currentText = this.getText();
		if (!currentText.startsWith(".") && !currentText.startsWith("#") && !currentText.equals("")
				|| currentText.contains(" ")) {
			return;
		}
		int currentWidth = this.fontRendererInstance.getStringWidth(currentText);
		int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
		int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
		this.fontRendererInstance.drawStringWithShadow(overlay, l + currentWidth, i1, Color.GRAY.getRGB());
	}

	@Override
	public boolean textboxKeyTyped(char typedChar, int keyCode) {
		boolean state = super.textboxKeyTyped(typedChar, keyCode);
		if (state) {
			EventChatboxType event = new EventChatboxType(this.getText(), overlay);
			Event.sendEvent(event);
			overlay = event.getOverlay();
		}
		return state;
	}

}
