package me.deftware.client.framework.wrappers.gui;

import me.deftware.client.framework.utils.ResourceUtils;
import me.deftware.client.framework.utils.render.Texture;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class IGuiScreen extends GuiScreen {

	private boolean pause = true;
	private HashMap<String, Texture> textureHashMap = new HashMap<>();

	public IGuiScreen(boolean doesGuiPause) {
		pause = doesGuiPause;
	}

	public IGuiScreen() {
		this(true);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		onDraw(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
		onPostDraw(mouseX, mouseY, partialTicks);
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
	public void tick() {
		super.tick();
		onUpdate();
	}

	@Override
	public void initGui() {
		super.initGui();
		onInitGui();
		children.add(new IGuiEventListener() {

			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
				onMouseClicked((int) Math.round(mouseX), (int) Math.round(mouseY), mouseButton);
				return false;
			}

			@Override
			public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
				onMouseReleased((int) Math.round(mouseX), (int) Math.round(mouseY), mouseButton);
				return false;
			}

		});
	}

	@Override
	public void onGuiClosed() {
		onGuiClose();
		super.onGuiClosed();
	}

	@Override
	public boolean keyPressed(int keyCode, int action, int modifiers) {
		onKeyPressed(keyCode, action, modifiers);
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			IMinecraft.setGuiScreen(null);
		}
		return true;
	}

	public void addEventListener(CustomIGuiEventListener listener) {
		this.children.add(listener);
	}

	protected void drawIDefaultBackground() {
		drawDefaultBackground();
	}

	public void drawDarkOverlay() {
		Gui.drawRect(0, 0, width, height, Integer.MIN_VALUE);
	}

	protected List<GuiButton> getButtonList() {
		return buttons;
	}

	protected void addButton(IGuiButton button) {
		children.add(button);
		buttons.add(button);
	}

	protected ArrayList<IGuiButton> getIButtonList() {
		ArrayList<IGuiButton> list = new ArrayList<>();
		for (GuiButton b : buttons) {
			if (b instanceof IGuiButton) {
				list.add((IGuiButton) b);
			}
		}
		return list;
	}

	protected void clearButtons() {
		buttons.clear();
	}

	/**
	 * Returns a string stored in the system clipboard.
	 */
	public static String getClipboardString() {
		try {
			Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object) null);

			if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String) transferable.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (Exception var1) {
			;
		}

		return "";
	}

	/**
	 * Stores the given string in the system clipboard
	 */
	public static void setClipboardString(String copyText) {
		if (!StringUtils.isNullOrEmpty(copyText)) {
			try {
				StringSelection stringselection = new StringSelection(copyText);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner) null);
			} catch (Exception var2) {
				;
			}
		}
	}

	public static void openLink(String url) {
		Util.getOSType().openURI(url);
	}

	public void drawCenteredString(String text, int x, int y, int color) {
		Minecraft.getInstance().fontRenderer.drawStringWithShadow(text,
				x - Minecraft.getInstance().fontRenderer.getStringWidth(text) / 2, y, color);
	}

	public void setDoesGuiPauseGame(boolean state) {
		pause = state;
	}

	protected void drawTexture(IResourceLocation texture, int x, int y, int width, int height) {
		mc.getTextureManager().bindTexture(texture);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
	}

	protected void drawTexture(String mod, String texture, int x, int y, int width, int height) {
		GL11.glPushMatrix();
		if (!textureHashMap.containsKey(texture)) {
			try {
				BufferedImage img = ImageIO.read(ResourceUtils.getStreamFromModResources(mod, texture));
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
		GuiScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
		GL11.glPopMatrix();
	}

	public static boolean isCtrlPressed() {
		return isCtrlKeyDown();
	}

	public int getIGuiScreenWidth() {
		return width;
	}

	public int getIGuiScreenHeight() {
		return height;
	}

	public static int getScaledHeight() {
		return Minecraft.getInstance().mainWindow.getScaledHeight();
	}

	public static int getScaledWidth() {
		return Minecraft.getInstance().mainWindow.getScaledWidth();
	}

	public static int getDisplayHeight() {
		return Minecraft.getInstance().mainWindow.getHeight();
	}

	public static int getDisplayWidth() {
		return Minecraft.getInstance().mainWindow.getWidth();
	}

	public static boolean isWindowMinimized(){
		if(getDisplayWidth() == 0 || getDisplayHeight() == 0)
			return true;
		return false;
	}

	public void drawITintBackground(int tint) {
		drawBackground(tint);
	}

	public void setFocusedComponent(CustomIGuiEventListener listener) {
		this.setFocused(listener);
	}

	protected void onGuiClose() {
	}

	protected abstract void onInitGui();

	protected void onPostDraw(int mouseX, int mouseY, float partialTicks) {
	}

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

}
