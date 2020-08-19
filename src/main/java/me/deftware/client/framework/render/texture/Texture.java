package me.deftware.client.framework.render.texture;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Deftware, Ananas
 */
public class Texture {
    int width;
    int height;
    NativeImageBackedTexture dynamicTexture;
    NativeImage nativeImage;

    public Texture(int width, int height, boolean clear) {
        this.width = width;
        this.height = height;
        this.nativeImage = new NativeImage(NativeImage.Format.ABGR, width, height, clear);
        this.dynamicTexture = new NativeImageBackedTexture(nativeImage);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void refreshParameters() {
        this.width = nativeImage.getWidth();
        this.height = nativeImage.getHeight();
    }

    public int fillFromBufferedImage(BufferedImage img) {
        try {
            this.nativeImage = new NativeImage(img.getWidth(), img.getHeight(), true);
            for (int width = 0; width < img.getWidth(); width++) {
                for (int height = 0; height < img.getHeight(); height++) {
                    this.setPixel(width, height, img.getRGB(width, height));
                }

            }

            this.refreshParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

    public int fillFromBufferedImageFlip(BufferedImage img) {
        try {
            this.nativeImage = new NativeImage(img.getWidth(), img.getHeight(), true);
            for (int width = 0; width < img.getWidth(); width++) {
                for (int height = 0; height < img.getHeight(); height++) {
                    int rgb = img.getRGB(width, height);
                    int alpha = (rgb >> 24) & 0xFF;
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    rgb = ((alpha & 0xFF) << 24) |
                            ((blue & 0xFF) << 16) |
                            ((green & 0xFF) << 8) |
                            ((red & 0xFF));
                    this.setPixel(width, height, rgb);
                }

            }

            this.refreshParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

    public int fillFromBufferedFormatImage(BufferedImage img, NativeImage.Format pixelFormat) {
        byte[] imageBytes = ((DataBufferByte) img.getData().getDataBuffer()).getData();
        try {
            this.nativeImage = NativeImage.read(pixelFormat, new ByteArrayInputStream(imageBytes));
            this.dynamicTexture.setImage(nativeImage);
            this.refreshParameters();
        } catch (IOException ioe) {
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

    public int fillFromBufferedRGBImage(BufferedImage img) {
        return fillFromBufferedFormatImage(img, NativeImage.Format.BGR);
    }


    public int fillFromBufferedRGBAImage(BufferedImage img) {
        return fillFromBufferedFormatImage(img, NativeImage.Format.ABGR);
    }

    public int clearPixels() {
        try {
            for (int x = 0; x < nativeImage.getWidth(); x++) {
                for (int y = 0; y < nativeImage.getHeight(); y++) {
                    int rgb = ((0xFF) << 24) |
                            ((0xFF) << 16) |
                            ((0xFF) << 8) |
                            ((0xFF));
                    this.setPixel(x, y, rgb);
                }
            }
            this.dynamicTexture.setImage(this.nativeImage);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }


    public void setPixel(int x, int y, int red, int green, int blue) {
        int rgb = ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8) |
                ((blue & 0xFF));
        this.nativeImage.setPixelColor(x, y, rgb);
    }

    public void setPixel(int x, int y, int red, int green, int blue, int alpha) {
        int rgba = ((alpha & 0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8) |
                ((blue & 0xFF));
        this.nativeImage.setPixelColor(x, y, rgba);
    }

    public void setPixel(int x, int y, int rgba) {
        this.nativeImage.setPixelColor(x, y, rgba);
    }

    public int getPixel(int x, int y) {
        return this.nativeImage.getPixelColor(x, y);
    }

    public byte getAlpha(int x, int y) {
        return this.nativeImage.getPixelOpacity(x, y);
    }

    public int updatePixels() {
        try {
            this.dynamicTexture.setImage(nativeImage);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public int updateTexture() {
        this.dynamicTexture.upload();
        return 0;
    }

    public int update() {
        int errorCode = 0;
        errorCode += this.updateTexture();
        this.refreshParameters();
        errorCode += this.updatePixels();
        return errorCode;
    }

    public void bind() {
        this.bind(GL_ONE);
    }


    public void bind(int blendfunc) {
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, blendfunc);
        this.blindBind();
    }


    public void blindBind() {
        this.dynamicTexture.bindTexture();
    }


    public void unbind() {
        if (GL11.glIsEnabled(GL_BLEND))
            GL11.glDisable(GL_BLEND);
    }

    public void destroy() {
        nativeImage.close();
        nativeImage = null;
        dynamicTexture.close();
        dynamicTexture = null;
    }

}

