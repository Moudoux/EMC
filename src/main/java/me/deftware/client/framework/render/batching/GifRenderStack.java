package me.deftware.client.framework.render.batching;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import me.deftware.client.framework.render.texture.GraphicsUtil;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Deftware
 */
public class GifRenderStack extends RenderStack<GifRenderStack> {

    private final static Logger logger = LogManager.getLogger("GifRenderer");

    @Getter
    private boolean isAvailable = false;

    /**
     * The size of the texture
     */
    @Getter
    private int width = 0, height = 0;

    /**
     * Frame index to frame data
     */
    @Getter
    private final Map<Integer, Frame> frames = new HashMap<>();

    @Getter
    private int glId = -1;

    public GifRenderStack(GifProvider gif) {
        try {
            // Initialize texture atlas
            logger.debug("Loading gif, with {} frames", gif.getFrameCount());

            // Calculate size
            this.width = gif.getWidth();
            this.height = gif.getHeight();

            // Allocate image
            glId = GraphicsUtil.loadTextureFromBufferedImage(
                gif.getFrame(0)
            );

            // Loop all frames after the first frame
            for (int i = 0; i < gif.getFrameCount(); i++) {
                BufferedImage image = gif.getFrame(i);
                frames.put(i, new Frame(image.getWidth(), image.getHeight(), gif.getDelay(i), getImageBuffer(image)));
            }

            logger.debug("Successfully loaded gif");

            isAvailable = true;
        } catch (Throwable ex) {
            logger.error("Failed to load gif", ex);
        }
    }

    private ByteBuffer getImageBuffer(BufferedImage image) {
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 for RGBA, 3 for RGB
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));    // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));     // Green component
                buffer.put((byte) (pixel & 0xFF));            // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip();
        return buffer;
    }

    private int frameIndex = 0;
    private long lastFrame = System.currentTimeMillis();

    private void next() {
        if (lastFrame < System.currentTimeMillis()) {
            if (frameIndex >= frames.size())
                frameIndex = 0;
            Frame frame = frames.get(this.frameIndex);
            // Update delay
            lastFrame = System.currentTimeMillis() + frame.getDelay();
            // Update texture
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, frame.getBuffer());
            this.frameIndex++;
        }
    }

    @Override
    public GifRenderStack begin() {
        if (!isAvailable)
            throw new RuntimeException("Cannot render unavailable gif!");
        RenderSystem.enableTexture();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
        RenderSystem.setShaderTexture(0, glId);
        return begin(GL11.GL_QUADS);
    }

    @Override
    public void end() {
        super.end();
        RenderSystem.disableTexture();
    }

    @Override
    protected VertexFormat getFormat() {
        return VertexFormats.POSITION_TEXTURE;
    }

    @Override
    protected void setShader() {
        // POSITION_TEXTURE
        RenderSystem.setShader(GameRenderer::method_34542);
    }

    @Override
    protected VertexConsumer vertex(double x, double y, double z) {
        return builder.vertex((float) x,  (float) y,  (float) z);
    }

    public GifRenderStack draw(int x0, int y0, int x1, int y1) {
        // Draw frame
        int u0 = 0, u1 = 1, v0 = 0, v1 = 1;
        vertex(x0, y1, 0).texture(u0, v1).next();
        vertex(x1, y1, 0).texture(u1, v1).next();
        vertex(x1, y0, 0).texture(u1, v0).next();
        vertex(x0, y0, 0).texture(u0, v0).next();
        // Update
        next();
        return this;
    }

    public void destroy() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glId);
        GL11.glDeleteTextures(glId);
        glId = -1;
    }

    @Data
    @AllArgsConstructor
    private static class Frame {

        /**
         * Frame size
         */
        private final int width, height;

        /**
         * The delay in ms until the next frame can be drawn
         */
        private final int delay;

        /**
         * A buffer that contains the pixel data of a frame
         */
        private final ByteBuffer buffer;

    }

    public interface GifProvider {

        /**
         * @return The total amount of frames
         */
        int getFrameCount();

        /**
         * @return The image for a frame
         */
        BufferedImage getFrame(int index);

        /**
         * @return The delay before showing the next frame, in ms
         */
        int getDelay(int index);

        /**
         * @return The width of the gif
         */
        int getWidth();

        /**
         * @return The height if the gif
         */
        int getHeight();

    }

}
