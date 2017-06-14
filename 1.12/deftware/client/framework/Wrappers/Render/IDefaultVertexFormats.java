package me.deftware.client.framework.Wrappers.Render;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class IDefaultVertexFormats {

	public static IVertexFormat get(Types type) {
		IVertexFormat format = null;
		if (type.equals(Types.POSITION_COLOR)) {
			format = new IVertexFormat(DefaultVertexFormats.POSITION_COLOR);
		}
		return format;
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

	public static enum Types {
		POSITION_COLOR
	}

}
