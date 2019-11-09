package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class IDefaultVertexFormats {

    public static IVertexFormat get(Types type) {
        return type.equals(Types.POSITION_COLOR) ?
                new IVertexFormat(VertexFormats.POSITION_COLOR) :
                new IVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR);
    }

    public enum Types {
        POSITION_COLOR, POSITION_TEX_COLOR
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

}
