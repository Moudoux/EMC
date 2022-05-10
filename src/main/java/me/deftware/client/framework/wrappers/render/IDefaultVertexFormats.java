package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class IDefaultVertexFormats {

	public static IVertexFormat get(Types type) {
		return type.equals(Types.POSITION_COLOR) ?
				new IVertexFormat(DefaultVertexFormats.POSITION_COLOR) :
				new IVertexFormat(DefaultVertexFormats.POSITION_TEX_COLOR);
	}

	public static class IVertexFormat {

		private VertexFormat format;

		public IVertexFormat(VertexFormat format) {
			this.format = format;
		}

		public VertexFormat getFormat() {
			return format;
		}

	}

	public enum Types {
		POSITION_COLOR, POSITION_TEX_COLOR
	}

}
