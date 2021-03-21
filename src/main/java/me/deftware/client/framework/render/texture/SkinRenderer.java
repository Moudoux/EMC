package me.deftware.client.framework.render.texture;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.gl.GLX;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.UUID;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Deftware
 */
public class SkinRenderer {

    private static final HashMap<String, Pair<Boolean, Identifier>> loadedSkins = new HashMap<>();

    public static void bindSkinTexture(String name, String uuid) {
        GameProfile profile = new GameProfile(UUID.fromString(uuid), name);
        if(loadedSkins.containsKey(name)) {
            Identifier identifier;
            if (loadedSkins.get(name).getLeft()) {
                identifier = loadedSkins.get(name).getRight();
            } else {
                identifier = DefaultSkinHelper.getTexture(profile.getId());
            }
            MinecraftClient.getInstance().getTextureManager().bindTexture(identifier);
            RenderSystem.setShaderTexture(0, identifier);
        } else {
            loadedSkins.put(name, new Pair<>(false, null));
            try {
                MinecraftClient.getInstance().getSkinProvider().loadSkin(profile, (type, identifier, minecraftProfileTexture) -> {
                    if (type == MinecraftProfileTexture.Type.SKIN) {
                        loadedSkins.put(name, new Pair<>(true, identifier));
                    }
                }, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void drawAltFace(String name, String uuid, int x, int y, int w, int h) {
        try {
            bindSkinTexture(name, uuid);
            RenderStack.blend();
            GLX.INSTANCE.color(0.9F, 0.9F, 0.9F, 1.0F);
            DrawableHelper.drawTexture(GLX.INSTANCE.getStack(), x, y, 24, 24, w, h, 192, 192);
            DrawableHelper.drawTexture(GLX.INSTANCE.getStack(), x, y, 120, 24, w, h, 192, 192);
            RenderStack.noBlend();
        } catch (Exception ignored) { }
    }

    public static void drawAltBody(String name, String uuid, int x, int y, int width, int height) {
        try {
            bindSkinTexture(name, uuid);
            boolean slim = DefaultSkinHelper.getModel(UUID.fromString(uuid)).equals("slim");

            RenderStack.blend();
            GLX.INSTANCE.color(1, 1, 1, 1);

            // Face
            x = x + width / 4;
            int w = width / 2;
            int h = height / 4;
            int fw = height * 2;
            int fh = height * 2;
            float u = height / 4f;
            float v = height / 4f;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Hat
            w = width / 2;
            h = height / 4;
            u = height / 4f * 5;
            v = height / 4f;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Chest
            y = y + height / 4;
            w = width / 2;
            h = height / 8 * 3;
            u = height / 4f * 2.5F;
            v = height / 4f * 2.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Jacket
            w = width / 2;
            h = height / 8 * 3;
            u = height / 4f * 2.5F;
            v = height / 4f * 4.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Left Arm
            x = x - width / 16 * (slim ? 3 : 4);
            y = y + (slim ? height / 32 : 0);
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4f * 5.5F;
            v = height / 4f * 2.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Left Sleeve
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4f * 5.5F;
            v = height / 4f * 4.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Right Arm
            x = x + width / 16 * (slim ? 11 : 12);
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4f * 5.5F;
            v = height / 4f * 2.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Right Sleeve
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            u = height / 4f * 5.5F;
            v = height / 4f * 4.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Left Leg
            x = x - width / 2;
            y = y + height / 32 * (slim ? 11 : 12);
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4f * 0.5F;
            v = height / 4f * 2.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Left Pants
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4f * 0.5F;
            v = height / 4f * 4.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Right Leg
            x = x + width / 4;
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4f * 0.5F;
            v = height / 4f * 2.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            // Right Pants
            w = width / 4;
            h = height / 8 * 3;
            u = height / 4f * 0.5F;
            v = height / 4f * 4.5F;
            Screen.drawTexture(GLX.INSTANCE.getStack(), x, y, u, v, w, h, fw, fh);

            RenderStack.noBlend();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
