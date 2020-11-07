package me.deftware.client.framework.render.tessellation;

import net.minecraft.client.render.BufferBuilder;

/**
 * @author Deftware
 */
public class VertexBuilder {

	private final BufferBuilder builder;

	public VertexBuilder(BufferBuilder builder) {
		this.builder = builder;
	}

	public static net.minecraft.client.render.VertexFormat.DrawMode intToDrawMode(int glMode) {
		return net.minecraft.client.render.VertexFormat.DrawMode.QUADS; // TODO: Fix this
	}

	public void begin(int glMode, VertexFormat format) {
		builder.begin(intToDrawMode(glMode), format.getMinecraftFormat());
	}

	public VertexBuilder pos(double x, double y, double z) {
		builder.vertex(x, y, z);
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
