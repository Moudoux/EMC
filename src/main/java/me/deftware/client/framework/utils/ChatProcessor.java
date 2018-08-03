package me.deftware.client.framework.utils;

import java.util.ArrayList;
import java.util.regex.Pattern;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.mixin.imp.IMixinGuiNewChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class ChatProcessor {

	private static ArrayList<String> history = new ArrayList<>();

	private static final Pattern URL_PATTERN = Pattern
			.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	public static String convertColorCodes(String message) {

		message = message.replace("&4", "" + ChatColor.DARK_RED);
		message = message.replace("&c", "" + ChatColor.RED);
		message = message.replace("&6", "" + ChatColor.GOLD);
		message = message.replace("&e", "" + ChatColor.YELLOW);
		message = message.replace("&2", "" + ChatColor.DARK_GREEN);
		message = message.replace("&a", "" + ChatColor.GREEN);
		message = message.replace("&b", "" + ChatColor.AQUA);
		message = message.replace("&3", "" + ChatColor.DARK_AQUA);
		message = message.replace("&1", "" + ChatColor.DARK_BLUE);
		message = message.replace("&9", "" + ChatColor.BLUE);
		message = message.replace("&d", "" + ChatColor.LIGHT_PURPLE);
		message = message.replace("&5", "" + ChatColor.DARK_PURPLE);
		message = message.replace("&f", "" + ChatColor.WHITE);

		message = message.replace("&7", "" + ChatColor.GRAY);
		message = message.replace("&8", "" + ChatColor.DARK_GRAY);
		message = message.replace("&0", "" + ChatColor.BLACK);

		message = message.replace("&l", "" + ChatColor.BOLD);
		message = message.replace("&n", "" + ChatColor.UNDERLINE);
		message = message.replace("&o", "" + ChatColor.ITALIC);
		message = message.replace("&k", "" + ChatColor.MAGIC);

		message = message.replace("&m", "" + ChatColor.STRIKETHROUGH);
		message = message.replace("&r", "" + ChatColor.RESET);

		return message;
	}

	public static ITextComponent getITextComponent(String chatMessage) {
		ITextComponent textComponent = new net.minecraft.util.text.TextComponentString("");
		String[] messageParts = chatMessage.split(" ");
		int pathIndex = 0;

		for (String messagePart : messageParts) {
			ITextComponent append = new net.minecraft.util.text.TextComponentString(messagePart);
			Style chatStyle = new Style();

			if (ChatProcessor.URL_PATTERN.matcher(ChatColor.stripColor(messagePart)).matches()) {
				chatStyle.setUnderlined(Boolean.valueOf(true));
				chatStyle.setClickEvent(new net.minecraft.util.text.event.ClickEvent(
						net.minecraft.util.text.event.ClickEvent.Action.OPEN_URL, ChatColor.stripColor(messagePart)));
			}

			String currentPath = chatMessage.substring(0, chatMessage.indexOf(messagePart, pathIndex));
			String lastColor = ChatColor.getLastColors(currentPath);

			if (lastColor.length() >= 2) {
				char formattingChar = lastColor.charAt(1);
				ChatProcessor.formatChatStyle(chatStyle, formattingChar);
			}

			append.setStyle(chatStyle);
			textComponent.appendSibling(append);
			textComponent.appendText(" ");
			pathIndex += messagePart.length() - 1;
		}

		return textComponent;
	}

	public static void sendMessages() {
		if (!history.isEmpty()) {
			for (String message : history) {
				printChatMessage(message, true);
			}
			history.clear();
		}
	}

	public static void printChatMessage(String chatMessage, boolean send) {
		if (!send){
			history.add(chatMessage);
			return;
		}
		ITextComponent textComponent = getITextComponent(chatMessage);
		((IMixinGuiNewChat) Minecraft.getMinecraft().ingameGUI.getChatGUI()).setTheChatLine(textComponent, 0,
				Minecraft.getMinecraft().ingameGUI.getUpdateCounter(), false);
	}

	public static void printClientMessage(String chatMessage) {
		ChatProcessor.printChatMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString()
				+ FrameworkConstants.FRAMEWORK_NAME + " " + ChatColor.RESET.toString()
				+ ChatColor.GRAY.toString() + "> " + ChatColor.GRAY + chatMessage, false);
	}

	public static void printFrameworkMessage(String msg) {
		ChatProcessor.printChatMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString()
				+ FrameworkConstants.FRAMEWORK_NAME + " " + ChatColor.RESET.toString()
				+ ChatColor.GRAY.toString() + "> " + ChatColor.GRAY + msg, false);
	}

	public static void printClientMessage(String chatMessage, boolean prefix) {
		if (!prefix) {
			ChatProcessor.printChatMessage(chatMessage, false);
		} else {
			ChatProcessor.printClientMessage(chatMessage);
		}
	}

	/*
	private static TextFormatting getTextFormattingByValue(char value) {
		int index = 0;
		for (ChatColor color : ChatColor.values()) {
			if (color.code == value) {
				index = color.intCode;
				break;
			}
		}
		for (TextFormatting textFormatting : TextFormatting.values()) {
			if (textFormatting.getColorIndex() == index) {
				return textFormatting;
			}
		}
		return null;
	}
	*/

	private static Style formatChatStyle(Style chatStyle, char formattingChar) {
		switch (formattingChar) {
		case 'k':
			chatStyle.setObfuscated(Boolean.valueOf(true));
			break;
		case 'm':
			chatStyle.setStrikethrough(Boolean.valueOf(true));
			break;
		case 'l':
			chatStyle.setBold(Boolean.valueOf(true));
			break;
		case 'n':
			chatStyle.setUnderlined(Boolean.valueOf(true));
			break;
		case 'o':
			chatStyle.setItalic(Boolean.valueOf(true));
			break;
		case 'r':
			chatStyle.setObfuscated(Boolean.valueOf(false));
			chatStyle.setStrikethrough(Boolean.valueOf(false));
			chatStyle.setBold(Boolean.valueOf(false));
			chatStyle.setUnderlined(Boolean.valueOf(false));
			chatStyle.setItalic(Boolean.valueOf(false));
			//chatStyle.setColor(TextFormatting.RESET);
			break;
		case 'p':
		case 'q':
		default:
			//chatStyle.setColor(ChatProcessor.getTextFormattingByValue(formattingChar));
		}

		return chatStyle;
	}

}
