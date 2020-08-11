package me.deftware.client.framework.chat.style;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.event.ChatClickEvent;
import me.deftware.client.framework.chat.event.ChatHoverEvent;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.regex.Pattern;

/**
 * @author Deftware
 */
public class ChatStyle {

	private static @Getter final char formattingChar = 167;

	private @Getter @Setter ChatColors.ChatColor color;
	private @Getter @Setter boolean bold, underline, italic, obfuscated, strikethrough;
	private @Getter @Setter ChatClickEvent clickEvent;
	private @Getter @Setter ChatHoverEvent hoverEvent;

	public void fromCode(String code) {
		if (code.equalsIgnoreCase("r")) { // Reset
			color = null;
			bold = underline = italic = obfuscated = strikethrough = false;
			return;
		}
		for (Formatting formatting : Formatting.values()) {
			if (formatting.toString().substring(1).equalsIgnoreCase(code)) {
				// Formatting
				if (formatting == Formatting.BOLD) bold = true;
				else if (formatting == Formatting.UNDERLINE) underline = true;
				else if (formatting == Formatting.ITALIC) italic = true;
				else if (formatting == Formatting.OBFUSCATED) obfuscated = true;
				else if (formatting == Formatting.STRIKETHROUGH) strikethrough = true;
				// Color
				else color = new ChatColors.ChatColor(null, formatting);
				break;
			}
		}
	}

	public void fromStyle(Style style) {
		// Color
		color = new ChatColors.ChatColor(style.getColor(), null);
		// Formatting
		bold = style.isBold();
		underline = style.isUnderlined();
		italic = style.isItalic();
		obfuscated = style.isObfuscated();
		strikethrough = style.isStrikethrough();
		// Other
		if (style.getClickEvent() != null) clickEvent = ChatClickEvent.fromEvent(style.getClickEvent());
		if (style.getHoverEvent() != null) hoverEvent = ChatHoverEvent.fromEvent(style.getHoverEvent());
	}

	public Style getStyle() {
		Style style = Style.EMPTY;
		// Formatting
		style = style.withBold(bold);
		style = style.withItalic(italic);
		if (underline) style = style.withFormatting(Formatting.UNDERLINE);
		if (obfuscated) style = style.withFormatting(Formatting.OBFUSCATED);
		if (strikethrough) style = style.withFormatting(Formatting.STRIKETHROUGH);
		// Color
		if (color != null) style = color.applyToStyle(style);
		// Other
		if (clickEvent != null) style = style.withClickEvent(clickEvent.toEvent());
		if (hoverEvent != null) style = style.withHoverEvent(hoverEvent.toEvent());
		return style;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// Color
		if (color != null) builder.append(color.toString());
		// Formatting
		if (bold) builder.append(Formatting.BOLD.toString());
		if (underline) builder.append(Formatting.UNDERLINE.toString());
		if (italic) builder.append(Formatting.ITALIC.toString());
		if (obfuscated) builder.append(Formatting.OBFUSCATED.toString());
		if (strikethrough) builder.append(Formatting.STRIKETHROUGH.toString());
		return builder.toString();
	}

	public ChatStyle deepCopy() {
		ChatStyle copy = new ChatStyle();
		copy.color = color.deepCopy();
		copy.bold = bold;
		copy.italic = italic;
		copy.obfuscated = obfuscated;
		copy.strikethrough = strikethrough;
		copy.clickEvent = clickEvent;
		copy.hoverEvent = hoverEvent;
		return copy;
	}

	public static String stripColors(String input) {
		return stripColors(input, ChatStyle.getFormattingChar());
	}

	public static String stripColors(String input, char formattingChar) {
		return Pattern.compile("(?i)" + formattingChar + "[0-9A-FK-OR]").matcher(input).replaceAll("");
	}

}
