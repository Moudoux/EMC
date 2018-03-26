package me.deftware.client.framework.wrappers.gui;


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
		displayString = text;
	}

	public String getText() {
		return displayString;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (onDraw(mouseX, mouseY) == 0) {
			super.drawButton(mc, mouseX, mouseY, partialTicks);
		}
	}

	public void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, y, color);
	}

	protected int onDraw(int mouseX, int mouseY) {
		return 0;
	}

	protected boolean isVisible() {
		return visible;
	}

	public void setEnabled(boolean state) {
		enabled = state;
	}

	public void setVisible(boolean state) {
		visible = state;
	}

	public boolean isEnabled() {
		return enabled;
	}


	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}

	protected void setButtonWidth(int width) {
		this.width = width;
	}

	protected void setButtonHeight(int height) {
		this.height = height;
	}

	protected boolean isHovered() {
		return hovered;
	}

	protected void setHovered(boolean state) {
		hovered = state;
	}

}