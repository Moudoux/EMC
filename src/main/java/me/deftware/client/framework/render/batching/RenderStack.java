package me.deftware.client.framework.render.batching;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.event.events.EventScaleChange;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * new RenderStackImpl()
 * optional glLineWidth
 * .begin().setupMatrix()
 * optional .glColor(color).
 * call to draw functions
 * .end();
 *
 * @author Deftware
 */
@SuppressWarnings("unchecked")
public abstract class RenderStack<T> {

	protected float red, green, blue, alpha, lineWidth = 2f;

	public static float getScale() {
		return (float) SettingsMap.getValue(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", 1.0f);
	}

	public static void setScale(float scale) {
		SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", scale);
		new EventScaleChange().broadcast();
	}

	public T setupMatrix() {
		return setupMatrix(MinecraftClient.getInstance().getWindow().getWidth(), MinecraftClient.getInstance().getWindow().getHeight());
	}

	public T setupMatrix(int matrixWidth, int matrixHeight) {
		GL11.glPushMatrix();
		reloadCustomMatrix(matrixWidth, matrixHeight);
		// Setup gl
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWidth);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		return (T) this;
	}

	public T glLineWidth(float width) {
		this.lineWidth = width;
		return (T) this;
	}

	public T glColor(Color color) {
		return glColor(color, color.getAlpha());
	}

	public T glColor(Color color, float alpha) {
		this.red = color.getRed() / 255.0F;
		this.green = color.getGreen() / 255.0F;
		this.blue = color.getBlue() / 255.0F;
		this.alpha = alpha / 255.0F;
		GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
		return (T) this;
	}

	public abstract T begin();

	public T begin(int mode) {
		GL11.glBegin(mode);
		glColor(Color.red, 100f); // Default color
		return (T) this;
	}

	public void end() {
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		reloadMinecraftMatrix();
	}

	public static void reloadCustomMatrix(int matrixWidth, int matrixHeight) {
		// Change matrix
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, matrixWidth, matrixHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public static void reloadMinecraftMatrix() {
		RenderSystem.matrixMode(5889);
		RenderSystem.loadIdentity();
		RenderSystem.ortho(0.0D, MinecraftClient.getInstance().getWindow().getFramebufferWidth() / MinecraftClient.getInstance().getWindow().getScaleFactor(),
				MinecraftClient.getInstance().getWindow().getFramebufferHeight() / MinecraftClient.getInstance().getWindow().getScaleFactor(), 0.0D, 1000.0D, 3000.0D);
		RenderSystem.matrixMode(5888);
		RenderSystem.loadIdentity();
		RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
		RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
	}

}
