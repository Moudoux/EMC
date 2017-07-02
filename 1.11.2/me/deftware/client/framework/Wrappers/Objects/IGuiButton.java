package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class IGuiButton extends GuiButton {

	public IGuiButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, 200, 20, buttonText);
	}

	public IGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public void setText(String text) {
		this.displayString = text;
	}
	
	public String getText() {
		return this.displayString;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (onDraw(mouseX, mouseY) == 0) {
			super.drawButton(mc, mouseX, mouseY);
		}
	}
	
	public void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
	}
	
	protected int onDraw(final int mouseX, final int mouseY) {
		return 0;
	}
	
	protected boolean isVisible() {
		return this.visible;
	}
	
	public void setEnabled(boolean state) {
		this.enabled = state;
	}
	
	public void setVisible(boolean state) {
		this.visible = state;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	/*
	 * Getters/Setters
	 */
	
	protected int getX() {
		return this.xPosition;
	}
	
	protected int getY() {
		return this.yPosition;
	}

	protected void setY(int y) {
		this.yPosition = y;
	}
	
	protected void setX(int x) {
		this.xPosition = x;
	}
	
	protected int getWidth() {
		return this.width;
	}
	
	protected int getHeight() {
		return this.height;
	}

	protected void setButtonWidth(int width) {
		this.width = width;
	}
	
	protected void setButtonHeight(int height) {
		this.height = height;
	}
	
	protected boolean isHovered() {
		return this.hovered;
	}
	
	protected void setHovered(boolean state) {
		this.hovered = state;
	}
	
}
