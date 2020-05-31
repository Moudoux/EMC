package me.deftware.client.framework.wrappers.gui;

import me.deftware.mixin.imp.IMixinGuiButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

public class IGuiButton extends AbstractButtonWidget implements CustomIGuiEventListener {

    public IGuiButton(int id, int x, int y, String buttonText) {
        super(x, y, 200, 20, buttonText);
    }

    public IGuiButton(int id, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void render(int mouseX, int mouseY, float float_1) {
        if (onDraw(mouseX, mouseY) == 0) {
            super.render(mouseX, mouseY, float_1);
        }
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(text, x - MinecraftClient.getInstance().textRenderer.getStringWidth(text) / 2f, y, color);
    }

    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (this.active) {
            if (int_1 == 0) {
                boolean boolean_1 = this.isButtonHovered();
                if (boolean_1) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    onButtonClick(x, y);
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public void onButtonClick(double mouseX, double mouseY) { }

    protected int onDraw(int mouseX, int mouseY) {
        return 0;
    }

    protected boolean isVisible() {
        return visible;
    }

    public void setEnabled(boolean state) {
        active = state;
    }

    public void setVisible(boolean state) {
        visible = state;
    }

    public boolean isEnabled() {
        return active;
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
        return getMessage();
    }

    public IGuiButton setButtonText(String text) {
        setMessage(text);
        return this;
    }

    public void resetToAfter(int ms, String text) {
        new Thread(() -> {
            Thread.currentThread().setName("Button reset thread");
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ignored) { }
            setButtonText(text);
        }).start();
    }

}