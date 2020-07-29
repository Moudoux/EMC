package me.deftware.client.framework.wrappers.gui;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.utils.ResourceUtils;
import me.deftware.client.framework.utils.render.Texture;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.client.framework.wrappers.gui.imp.ScreenInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class IGuiScreen extends Screen {

    protected ScreenInstance parentInstance;
    protected IGuiScreen parent;
    protected boolean escGoesBack = true;
    protected HashMap<String, Texture> textureHashMap = new HashMap<>();

    public IGuiScreen() {
        super(new LiteralText(""));
    }

    public IGuiScreen(ScreenInstance parent) {
        super(new LiteralText(""));
        this.parentInstance = parent;
    }

    public IGuiScreen(IGuiScreen parent) {
        super(new LiteralText(""));
        this.parent = parent;
    }

    public static String getClipboardString() {
       return MinecraftClient.getInstance().keyboard.getClipboard();
    }

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
        onDraw(mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
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

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public boolean keyPressed(int keyCode, int action, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            if (escGoesBack) {
                goback();
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

    public void addEventListener(CustomIGuiEventListener listener) {
        this.children.add(listener);
    }

    public void addRawEventListener(Element listener) {
        this.children.add(listener);
    }

    protected void drawIDefaultBackground() {
        drawIDefaultBackground(new MatrixStack());
    }

    protected void drawIDefaultBackground(MatrixStack matrixStack) {
        renderBackground(matrixStack);
    }

    public void drawDarkOverlay() {
        drawDarkOverlay(new MatrixStack());
    }

    public void drawDarkOverlay(MatrixStack matrixStack) {
        DrawableHelper.fill(matrixStack, 0, 0, width, height, Integer.MIN_VALUE);
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
        this.drawCenteredString(new MatrixStack(), text, x, y, color);
    }

    public void drawCenteredString(MatrixStack matrixStack, String text, int x, int y, int color) {
        this.drawCenteredString(matrixStack, MinecraftClient.getInstance().textRenderer, text, x, y, color);
    }

    protected void drawTexture(EMCMod mod, String texture, int x, int y, int width, int height) {
        drawTexture(mod, new MatrixStack(), texture, x, y, width, height);
    }

    protected void drawTexture(EMCMod mod, MatrixStack matrixStack, String texture, int x, int y, int width, int height) {
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
        Screen.drawTexture(matrixStack, x, y, 0, 0, width, height, width, height);
        GL11.glPopMatrix();
    }

    protected void drawTexture(IResourceLocation texture, int x, int y, int width, int height) {
        drawTexture(texture, new MatrixStack(), x, y, width, height);
    }

    protected void drawTexture(IResourceLocation texture, MatrixStack matrixStack, int x, int y, int width, int height) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Screen.drawTexture(matrixStack, x, y, 0, 0, width, height, width, height);
    }

    protected void goback() {
        if (parentInstance != null) {
            MinecraftClient.getInstance().openScreen(parentInstance.screen);
        } else {
            IMinecraft.setGuiScreen(parent);
        }
    }

    public int getIGuiScreenWidth() {
        return width;
    }

    public int getIGuiScreenHeight() {
        return height;
    }

    public void drawITintBackground(int tint) {
        drawITintBackground(new MatrixStack(), tint);
    }

    public void drawITintBackground(MatrixStack matrixStack, int tint) {
        renderBackground(matrixStack, tint);
    }

    public void setFocusedComponent(CustomIGuiEventListener listener) {
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
