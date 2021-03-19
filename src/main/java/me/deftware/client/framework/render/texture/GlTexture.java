package me.deftware.client.framework.render.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.render.gl.GLX;
import me.deftware.client.framework.util.ResourceUtils;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Deftware
 */
public class GlTexture {

    @Getter
    private int glId;

    @Getter
    private final int textureWidth, textureHeight;

    public GlTexture(EMCMod mod, String asset) throws IOException {
        this(
                ResourceUtils.getStreamFromModResources(mod, asset)
        );
    }

    public GlTexture(File file) throws IOException {
        this(ImageIO.read(file));
    }

    public GlTexture(InputStream stream) throws IOException {
        this(ImageIO.read(stream));
    }

    public GlTexture(BufferedImage image) {
        this.textureWidth = image.getWidth();
        this.textureHeight = image.getHeight();
        glId = GraphicsUtil.loadTextureFromBufferedImage(image);
    }

    public void draw(int x, int y, int width, int height) {
        draw(x, y, width, height, 0, 0, width, height);
    }

    public void draw(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight) {
        Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public GlTexture bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
        RenderSystem.setShaderTexture(0, glId);
        return this;
    }

    public void upload(BufferedImage image) {
        upload(
                GraphicsUtil.getImageBuffer(image)
        );
    }

    public void upload(ByteBuffer buffer) {
        // Minecraft modifies these
        GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, 0);
        GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, 0);
        GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, 0);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 4);
        // Upload texture
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, textureWidth, textureHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }

    public void destroy() {
        bind();
        GL11.glDeleteTextures(glId);
        glId = -1;
    }

}
