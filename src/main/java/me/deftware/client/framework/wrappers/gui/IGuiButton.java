package me.deftware.client.framework.wrappers.gui;

import me.deftware.mixin.imp.IMixinGuiButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;

public class IGuiButton extends ButtonWidget implements CustomIGuiEventListener {

    public IGuiButton(int x, int y, String buttonText) {
        super(x, y, 200, 20, buttonText);
    }

    public IGuiButton(int x, int y, int widthIn, int heightIn, String buttonText) {
        super(x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (onDraw(mouseX, mouseY) == 0) {
            super.draw(mouseX, mouseY, partialTicks);
        }
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x - MinecraftClient.getInstance().textRenderer.getStringWidth(text) / 2, y, color);
    }

    @Override
    public void onPressed(double x, double y) {
        onButtonClick(x, y);
    }

    public void onButtonClick(double mouseX, double mouseY) {
    }

    protected int onDraw(int mouseX, int mouseY) {
        return 0;
    }

    protected boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean state) {
        visible = state;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean state) {
        enabled = state;
    }

    protected int getButtonX() {
        return x;
    }

    protected void setButtonX(int x) {
        this.x = x;
    }

    protected int getButtonY() {
        return y;
    }

    protected void setButtonY(int y) {
        this.y = y;
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

    protected boolean isButtonHovered() {
        return isHovered();
    }

    protected void setButtonHovered(boolean state) {
        ((IMixinGuiButton) this).setIsHovered(state);
    }

    public String getButtonText() {
        return getText();
    }

    public void setButtonText(String text) {
        setText(text);
    }

}