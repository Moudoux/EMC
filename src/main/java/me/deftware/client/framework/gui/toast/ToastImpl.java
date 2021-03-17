package me.deftware.client.framework.gui.toast;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.render.gl.GLX;
import me.deftware.client.framework.render.texture.GlTexture;
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

	public GlTexture icon;
	public ChatMessage title;
	public ChatMessage[] text;
	public long transitionTime = 2000L, visibilityTime = transitionTime;
	public int width = 160, height = 32, index = 0;
	public int iconWidth = 25, iconHeight = 25;

	public ToastImpl(ChatMessage title, ChatMessage... text) {
		this(null, title, text);
	}

	public ToastImpl(GlTexture icon, ChatMessage title, ChatMessage... text) {
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
		manager.getGame().getTextureManager().bindTexture(TEXTURE);
		GLX.INSTANCE.color(1, 1, 1, 1);
		manager.drawTexture(matrices, 0, 0, 0, 0, width, height);

		// Title
		manager.getGame().textRenderer.draw(matrices, title.build(), 30.0F, 7.0F, -1);

		// Draw text
		if (text != null && text.length != 0) {
			if (text.length > 1) {
				if (startTime > transitionTime * (index + 1) && index + 1 < text.length) {
					index++;
				}
			}
			manager.getGame().textRenderer.draw(matrices, text[index].build(), 30.0F, 18f, -1);
		}

		// Icon
		if (icon != null) {
			GLX.INSTANCE.color(1, 1, 1, 1);
			icon.bind().draw(4, 4, iconWidth, iconHeight);
		}
		return startTime >= visibilityTime ? Visibility.HIDE : Visibility.SHOW;
	}

}

