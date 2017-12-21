package me.deftware.client.framework.Wrappers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;

public class IFaceRenderer {

	public static void renderFace(String name, int x, int y, int width, int height) {
		try {
			AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name)
					.loadTexture(Minecraft.getMinecraft().getResourceManager());
			Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(0.9F, 0.9F, 0.9F, 1);

			Gui.drawModalRectWithCustomSizedTexture(x, y, 24, 24, width, height, 192, 192);
			Gui.drawModalRectWithCustomSizedTexture(x, y, 120, 24, width, height, 192, 192);

			GL11.glDisable(GL11.GL_BLEND);
		} catch (Exception ex) {
			;
		}
	}

}
