package me.deftware.client.framework.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.event.events.EventScaleChange;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.nio.DoubleBuffer;

@SuppressWarnings("All")
public class NonScaledRenderer {

    public static float getScale() {
        return (float) SettingsMap.getValue(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", 1.0f);
    }

    public static void setScale(float scale) {
        SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", scale);
        new WeakReference<>(new EventScaleChange()).get().broadcast();
    }

    public static void drawRectAuto(float x, float y, float width, float height, int c) {
        NonScaledRenderer.drawRect(x, y, x + width, y + height, c);
    }

    public static void drawRectAuto(float x, float y, float width, float height, Color c) {
        NonScaledRenderer.drawRect(x, y, x + width, y + height, c);
    }

    public static void drawRect(float x, float y, float xx, float yy, Color c) {
        NonScaledRenderer.drawRect(x, y, xx, yy, 0, c, getScale());
    }

    public static void drawRect(float x, float y, float xx, float yy, int c) {
        NonScaledRenderer.drawRect(x, y, xx, yy, c, null, getScale());
    }

    public static void drawRect(float x, float y, float xx, float yy, int c, Color color, float scale) {
        x *= scale;
        y *= scale;
        xx *= scale;
        yy *= scale;
        GL11.glPushMatrix();
        GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        if (color == null) {
            float f = (c >> 24 & 0xFF) / 255.0F;
            float f2 = (c >> 16 & 0xFF) / 255.0F;
            float f3 = (c >> 8 & 0xFF) / 255.0F;
            float f4 = (c & 0xFF) / 255.0F;
            GL11.glColor4f(f2, f3, f4, f);
        } else {
            RenderUtils.glColor(color);
        }
        GL11.glBegin(7);
        GL11.glVertex2d(xx, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, yy);
        GL11.glVertex2d(xx, yy);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        IMinecraft.triggerGuiRenderer();
        resetScale();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, int col) {
        xx *= getScale();
        yy *= getScale();
        radius *= getScale();
        float f = (col >> 24 & 0xFF) / 255.0F;
        float f2 = (col >> 16 & 0xFF) / 255.0F;
        float f3 = (col >> 8 & 0xFF) / 255.0F;
        float f4 = (col & 0xFF) / 255.0F;
        GL11.glPushMatrix();
        GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glBegin(6);
        for (int i = 0; i < 50; i++) {
            float x = (float) (radius + 1 * Math.sin(i * 0.12566370614359174D));
            float y = (float) (radius + 1 * Math.cos(i * 0.12566370614359174D));
            GL11.glColor4f(f2, f3, f4, -0.5f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GL11.glEnd();
        GL11.glBegin(6);
        for (int i = 0; i < 50; i++) {
            float x = (float) (radius * Math.sin(i * 0.12566370614359174D));
            float y = (float) (radius * Math.cos(i * 0.12566370614359174D));
            GL11.glColor4f(f2, f3, f4, f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        resetScale();
    }

    public static void resetScale() {
        GlStateManager.clear(256, false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, MinecraftClient.getInstance().getWindow().getFramebufferWidth() / MinecraftClient.getInstance().getWindow().getScaleFactor(), MinecraftClient.getInstance().getWindow().getFramebufferHeight() / MinecraftClient.getInstance().getWindow().getScaleFactor(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translatef(0.0F, 0.0F, -2000.0F);
    }

    public static void drawLine(float x1, float y1, float x2, float y2) {
        x1 *= getScale();
        y1 *= getScale();
        x2 *= getScale();
        y2 *= getScale();
        GL11.glPushMatrix();
        GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        resetScale();
    }

    public static int getMouseX() {
        return getMouseX(getScale());
    }

    public static int getMouseY() {
        return getMouseY(getScale());
    }

    public static int getMouseX(float scale) {
        return (int) (getCursorPos()[0] / scale);
    }

    public static int getMouseY(float scale) {
        return (int) (getCursorPos()[1] / scale);
    }

    public static double[] getCursorPos() {
        double[] pos = new double[2];
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1),
                posY = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(MinecraftClient.getInstance().getWindow().getHandle(), posX, posY);
        pos[0] = posX.get(0);
        pos[1] = posY.get(0);
        return pos;
    }

}

