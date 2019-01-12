package me.deftware.client.framework.utils.fonts;

import me.deftware.client.framework.wrappers.IMinecraft;
import org.lwjgl.opengl.GL11;

public class FontRenderer {

    public static int drawString(Font font, int x, int y, String text) {
        font.generateString(text);
        prepareAndPushMatrix(6);
        font.prepareForRendering();
        renderAndPopMatrix(6, x, y, font.getLastRenderedWidth(), font.getLastRenderedHeight(), true);
        return 0;
    }

    public static int drawCenteredString(Font font, int x, int y, String text) {
        font.generateString(text);
        prepareAndPushMatrix(6);
        font.prepareForRendering();
        renderAndPopMatrix(6, x - font.getStringWidth(text)/2, y - font.getStringHeight(text)/2,
                font.getStringWidth(text), font.getStringHeight(text), true);
        return 0;
    }

    public static void drawStringWithShadow(String text) {

    }

    public static void prepareAndPushMatrix(int taskId) {
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST); //3008 alpha test

        GL11.glPushMatrix();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void renderAndPopMatrix(int taskId, int x, int y, int width, int height, boolean autoAdjust){
        int scale = 2;

        //Counter the default scaling
        if(autoAdjust)
        scale = IMinecraft.getGuiScale();

        if(scale == 0)
            scale = 2;

        GL11.glColor3f(0.0f, 0.0f, 0.0f);
        //Draw quad counterclockwise
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2d((x) * scale /2, (y) * scale/2); //top-left
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2d((x) * scale/2, (y + height) * scale/2); //height - down-left
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2d((x + width) * scale/2, (y + height) * scale/2); //width - down-right
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2d((x + width) * scale/2, (y) * scale/2); //top right
        }
        GL11.glEnd();

        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }
}
