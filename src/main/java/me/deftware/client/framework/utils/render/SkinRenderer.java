package me.deftware.client.framework.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.ThreadDownloadImageData;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class SkinRenderer {

    private static ArrayList<String> loaded = new ArrayList<>();

    private static void downloadAndBindSkinTexture(String name) {
        ResourceLocation location = AbstractClientPlayer.getLocationSkin(name);
        if (loaded.contains(name)) {
            Minecraft.getInstance().getTextureManager().bindTexture(location);
            return;
        }
        loaded.add(name);
        try {
            ThreadDownloadImageData img =
                    AbstractClientPlayer.getDownloadImageSkin(location, name);
            img.loadTexture(Minecraft.getInstance().getResourceManager());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Minecraft.getInstance().getTextureManager().bindTexture(location);
    }

    public static void drawAltBody(String name, int x, int y, int width, int height) {
        try {
            downloadAndBindSkinTexture(name);
            boolean slim = DefaultPlayerSkin
                    .getSkinType(EntityPlayerSP.getOfflineUUID(name)).equals("slim");

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);

            // Face
            x = x + width / 4;
            y = y + 0;
            int w = width / 2;
            int h = height / 4;
            float fw = height * 2;
            float fh = height * 2;
            float u = height / 4;
            float v = height / 4;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Hat
            x = x + 0;
            y = y + 0;
            w = width / 2;
            h = height / 4;
            u = height / 4 * 5;
            v = height / 4;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Chest
            x = x + 0;
            y = y + height / 4;
            w = width / 2;
            h = height / 8 * 3;
            u = height / 4 * 2.5F;
            v = height / 4 * 2.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Jacket
            x = x + 0;
            y = y + 0;
            w = width / 2;
            h = height / 8 * 3;
            u = height / 4 * 2.5F;
            v = height / 4 * 4.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Left Arm
            x = x - width / 16 * (slim ? 3 : 4);
            y = y + (slim ? height / 32 : 0);
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4 * 5.5F;
            v = height / 4 * 2.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Left Sleeve
            x = x + 0;
            y = y + 0;
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4 * 5.5F;
            v = height / 4 * 4.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Right Arm
            x = x + width / 16 * (slim ? 11 : 12);
            y = y + 0;
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4 * 5.5F;
            v = height / 4 * 2.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Right Sleeve
            x = x + 0;
            y = y + 0;
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4 * 5.5F;
            v = height / 4 * 4.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Left Leg
            x = x - width / 2;
            y = y + height / 32 * (slim ? 11 : 12);
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4 * 0.5F;
            v = height / 4 * 2.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Left Pants
            x = x + 0;
            y = y + 0;
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4 * 0.5F;
            v = height / 4 * 4.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Right Leg
            x = x + width / 4;
            y = y + 0;
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4 * 0.5F;
            v = height / 4 * 2.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            // Right Pants
            x = x + 0;
            y = y + 0;
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4 * 0.5F;
            v = height / 4 * 4.5F;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);

            GL11.glDisable(GL11.GL_BLEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
