package me.deftware.client.framework.chat;

import me.deftware.client.framework.chat.style.ChatColors;

import java.util.Arrays;

/**
 * @author Deftware
 */
public class LiteralChatMessage extends ChatMessage {

	public static final LiteralChatMessage EMPTY = new LiteralChatMessage("");

	public LiteralChatMessage(String text) {
		this(text, ChatColors.WHITE);
	}

	public LiteralChatMessage(String text, ChatColors color) {
		ChatSection section = new ChatSection(text);
		section.getStyle().setColor(color.getChatColor());
		sectionList.add(section);
	}

	public static LiteralChatMessage formatted(String text, Object... params) {
		return new LiteralChatMessage(String.format(text, params));
	}

	public static LiteralChatMessage[] of(String... text) {
		return Arrays.stream(text)
				.map(s -> s == null || s.isEmpty() ? EMPTY : new LiteralChatMessage(s))
				.toArray(LiteralChatMessage[]::new);
	}

}
