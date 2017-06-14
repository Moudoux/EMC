package me.deftware.client.framework.Wrappers.Render;

import me.deftware.client.framework.Wrappers.Render.IDefaultVertexFormats.IVertexFormat;
import net.minecraft.client.renderer.VertexBuffer;

public class IVertexBuffer {

	private VertexBuffer vertexbuffer;
	
	public IVertexBuffer(ITessellator tessellator) {
		vertexbuffer = tessellator.getTessellator().getBuffer();
	}

	public IVertexBuffer(VertexBuffer buffer) {
		this.vertexbuffer = buffer;
	}

	public void begin(int glMode, IVertexFormat format) {
		vertexbuffer.begin(glMode, format.getFormat());
	}

	public IVertexBuffer pos(double x, double y, double z) {
		vertexbuffer.pos(x, y, z);
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
