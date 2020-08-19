package me.deftware.client.framework.render.tessellation;

import net.minecraft.client.render.VertexFormats;

/**
 * @author Deftware
 */
public enum VertexFormat {

	POSITION_COLOR(VertexFormats.POSITION_COLOR), POSITION_TEX_COLOR(VertexFormats.POSITION_TEXTURE_COLOR);

	private final net.minecraft.client.render.VertexFormat format;

	VertexFormat(net.minecraft.client.render.VertexFormat format) {
		this.format = format;
	}

	public net.minecraft.client.render.VertexFormat getMinecraftFormat() {
		return format;
	}

}
