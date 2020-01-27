package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.render.BufferBuilder;

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
        vertexbuffer.vertex(x, y, z);
        return this;
    }

    @Deprecated
    public IVertexBuffer tex(float u, float v) {
        vertexbuffer.texture(u, v);
        return this;
    }

    public IVertexBuffer color(float red, float green, float blue, float alpha) {
        vertexbuffer.color(red, green, blue, alpha);
        return this;
    }

    public void endVertex() {
        vertexbuffer.next();
    }

}
