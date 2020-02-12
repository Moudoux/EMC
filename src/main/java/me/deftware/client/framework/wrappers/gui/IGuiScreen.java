package me.deftware.client.framework.wrappers.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.utils.ResourceUtils;
import me.deftware.client.framework.utils.render.Texture;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class IGuiScreen extends Screen {

    protected IGuiScreen parent = null;
    protected boolean escGoesBack;
    private boolean pause = true;
    private HashMap<String, Texture> textureHashMap = new HashMap<>();

    @Deprecated
    public IGuiScreen(boolean pause) {
        this("", true);
    }

    public IGuiScreen(String screenTitle, boolean escGoesBack) {
        super(new LiteralText(screenTitle));
        this.escGoesBack = escGoesBack;
    }

    public IGuiScreen() {
        this("", false);
    }

    public IGuiScreen(IGuiScreen parent) {
        this("", true);
        this.parent = parent;
    }

    /**
     * Returns a string stored in the system clipboard.
     */
    public static String getClipboardString() {
       return MinecraftClient.getInstance().keyboard.getClipboard();
    }

    /**
     * Stores the given string in the system clipboard
     */
    public static void setClipboardString(String copyText) {
        MinecraftClient.getInstance().keyboard.setClipboard(copyText);
    }

    public static void openLink(String url) {
        Util.getOperatingSystem().open(url);
    }

    public static boolean isCtrlPressed() {
        return hasControlDown();
    }

    public static boolean isShiftPressed() {
        return hasShiftDown();
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

    public boolean mouseReleased(double x, double y, int button) {
        children.forEach((listener) -> listener.mouseReleased(x, y, button));
        return false;
    }

    public static boolean isWindowMinimized() {
        return getDisplayWidth() == 0 || getDisplayHeight() == 0;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        onDraw(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
        onPostDraw(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return pause;
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
        children.add(new Element() {

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
    public void removed() {
        onGuiClose();
        super.removed();
    }

    @Override
    public boolean keyPressed(int keyCode, int action, int modifiers) {
        onKeyPressed(keyCode, action, modifiers);
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && escGoesBack) {
            IMinecraft.setGuiScreen(parent);
        }
        return true;
    }

    public void addEventListener(CustomIGuiEventListener listener) {
        this.children.add(listener);
    }

    public void addRawEventListener(Element listener) {
        this.children.add(listener);
    }

    protected void drawIDefaultBackground() {
        renderBackground();
    }

    public void drawDarkOverlay() {
        DrawableHelper.fill(0, 0, width, height, Integer.MIN_VALUE);
    }

    protected List<AbstractButtonWidget> getButtonList() {
        return buttons;
    }

    protected void addButton(IGuiButton button) {
        children.add(button);
        buttons.add(button);
    }

    protected ArrayList<IGuiButton> getIButtonList() {
        ArrayList<IGuiButton> list = new ArrayList<>();
        for (AbstractButtonWidget b : buttons) {
            if (b instanceof IGuiButton) {
                list.add((IGuiButton) b);
            }
        }
        return list;
    }

    protected void clearButtons() {
        buttons.clear();
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(text,
                x - MinecraftClient.getInstance().textRenderer.getStringWidth(text) / 2f, y, color);
    }

    public void setDoesGuiPauseGame(boolean state) {
        pause = state;
    }

    protected void drawTexture(EMCMod mod, String texture, int x, int y, int width, int height) {
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
        Screen.blit(x, y, 0, 0, width, height, width, height);
        GL11.glPopMatrix();
    }

    protected void drawTexture(IResourceLocation texture, int x, int y, int width, int height) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Screen.blit(x, y, 0, 0, width, height, width, height);
    }

    public int getIGuiScreenWidth() {
        return width;
    }

    public int getIGuiScreenHeight() {
        return height;
    }

    public void drawITintBackground(int tint) {
        renderBackground(tint);
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
