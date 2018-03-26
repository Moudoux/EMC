package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.renderer.BufferBuilder;

public class IVertexBuffer {

	private BufferBuilder vertexbuffer;

	public IVertexBuffer(ITessellator tessellator) {
		vertexbuffer = tessellator.getTessellator().getBuffer();
	}

	public IVertexBuffer(BufferBuilder buffer) {
		vertexbuffer = buffer;
	}

	public void begin(int glMode, IDefaultVertexFormats.IVertexFormat format) {
		vertexbuffer.begin(glMode, format.getFormat());
	}

	public IVertexBuffer pos(double x, double y, double z) {
		vertexbuffer.pos(x, y, z);
		return this;
	}

	public IVertexBuffer tex(double u, double v) {
		vertexbuffer.tex(u, v);
		return this;
	}

	public IVertexBuffer color(float red, float green, float blue, float alpha) {
		vertexbuffer.color(red, green, blue, alpha);
		return this;
	}

	public void endVertex() {
		vertexbuffer.endVertex();
	}

}
