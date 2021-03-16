package me.deftware.client.framework.render.batching;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.render.gl.GLX;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

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

	public static @Getter boolean inCustomMatrix = false;
	public static final CopyOnWriteArrayList<Runnable> scaleChangeCallback = new CopyOnWriteArrayList<>();
	public static float scale = 1;

	public static float getScale() {
		return scale;
	}

	public static void setScale(float scale) {
		if (scale < 0.5f)
			scale = 0.5f;
		if (scale > 4)
			scale = 4;
		Bootstrap.EMCSettings.putPrimitive("RENDER_SCALE", RenderStack.scale = scale);
		scaleChangeCallback.forEach(Runnable::run);
	}

	protected @Setter boolean customMatrix = true, locked = false, running = false;
	protected float red = 1f, green = 1f, blue = 1f, alpha = 1f, lineWidth = 2f;

	protected BufferBuilder builder = Tessellator.getInstance().getBuffer();

	@Getter @Setter
	protected boolean setupMatrix = false;

	public T setupMatrix() {
		if (customMatrix)
			reloadCustomMatrix();
		// Setup gl
		if (!setupMatrix)
			GLX.INSTANCE.push();
		else
			throw new IllegalStateException("Cannot call setupMatrix twice!");
		if (!locked) {
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderSystem.enableBlend();
			RenderSystem.lineWidth(lineWidth);
			RenderSystem.disableTexture();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.setShader(GameRenderer::method_34540);
		}
		setupMatrix = true;
		return (T) this;
	}

	public T glLineWidth(float width) {
		this.lineWidth = width;
		return (T) this;
	}

	public T glColor(Color color) {
		return glColor(color, color.getAlpha());
	}

	public T glOverrideMatrix(boolean flag) {
		this.customMatrix = flag;
		return (T) this;
	}

	public T glColor(Color color, float alpha) {
		this.red = color.getRed() / 255.0F;
		this.green = color.getGreen() / 255.0F;
		this.blue = color.getBlue() / 255.0F;
		this.alpha = alpha / 255.0F;
		return (T) this;
	}

	public abstract T begin();

	public T begin(int mode) {
		running = true;
		builder.begin(translate(mode), getFormat());
		return (T) this;
	}

	public static VertexFormat.DrawMode translate(int mode) {
		switch (mode) {
			case GL11.GL_QUADS:
				return VertexFormat.DrawMode.QUADS;
			case GL11.GL_LINE_STRIP:
				return VertexFormat.DrawMode.LINE_STRIP;
			case GL11.GL_LINES:
				return VertexFormat.DrawMode.LINES;
			case GL11.GL_TRIANGLE_FAN:
				return VertexFormat.DrawMode.TRIANGLE_FAN;
			case GL11.GL_TRIANGLE_STRIP:
				return VertexFormat.DrawMode.TRIANGLE_STRIP;
			case GL11.GL_TRIANGLES:
				return VertexFormat.DrawMode.TRIANGLES;
		}
		return VertexFormat.DrawMode.QUADS;
	}

	protected VertexFormat getFormat() {
		return VertexFormats.POSITION_COLOR;
	}

	public void end() {
		end(true);
	}

	public void end(boolean pop) {
		running = false;
		drawBuffer();
		if (pop)
			if (setupMatrix)
				GLX.INSTANCE.pop();
			else
				throw new IllegalStateException("Cannot call end() on empty matrix!");
		if (!locked && setupMatrix) {
			RenderSystem.depthMask(true);
			RenderSystem.enableTexture();
			RenderSystem.enableDepthTest();
			if (customMatrix)
				reloadMinecraftMatrix();
		}
		setupMatrix = false;
	}

	protected void drawBuffer() {
		builder.end();
		BufferRenderer.draw(builder);
	}

	public Matrix4f getModel() {
		return GLX.INSTANCE.getModel();
	}

	protected VertexConsumer vertex(double x, double y, double z) {
		return builder.vertex(getModel(), (float) x, (float) y, (float) z);
	}

	/**
	 * Creates a 1 to 1 pixel matrix
	 */
	public static void reloadCustomMatrix() {
		inCustomMatrix = true;
		// Change matrix
		/*GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, MinecraftClient.getInstance().getWindow().getWidth(), MinecraftClient.getInstance().getWindow().getHeight(), 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glColor4f(1f, 1f, 1f, 1f);*/

		RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);

		Window window = MinecraftClient.getInstance().getWindow();
		Matrix4f matrix4f = Matrix4f.method_34239(0.0F, (float) window.getWidth(), 0.0F, (float) window.getHeight(), 1000.0F, 3000.0F);
		RenderSystem.setProjectionMatrix(matrix4f);
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.method_34426();
		matrixStack.translate(0.0D, 0.0D, -2000.0D);
		RenderSystem.applyModelViewMatrix();

	}

	public static void reloadMinecraftMatrix() {
		inCustomMatrix = false;
		// Revert back to Minecraft
		/*RenderSystem.matrixMode(5889);
		RenderSystem.loadIdentity();
		RenderSystem.ortho(0.0D, MinecraftClient.getInstance().getWindow().getFramebufferWidth() / MinecraftClient.getInstance().getWindow().getScaleFactor(),
				MinecraftClient.getInstance().getWindow().getFramebufferHeight() / MinecraftClient.getInstance().getWindow().getScaleFactor(), 0.0D, 1000.0D, 3000.0D);
		RenderSystem.matrixMode(5888);
		RenderSystem.loadIdentity();
		RenderSystem.translatef(0.0F, 0.0F, -2000.0F);*/

		Window window = MinecraftClient.getInstance().getWindow();
		RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
		Matrix4f matrix4f = Matrix4f.method_34239(0.0F, (float) (window.getFramebufferWidth() / window.getScaleFactor()), 0.0F, (float) (window.getFramebufferHeight() / window.getScaleFactor()), 1000.0F, 3000.0F);
		RenderSystem.setProjectionMatrix(matrix4f);
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.method_34426();
		matrixStack.translate(0.0D, 0.0D, -2000.0D);
		RenderSystem.applyModelViewMatrix();

	}

}
