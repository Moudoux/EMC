package me.deftware.client.framework.utils;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FontRenderer {
    String fontName;
    int fontHeight;
    boolean bold;
    boolean italic;
    boolean underlined;
    boolean memorySaving;

    Texture texturet;

    String lastString = "";

    public FontRenderer(String fontName, int fontHeight, boolean bold, boolean italic, boolean underlined, boolean memorySaving) {
        this.fontName = fontName;
        this.fontHeight = fontHeight;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.memorySaving = memorySaving;
    }

    public Texture drawString(String text, int x, int y){
        if(!text.equals(lastString)) {
            if(texturet != null)
                texturet.destroy();
            BufferedImage premadeTexture = new BufferedImage(500, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = premadeTexture.createGraphics();
            graphics.setColor(Color.CYAN);
            graphics.setFont(new Font("Arial", Font.PLAIN, 15));
            graphics.drawString(text, x, y);
            graphics.dispose();
            texturet = new Texture(500, 20, true);
            texturet.fillFromBufferedImage(premadeTexture);
            texturet.update();
        }

        texturet.updateTexture();

        //me.deftware.client.framework.utils.fonts.Font fnt = new DynamicFont(null,0,null,0);
        return texturet;
        /*texture.bind();
        TexUtil.drawModalRectWithCustomSizedTexture(100, 100, 200, 20, 200, 20, 200, 20);
        texture.destroy();*/

    }

    public Texture drawArialString(String text, int x, int y){
        BufferedImage premadeTexture = new BufferedImage(300, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = premadeTexture.createGraphics();
        graphics.setColor(Color.CYAN);
        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
        graphics.drawString(text, 1, 10);
        graphics.dispose();
        Texture texture = new Texture(300, 10, true);
        texture.fillFromBufferedImage(premadeTexture);
        texture.update();
        return texture;
        /*texture.bind();
        TexUtil.drawModalRectWithCustomSizedTexture(100, 100, 200, 20, 200, 20, 200, 20);
        texture.destroy();*/
    }

    public Texture drawRomanString(String text, int x, int y){
        BufferedImage premadeTexture = new BufferedImage(300, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = premadeTexture.createGraphics();
        graphics.setColor(Color.CYAN);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 10));
        graphics.drawString(text, 1, 10);
        graphics.dispose();
        Texture texture = new Texture(300, 10, true);
        texture.fillFromBufferedImage(premadeTexture);
        texture.update();
        return texture;
        /*texture.bind();
        TexUtil.drawModalRectWithCustomSizedTexture(100, 100, 200, 20, 200, 20, 200, 20);
        texture.destroy();*/
    }

    public Texture drawMontserratString(String text, int x, int y){
        BufferedImage premadeTexture = new BufferedImage(300, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = premadeTexture.createGraphics();
        graphics.setColor(Color.CYAN);
        graphics.setFont(new Font("Montserrat Alternates", Font.ITALIC, 40));
        graphics.drawString(text, 1, 40);
        graphics.dispose();
        Texture texture = new Texture(300, 40, true);
        texture.fillFromBufferedImage(premadeTexture);
        texture.update();
        return texture;
        /*texture.bind();
        TexUtil.drawModalRectWithCustomSizedTexture(100, 100, 200, 20, 200, 20, 200, 20);
        texture.destroy();*/
    }

    public Texture drawRotatedStrings(String text, int x, int y){
        BufferedImage premadeTexture = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = premadeTexture.createGraphics();
        graphics.setColor(Color.CYAN);
        graphics.setFont(new Font("FreeSans", Font.PLAIN, 10));
        graphics.drawString(text, 100, 30);

        AffineTransform at = new AffineTransform();
        at.rotate(Math.PI / 6);
        graphics.setTransform(at);
        graphics.drawString(text, 100, 30);

        at.rotate(Math.PI / 3);
        graphics.setTransform(at);
        graphics.drawString(text, 100, -250);

        at.rotate(Math.PI / 2);
        graphics.setTransform(at);
        graphics.drawString(text, 100, -250);

        graphics.dispose();
        Texture texture = new Texture(300, 300, true);
        texture.fillFromBufferedImage(premadeTexture);
        texture.update();
        return texture;
        /*texture.bind();
        TexUtil.drawModalRectWithCustomSizedTexture(100, 100, 200, 20, 200, 20, 200, 20);
        texture.destroy();*/
    }

    public static void drawSquare(double x1, double y1, double sidelength)
    {
        double halfside = sidelength / 2;

        GL11.glColor3d(1,0,0);
        GL11.glBegin(GL11.GL_POLYGON);

        GL11.glVertex2d(x1 + halfside, y1 + halfside);
        GL11.glVertex2d(x1 + halfside, y1 - halfside);
        GL11.glVertex2d(x1 - halfside, y1 - halfside);
        GL11.glVertex2d(x1 - halfside, y1 + halfside);

        GL11.glEnd();
    }

}
