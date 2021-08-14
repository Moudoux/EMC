package me.deftware.client.framework.gui;

import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.screens.GenericScreen;
import me.deftware.client.framework.gui.screens.MinecraftScreen;
import me.deftware.client.framework.gui.widgets.GenericComponent;
import me.deftware.client.framework.gui.widgets.Label;
import me.deftware.client.framework.gui.widgets.TextField;
import me.deftware.client.framework.input.Mouse;
import me.deftware.client.framework.render.gl.GLX;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

/**
 * @author Deftware
 */
public abstract class GuiScreen extends Screen implements GenericScreen {

	public GenericScreen parent;

	@Setter
	private BackgroundType backgroundType = BackgroundType.Textured;

	public GuiScreen(GenericScreen parent) {
		super(new LiteralText(""));
		this.parent = parent;
	}

	public GuiScreen() {
		this(null);
	}

	@Override
	public boolean mouseReleased(double x, double y, int button) {
		if (onMouseReleased((int) Math.round(x), (int) Math.round(y), button))
			return true;
		return super.mouseReleased(x, y, button);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (onMouseClicked((int) Math.round(mouseX), (int) Math.round(mouseY), mouseButton))
			return true;
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Mouse.updateMousePosition();
		GLX.INSTANCE.refresh();
		if (backgroundType != BackgroundType.None) {
			if (backgroundType == BackgroundType.Textured)
				this.renderBackgroundTexture(0);
			else if (backgroundType == BackgroundType.TexturedOrTransparent)
				this.renderBackground(matrixStack, 0);
		}
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		onDraw(mouseX, mouseY, partialTicks);
		onPostDraw(mouseX, mouseY, partialTicks);
	}

	@Override
	public void resize(MinecraftClient mcIn, int w, int h) {
		super.resize(mcIn, w, h);
		onGuiResize(w, h);
	}

	@Override
	public void tick() {
		super.tick();
		onUpdate();
		// Do textbox cursor tick
		getMinecraftScreen().getChildren(TextField.class)
				.forEach(field -> ((TextFieldWidget) field).tick());
	}

	@Override
	public void init() {
		super.init();
		onInitGui();
	}

	@Override
	public void removed() {
		onGuiClose();
		super.removed();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE && goBack())
			return true;
		if (onKeyPressed(keyCode, scanCode, modifiers))
			return true;
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (onKeyReleased(keyCode, scanCode, modifiers))
			return true;
		return super.keyReleased(keyCode, scanCode, modifiers);
	}

	protected MinecraftScreen getMinecraftScreen() {
		return (MinecraftScreen) this;
	}

	protected void addComponent(GenericComponent component) {
		getMinecraftScreen().addScreenComponent(component);
	}

	protected GuiScreen addText(int x, int y, ChatMessage text) {
		addComponent(
				new Label(x, y, text)
		);
		return this;
	}

	protected GuiScreen addCenteredText(int x, int y, ChatMessage text) {
		addComponent(
				new Label(x - MinecraftClient.getInstance().textRenderer.getWidth(text.build()) / 2, y, text)
		);
		return this;
	}

	protected boolean goBack() {
		MinecraftClient.getInstance().openScreen((Screen) parent);
		return true;
	}

	public int getGuiScreenWidth() {
		return width;
	}

	public int getGuiScreenHeight() {
		return height;
	}

	protected void onGuiClose() { }

	protected abstract void onInitGui();

	protected void onPostDraw(int mouseX, int mouseY, float partialTicks) { }

	protected abstract void onDraw(int mouseX, int mouseY, float partialTicks);

	protected void onUpdate() { }

	/**
	 * @see GLFW#GLFW_RELEASE
	 * @see GLFW#GLFW_PRESS
	 * @see GLFW#GLFW_REPEAT
	 */
	protected boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
		return false;
	}

	protected boolean onKeyReleased(int keyCode, int scanCode, int modifiers) {
		return false;
	}

	protected boolean onMouseReleased(int mouseX, int mouseY, int mouseButton) {
		return false;
	}

	protected boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		return false;
	}

	protected void onGuiResize(int w, int h) { }

	public enum BackgroundType {

		/**
		 * No background will be rendered
		 */
		None,

		/**
		 * A textured background will always be rendered
		 */
		Textured,

		/**
		 * A textured background will be rendered,
		 * but if a world is loaded, a transparent black
		 * overlay will be drawn instead
		 */
		TexturedOrTransparent

	}

	public static int getScaledHeight() {
		return MinecraftClient.getInstance().getWindow().getScaledHeight();
	}

	public static int getScaledWidth() {
		return MinecraftClient.getInstance().getWindow().getScaledWidth();
	}

	public static int getDisplayHeight() {
		return MinecraftClient.getInstance().getWindow().getHeight();
	}

	public static int getDisplayWidth() {
		return MinecraftClient.getInstance().getWindow().getWidth();
	}

}
