package me.deftware.client.framework.gui.widgets;

import me.deftware.client.framework.gui.GuiEventListener;
import me.deftware.client.framework.render.gl.GLX;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.function.Predicate;

/**
 * @author Deftware
 */
public class TextField extends TextFieldWidget implements GuiEventListener {

	public TextField(int id, int x, int y, int width, int height) {
		super(MinecraftClient.getInstance().textRenderer, x, y, width, height, new LiteralText(""));
	}

	public String getTextboxText() {
		return getText();
	}

	public void setTextboxPasswordMode(boolean flag) {
		((IMixinGuiTextField) this).setPasswordField(flag);
	}

	public void setTextboxPredicate(Predicate<String> textPredicate) {
		this.setTextPredicate(textPredicate);
	}

	public void setTextboxText(String text) {
		setText(text);
	}

	public void setMaxTextboxLenght(int lenght) {
		setMaxLength(lenght);
	}

	public boolean isTextboxFocused() {
		return isFocused();
	}

	public void setTextboxFocused(boolean state) {
		setFocused(state);
	}

	public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
		return keyPressed(keyCode, scanCode, modifiers);
	}

	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void onDraw(int mouseX, int mouseY, float partialTicks) {
		renderButton(GLX.INSTANCE.getStack(), mouseX, mouseY, partialTicks);
	}

	public void doCursorTick() {
		tick();
	}

	public void setTextboxEnabled(boolean state) {
		setEditable(state);
	}

	public int getPosX() {
		return ((IMixinGuiTextField) this).getX();
	}

	public void setPosX(int x) {
		((IMixinGuiTextField) this).setX(x);
	}

	public int getPosY() {
		return ((IMixinGuiTextField) this).getY();
	}

	public void setPosY(int y) {
		((IMixinGuiTextField) this).setY(y);
	}

}

