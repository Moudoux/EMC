package me.deftware.client.framework.gui;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.minecraft.ScreenInstance;
import me.deftware.client.framework.gui.widgets.Button;
import me.deftware.client.framework.input.Mouse;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.render.texture.Texture;
import me.deftware.client.framework.util.ResourceUtils;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;
import me.deftware.client.framework.util.types.Tuple;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Deftware
 */
public abstract class GuiScreen extends Screen {

	public GuiScreen parent;
	protected boolean escGoesBack = true;
	protected ScreenInstance parentInstance;
	protected HashMap<String, Texture> textureHashMap = new HashMap<>();
	protected @Getter List<Tuple<Integer, Integer, LiteralText>> compiledText = new ArrayList<>();
	private final MatrixStack stack = new MatrixStack();

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
		onMouseReleased((int) Math.round(x), (int) Math.round(y), button);
		super.mouseReleased(x, y, button);
		return false;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		onMouseClicked((int) Math.round(mouseX), (int) Math.round(mouseY), mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
		return false;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Mouse.updateMousePosition();
		onDraw(mouseX, mouseY, partialTicks);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for (Tuple<Integer, Integer, LiteralText> text : compiledText) {
			textRenderer.drawWithShadow(stack, text.getRight(), text.getLeft(), text.getMiddle(), 0xFFFFFF);
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
	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public boolean keyPressed(int keyCode, int action, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			if (escGoesBack) {
				goBack();
				return true;
			}
			return onGoBackRequested();
		} else {
			onKeyPressed(keyCode, action, modifiers);
			// TextFieldWidget inherits AbstractButtonWidget so this applies to both normal buttons and textfield's
			if (keyCode == GLFW.GLFW_KEY_TAB && children.stream().anyMatch(e -> e instanceof AbstractButtonWidget && ((AbstractButtonWidget) e).active)) {
				int i = Iterables.indexOf(children, e -> e instanceof AbstractButtonWidget && ((AbstractButtonWidget) e).isFocused()),
						newIndex = i == Iterables.indexOf(children, e -> e == children.stream().filter(t -> t instanceof AbstractButtonWidget && ((AbstractButtonWidget) t).active).reduce((first, second) -> second).get()) || i == -1 ?
								Iterables.indexOf(children, e -> e == children.stream().filter(t -> t instanceof AbstractButtonWidget && ((AbstractButtonWidget) t).active).findFirst().get()) : i + 1;
				if (i != -1 && ((AbstractButtonWidget) children.get(i)).isFocused()) {
					children.get(newIndex).changeFocus(true);
				}
				children.get(newIndex).changeFocus(true);
			}
			super.keyPressed(keyCode, action, modifiers);
		}
		return false;
	}

	public void addEventListener(GuiEventListener listener) {
		this.children.add(listener);
	}

	public void addRawEventListener(Element listener) {
		this.children.add(listener);
	}

	protected void renderBackgroundWrap(int offset) {
		renderBackground(stack, offset);
	}

	protected void renderBackgroundTextureWrap(int offset) {
		this.renderBackgroundTexture(offset);
	}

	protected GuiScreen addButton(Button button) {
		children.add(button);
		buttons.add(button);
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
		buttons.clear();
		children.removeIf(element -> element instanceof Button);
	}

	protected void clearTexts() {
		compiledText.clear();
	}

	protected void drawTexture(EMCMod mod, String texture, int x, int y, int width, int height) {
		// TODO: Redo this function
		GL11.glPushMatrix();
		if (!textureHashMap.containsKey(texture)) {
			try {
				BufferedImage img = ImageIO.read(Objects.requireNonNull(ResourceUtils.getStreamFromModResources(mod, texture)));
				Texture tex = new Texture(img.getWidth(), img.getHeight(), true);
				tex.fillFromBufferedImageFlip(img);
				tex.update();
				tex.bind();
				textureHashMap.put(texture, tex);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			textureHashMap.get(texture).updateTexture();
		}
		Screen.drawTexture(stack, x, y, 0, 0, width, height, width, height);
		GL11.glPopMatrix();
	}

	protected void drawTexture(MinecraftIdentifier texture, int x, int y, int width, int height) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Screen.drawTexture(stack, x, y, 0, 0, width, height, width, height);
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

	protected abstract void onUpdate();

	/**
	 * @see GLFW#GLFW_RELEASE
	 * @see GLFW#GLFW_PRESS
	 * @see GLFW#GLFW_REPEAT
	 */
	protected abstract void onKeyPressed(int keyCode, int action, int modifiers);

	protected abstract void onMouseReleased(int mouseX, int mouseY, int mouseButton);

	protected abstract void onMouseClicked(int mouseX, int mouseY, int mouseButton);

	protected abstract void onGuiResize(int w, int h);

	protected boolean onGoBackRequested() {
		return false;
	}
}
