package me.deftware.client.framework.wrappers.toast;

import me.deftware.client.framework.utils.render.Texture;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

/**
 * A custom toast implementation that can be used by EMC mods
 *
 * @author Deftware
 */
public class ToastImpl implements Toast {

	public Texture icon;
	public String title;
	public String[] text;
	public long transitionTime = 2000L, visibilityTime = transitionTime;
	public int width = 160, height = 32, index = 0;
	public int iconWidth = 25, iconHeight = 25;

	public ToastImpl(String title, String...text) {
		this(null, title, text);
	}

	public ToastImpl(Texture icon, String title, String...text) {
		this.text = text;
		this.title = title;
		this.icon = icon;
		if (text != null && text.length > 0) {
			visibilityTime *= text.length;
		}
	}

	@Override
	public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
		// Texture
		manager.getGame().getTextureManager().bindTexture(TOASTS_TEX);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		manager.drawTexture(matrices, 0, 0, 0, 0, width, height);

		// Title
		manager.getGame().textRenderer.draw(matrices, title, 30.0F, 7.0F, -1);

		// Draw text
		if (text != null && text.length != 0) {
			if (text.length > 1) {
				if (startTime > transitionTime * (index + 1) && index + 1 < text.length) {
					index++;
				}
			}
			manager.getGame().textRenderer.draw(matrices, text[index], 30.0F, 18f, -1);
		}

		// Icon
		if (icon != null) {
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			icon.updateTexture();
			Screen.drawTexture(matrices, 4, 4, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
			GL11.glPopMatrix();
		}
		return startTime >= visibilityTime ? Visibility.HIDE : Visibility.SHOW;
	}

}
