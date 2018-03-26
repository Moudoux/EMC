package me.deftware.client.framework.wrappers.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class IGuiTextField extends GuiTextField {

	public IGuiTextField(int componentId, int x, int y, int width, int height) {
		super(componentId, Minecraft.getMinecraft().fontRenderer, x, y, width, height);
	}

	public String getTextboxText() {
		return getText();
	}

	public void setTextboxText(String text) {
		setText(text);
	}

	public void setMaxLenght(int lenght) {
		setMaxStringLength(lenght);
	}

	public boolean isTextboxFocused() {
		return isFocused();
	}

	public void setTextboxFocused(boolean state) {
		setFocused(state);
	}

	public void onKeyTyped(char typedChar, int keyCode) {
		textboxKeyTyped(typedChar, keyCode);
	}

	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void onDraw() {
		drawTextBox();
	}

	public void doCursorTick() {
		updateCursorCounter();
	}

	public void setTextboxEnabled(boolean state) {
		setEnabled(state);
	}

	public int getPosX() {
		return x;
	}

	public int getPosY() {
		return y;
	}

	public void setPosX(int x) {
		this.x = x;
	}

	public void setPosY(int y) {
		this.y = y;
	}

	public void setIEnableBackgroundDrawing(boolean state) {
		setEnableBackgroundDrawing(state);
	}

}
