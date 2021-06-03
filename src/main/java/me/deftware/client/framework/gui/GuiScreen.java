package me.deftware.client.framework.gui;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.minecraft.ScreenInstance;
import me.deftware.client.framework.gui.widgets.Button;
import me.deftware.client.framework.input.Mouse;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.render.gl.GLX;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;
import me.deftware.client.framework.util.types.Tuple;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deftware
 */
public abstract class GuiScreen extends Screen {

	public GuiScreen parent;
	protected boolean escGoesBack = true;
	protected ScreenInstance parentInstance;
	protected @Getter List<Tuple<Integer, Integer, LiteralText>> compiledText = new ArrayList<>();

	public GuiScreen() {
		super(new LiteralText(""));
	}

	public GuiScreen(ScreenInstance parent) {
		super(new LiteralText(""));
		this.parentInstance = parent;
	}

	public GuiScreen(GuiScreen parent) {
		super(new LiteralText(""));
		this.parent = parent;
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
		onDraw(mouseX, mouseY, partialTicks);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for (Tuple<Integer, Integer, LiteralText> text : compiledText) {
			textRenderer.drawWithShadow(GLX.INSTANCE.getStack(), text.getRight(), text.getLeft(), text.getMiddle(), 0xFFFFFF);
		}
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
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			if (escGoesBack) {
				goBack();
				return true;
			}
			return onGoBackRequested();
		} else {
			if (onKeyPressed(keyCode, scanCode, modifiers))
				return true;
			return super.keyPressed(keyCode, scanCode, modifiers);
		}
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (onKeyReleased(keyCode, scanCode, modifiers))
			return true;
		return super.keyReleased(keyCode, scanCode, modifiers);
	}

	public void addEventListener(GuiEventListener listener) {
		addRawEventListener(listener);
	}

	public void addRawEventListener(Element listener) {
		((IMixinGuiScreen) this).addChildElement(listener);
	}

	protected void renderBackgroundWrap(int offset) {
		renderBackground(GLX.INSTANCE.getStack(), offset);
	}

	protected void renderBackgroundTextureWrap(int offset) {
		this.renderBackgroundTexture(offset);
	}

	protected GuiScreen addButton(Button button) {
		addDrawable(button);
		addRawEventListener(button);
		return this;
	}

	protected GuiScreen addText(int x, int y, ChatMessage text) {
		compiledText.add(new Tuple<>(x, y, text.build()));
		return this;
	}

	protected GuiScreen addCenteredText(int x, int y, ChatMessage text) {
		compiledText.add(new Tuple<>(x - MinecraftClient.getInstance().textRenderer.getWidth(text.build()) / 2, y, text.build()));
		return this;
	}

	protected List<Button> getIButtonList() {
		return ((IMixinGuiScreen) this).getEmcButtons();
	}

	protected void clearButtons() {
		clearChildren();
	}

	protected void clearTexts() {
		compiledText.clear();
	}

	public static void drawTexture(MinecraftIdentifier texture, int x, int y, int width, int height) {
		drawTexture(texture, x, y, 0, 0, width, height, width, height);
	}

	public static void drawTexture(MinecraftIdentifier texture, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, width, height, textureWidth, textureHeight);
	}

	protected void goBack() {
		if (parentInstance != null) {
			MinecraftClient.getInstance().openScreen(parentInstance.getMinecraftScreen());
		} else {
			Minecraft.openScreen(parent);
		}
	}

	public int getGuiScreenWidth() {
		return width;
	}

	public int getGuiScreenHeight() {
		return height;
	}

	public void setFocusedComponent(GuiEventListener listener) {
		this.setFocused(listener);
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
	protected boolean onKeyPressed(int keyCode, int scanCode, int modifiers) { return false; }

	protected boolean onKeyReleased(int keyCode, int scanCode, int modifiers) { return false; }

	protected boolean onMouseReleased(int mouseX, int mouseY, int mouseButton) { return false; }

	protected boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) { return false; }

	protected void onGuiResize(int w, int h) { }

	protected boolean onGoBackRequested() {
		return false;
	}
}
