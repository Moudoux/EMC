package me.deftware.client.framework.chat;

import lombok.Getter;
import me.deftware.client.framework.chat.event.ChatClickEvent;
import me.deftware.client.framework.chat.event.ChatHoverEvent;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.chat.style.ChatStyle;
import net.minecraft.util.Language;

/**
 * @author Deftware
 */
public class ChatBuilder {

	private @Getter static final char chevron = 187;

	private final ChatMessage message = new ChatMessage();
	private @Getter ChatSection currentSection = new ChatSection("");

	/*
		Functions
	 */

	public ChatBuilder append() {
		message.getSectionList().add(currentSection);
		currentSection = new ChatSection("");
		return this;
	}

	public ChatMessage build() {
		append();
		return message;
	}

	public ChatBuilder withSection(ChatSection section) {
		message.getSectionList().add(section);
		return this;
	}

	public ChatBuilder withMessage(ChatMessage message) {
		this.message.join(message);
		return this;
	}

	public ChatBuilder withPrefix() {
		message.join(new ChatBuilder().withText("EMC").withColor(ChatColors.AQUA).setBold().append().withSpace()
				.withChar(chevron).withColor(ChatColors.GRAY).append().withSpace().build());
		return this;
	}

	/*
		Text
	 */

	public ChatBuilder withCodeFormattedString(String text, char formattingChar) {
		message.join(new ChatMessage().fromString(text, formattingChar));
		return this;
	}

	public ChatBuilder withText(String text) {
		currentSection.setText(currentSection.getText() + text);
		return this;
	}

	public ChatBuilder withChar(char _char) {
		currentSection.setText(new String(new char[]{ _char }));
		return this;
	}

	public ChatBuilder withMinecraftTranslatedText(String key) {
		currentSection.setText(Language.getInstance().get(key));
		return this;
	}

	public ChatBuilder withSpace() {
		currentSection = new ChatSection(" ");
		return append();
	}

	public ChatBuilder withLink(String link) {
		currentSection.setText(link);
		currentSection.getStyle().setUnderline(true);
		currentSection.getStyle().setClickEvent(new ChatClickEvent(ChatClickEvent.EventType.OPEN_URL, link));
		return this;
	}

	public ChatBuilder withHover(String text) {
		return withHover(new LiteralChatMessage(text));
	}

	public ChatBuilder withHover(ChatMessage text) {
		currentSection.getStyle().setHoverEvent(new ChatHoverEvent(ChatHoverEvent.EventType.SHOW_TEXT, text));
		return this;
	}

	/*
		Color
	 */

	public ChatBuilder withColor(ChatColors color) {
		return withColor(color.getChatColor());
	}

	public ChatBuilder withColor(ChatColors.ChatColor color) {
		currentSection.getStyle().setColor(color);
		return this;
	}

	public ChatBuilder replaceEmptyColor(ChatColors color) {
		return replaceEmptyColor(color.getChatColor());
	}

	public ChatBuilder replaceEmptyColor(ChatColors.ChatColor color) {
		for (ChatSection section : message.getSectionList()) {
			if (section.getStyle().getColor() == null) {
				section.getStyle().setColor(color);
			}
		}
		return this;
	}

	/*
		Style
	 */

	public ChatBuilder withStyle(ChatStyle style) {
		currentSection.setStyle(style);
		return this;
	}

	public ChatBuilder setBold() {
		currentSection.getStyle().setBold(true);
		return this;
	}

	public ChatBuilder setUnderlined() {
		currentSection.getStyle().setUnderline(true);
		return this;
	}

	public ChatBuilder setItalic() {
		currentSection.getStyle().setItalic(true);
		return this;
	}

	public ChatBuilder setObfuscated() {
		currentSection.getStyle().setObfuscated(true);
		return this;
	}

	public ChatBuilder setStrikethrough() {
		currentSection.getStyle().setStrikethrough(true);
		return this;
	}

	/*
		Event
	 */

	public ChatBuilder withClickEvent(ChatClickEvent event) {
		currentSection.getStyle().setClickEvent(event);
		return this;
	}

	public ChatBuilder withHoverEvent(ChatHoverEvent event) {
		currentSection.getStyle().setHoverEvent(event);
		return this;
	}

}
