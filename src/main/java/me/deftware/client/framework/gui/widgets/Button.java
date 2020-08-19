package me.deftware.client.framework.gui.widgets;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.GuiEventListener;
import me.deftware.mixin.imp.IMixinGuiButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author Deftware
 */
public abstract class Button extends AbstractButtonWidget implements GuiEventListener {

	private @Getter @Setter boolean shouldPlaySound = true;

	public Button(int id, int x, int y, ChatMessage buttonText) {
		super(x, y, 200, 20, buttonText.build());
	}

	public Button(int id, int x, int y, int widthIn, int heightIn, ChatMessage buttonText) {
		super(x, y, widthIn, heightIn, buttonText.build());
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float float_1) {
		if (onDraw(mouseX, mouseY) == 0) {
			super.render(matrixStack, mouseX, mouseY, float_1);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0 && this.clicked(mouseX, mouseY)) {
			if (shouldPlaySound) this.playDownSound(MinecraftClient.getInstance().getSoundManager());
			onButtonClick(x, y);
			return true;
		}
		return false;
	}

	public abstract void onButtonClick(double mouseX, double mouseY);

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

	public ChatMessage getButtonText() {
		return new ChatMessage().fromText(getMessage());
	}

	public Button setButtonText(ChatMessage text) {
		setMessage(text.build());
		return this;
	}

	public void resetToAfter(int ms, ChatMessage text) {
		new Thread(() -> {
			Thread.currentThread().setName("Button reset thread");
			try {
				Thread.sleep(ms);
			} catch (InterruptedException ignored) { }
			setButtonText(text);
		}).start();
	}

}
