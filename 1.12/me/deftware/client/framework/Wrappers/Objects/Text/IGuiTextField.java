package me.deftware.client.framework.Wrappers.Objects.Text;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class IGuiTextField extends GuiTextField {

	public IGuiTextField(int componentId, int x, int y, int width, int height) {
		super(componentId, Minecraft.getMinecraft().fontRendererObj, x, y, width, height);
	}
	
	public String getTextboxText() {
		return this.getText();
	}
	
	public void setTextboxText(String text) {
		this.setText(text);
	}
	
	public boolean isTextboxFocused() {
		return this.isFocused();
	}
	
	public void setTextboxFocused(boolean state) {
		this.setFocused(state);
	}
	
	/*
	 * Wrappers
	 */
	
	public void onKeyTyped(char typedChar, int keyCode) {
		this.textboxKeyTyped(typedChar, keyCode);
	}
	
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		this.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void onDraw()  {
		this.drawTextBox();
	}
	
}
