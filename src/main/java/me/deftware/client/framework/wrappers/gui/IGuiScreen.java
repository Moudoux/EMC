package me.deftware.client.framework.wrappers.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public abstract class IGuiScreen extends GuiScreen {

	private boolean pause = true;

	public IGuiScreen(boolean doesGuiPause) {
		pause = doesGuiPause;
	}

	public IGuiScreen() {
		this(true);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		onDraw(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
		onPostDraw(mouseX, mouseY, partialTicks);
	}

	@Override
	public void handleMouseInput() throws IOException {
		onMouseInput();
		super.handleMouseInput();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		onKeyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		onMouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		onMouseReleased(mouseX, mouseY, state);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return pause;
	}

	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		super.onResize(mcIn, w, h);
		onGuiResize(w, h);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		onUpdate();
	}

	@Override
	public void initGui() {
		super.initGui();
		onInitGui();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		onActionPerformed(button.id, button.enabled);
	}

	@Override
	public void onGuiClosed() {
		onGuiClose();
		super.onGuiClosed();
	}

	protected void drawIDefaultBackground() {
		drawDefaultBackground();
	}

	public void drawDarkOverlay() {
		Gui.drawRect(0, 0, width, height, Integer.MIN_VALUE);
	}

	protected List<GuiButton> getButtonList() {
		return buttonList;
	}

	protected void addButton(IGuiButton button) {
		buttonList.add(button);
	}

	protected ArrayList<IGuiButton> getIButtonList() {
		ArrayList<IGuiButton> list = new ArrayList<>();
		for (GuiButton b : buttonList) {
			if (b instanceof IGuiButton) {
				list.add((IGuiButton) b);
			}
		}
		return list;
	}

	protected void clearButtons() {
		buttonList.clear();
	}

	protected String getClipboard() {
		return GuiScreen.getClipboardString();
	}

	protected void setClipboard(String clipboard) {
		GuiScreen.setClipboardString(clipboard);
	}

	public void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text,
				x - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public void setDoesGuiPauseGame(boolean state) {
		pause = state;
	}

	public static boolean isCtrlKeyDown() {
		return Minecraft.IS_RUNNING_ON_MAC ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)
				: Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
	}

	public static boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	}

	public static void openLink(String url) {
		try {
			Desktop.getDesktop().browse(new URL(url).toURI());
		} catch (Exception e) {
		}
	}

	public static boolean isAltKeyDown() {
		return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
	}

	public static boolean isKeyDown(int id) {
		return Keyboard.isKeyDown(id);
	}

	protected void drawTexture(IResourceLocation texture, int x, int y, int width, int height) {
		mc.getTextureManager().bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GuiScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
	}

	public int getIGuiScreenWidth() {
		return width;
	}

	public int getIGuiScreenHeight() {
		return height;
	}

	protected int getDisplayScreenWidth() {
		return Display.getWidth();
	}

	protected int getDisplayScreenHeight() {
		return Display.getHeight();
	}

	public static int getScaledHeight() {
		ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
		return r.getScaledHeight();
	}

	public static int getScaledWidth() {
		ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
		return r.getScaledWidth();
	}

	public void drawITintBackground(int tint) {
		drawBackground(tint);
	}

	protected void onGuiClose() {
	}

	protected void onMouseInput() {
	}

	protected abstract void onInitGui();

	protected void onPostDraw(int mouseX, int mouseY, float partialTicks) {
	}

	protected abstract void onDraw(int mouseX, int mouseY, float partialTicks);

	protected abstract void onUpdate();

	protected abstract void onActionPerformed(int buttonID, boolean enabled);

	protected abstract void onKeyTyped(char typedChar, int keyCode);

	protected abstract void onMouseReleased(int mouseX, int mouseY, int mouseButton);

	protected abstract void onMouseClicked(int mouseX, int mouseY, int mouseButton);

	protected abstract void onGuiResize(int w, int h);

	public void onTick() {

	}

}
