package me.deftware.client.framework.render.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * @author Deftware, Ananas
 */
public class GraphicsUtil {

    public static int loadTextureFromBufferedImage(BufferedImage image){
        return loadTextureFromBufferedImage(image, 4, image.getWidth(), image.getHeight());
    }

    public static int loadTextureFromBufferedImage(BufferedImage image, int bytesPerPixel, int width, int height){
        return loadTextureFromBufferedImage(image, bytesPerPixel, width, height, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE);
    }

    public static int loadTextureFromBufferedImage(BufferedImage image, int bytesPerPixel, int width, int height, int clampModeS, int clampModeT){
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * bytesPerPixel); //4 for RGBA, 3 for RGB
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));    // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));     // Green component
                buffer.put((byte) (pixel & 0xFF));            // Blue component
                if(bytesPerPixel==4)
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip();

        return loadTexture(buffer, image.getWidth(), image.getHeight(), clampModeS, clampModeT);
    }

    public static int loadTexture(ByteBuffer bytebuffer, int width, int height, int clampModeS, int clampModeT){
        int textureID = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, clampModeS);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, clampModeT);

        //Setup texture scaling filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, 0);
        GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, 0);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 4);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytebuffer);

        return textureID;
    }

}
