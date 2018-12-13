package me.deftware.client.framework.wrappers.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.Overwrite;

public class IGuiButton extends GuiButton implements CustomIGuiEventListener {

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
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (onDraw(mouseX, mouseY) == 0) {
			super.render(mouseX, mouseY, partialTicks);
		}
	}

	public void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getInstance().fontRenderer.drawStringWithShadow(text, x - Minecraft.getInstance().fontRenderer.getStringWidth(text) / 2, y, color);
	}

	@Override
	public void onClick(double x, double y) {
		onButtonClick(x, y);
	}

	public void onButtonClick(double mouseX, double mouseY){}

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

	protected int getButtonX() {
		return x;
	}

	protected int getButtonY() {
		return y;
	}

	protected void setButtonY(int y) {
		this.y = y;
	}

	protected void setButtonX(int x) {
		this.x = x;
	}

	protected int getTheButtonWidth() {
		return width;
	}

	protected int getTheButtonHeight() {
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