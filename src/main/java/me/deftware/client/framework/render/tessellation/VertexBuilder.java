package me.deftware.client.framework.render.tessellation;

import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.gl.GLX;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.Matrix4f;

/**
 * @author Deftware
 */
public class VertexBuilder {

	private final BufferBuilder builder;

	public VertexBuilder(BufferBuilder builder) {
		this.builder = builder;
	}

	public void begin(int glMode, VertexFormat format) {
		builder.begin(RenderStack.translate(glMode), format.getMinecraftFormat());
	}

	public VertexBuilder pos(double x, double y, double z) {
		Matrix4f model = GLX.INSTANCE.getModel();
		builder.vertex(model, (float) x, (float) y, (float) z);
		return this;
	}

	public VertexBuilder tex(float u, float v) {
		builder.texture(u, v);
		return this;
	}

	public VertexBuilder color(float red, float green, float blue, float alpha) {
		builder.color(red, green, blue, alpha);
		return this;
	}

	public void endVertex() {
		builder.next();
	}

}
