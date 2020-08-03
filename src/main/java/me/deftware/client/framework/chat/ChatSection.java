package me.deftware.client.framework.chat;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.style.ChatStyle;

/**
 * @author Deftware
 */
public class ChatSection {

	private @Getter @Setter ChatStyle style = new ChatStyle();
	private @Getter @Setter String text;

	public ChatSection(String text) {
		this.text = text;
	}

	public ChatSection reset() {
		style = new ChatStyle();
		text = "";
		return this;
	}

}
