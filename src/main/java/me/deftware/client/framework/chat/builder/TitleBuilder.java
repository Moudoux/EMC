package me.deftware.client.framework.chat.builder;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.title.TitleAPI;

/**
 * @author Deftware
 */
public class TitleBuilder extends ChatBuilder {

	private ChatMessage title, subtitle;

	public TitleBuilder appendTitle() {
		title = build();
		message = new ChatMessage();
		return this;
	}

	public TitleBuilder appendSubtitle() {
		subtitle = build();
		message = new ChatMessage();
		return this;
	}

	public void display(int ticksFadeIn, int ticksVisible, int ticksFadeOut) {
		TitleAPI.sendTitle(title, subtitle, ticksFadeIn, ticksVisible, ticksFadeOut);
	}

}
