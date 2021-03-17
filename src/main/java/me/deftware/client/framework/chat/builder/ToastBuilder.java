package me.deftware.client.framework.chat.builder;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.toast.ToastAPI;
import me.deftware.client.framework.gui.toast.ToastImpl;
import me.deftware.client.framework.render.texture.GlTexture;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deftware
 */
public class ToastBuilder extends ChatBuilder {

	private final List<ChatMessage> text =  new ArrayList<>();
	private ChatMessage title;
	private GlTexture texture;

	public ToastBuilder appendTitle() {
		title = build();
		message = new ChatMessage();
		return this;
	}

	public ToastBuilder appendText() {
		text.add(build());
		message = new ChatMessage();
		return this;
	}

	public ToastBuilder withIcon(GlTexture texture) {
		this.texture = texture;
		return this;
	}

	public void display() {
		ToastAPI.addToast(new ToastImpl(texture, title, text.toArray(new ChatMessage[0])));
	}

}
