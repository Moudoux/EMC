package me.deftware.client.framework.utils;

import com.google.common.collect.EvictingQueue;
import me.deftware.mixin.imp.IMixinGuiNewChat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * This class has been deprecated, and a new method of managing internal Minecraft strings will be added later
 */
@Deprecated
public class ChatProcessor {

    private static final Pattern URL_PATTERN = Pattern
            .compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static Queue<String> history = EvictingQueue.create(256);

    private static final Lock historyLock = new ReentrantLock();

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

    /**
     * Internal use only! >1.16 Only!
     * Does not support events either
     */
    public static String getStringFromText(Text text) {
        StringBuilder result = new StringBuilder();
        text.visit((style, string) -> {
            if (!string.trim().isEmpty()) { // Make sure its not a new line
                // Formatting styles
                if (style.isBold()) {
                    string = Formatting.BOLD.toString() + string;
                }
                if (style.isUnderlined()) {
                    string = Formatting.UNDERLINE.toString() + string;
                }
                if (style.isItalic()) {
                    string = Formatting.ITALIC.toString() + string;
                }
                if (style.isObfuscated()) {
                    string = Formatting.OBFUSCATED.toString() + string;
                }
                if (style.isStrikethrough()) {
                    string = Formatting.STRIKETHROUGH.toString() + string;
                }
                // Color; Only supports the old style Minecraft color, no hex or rgb values are support
                if (style.getColor() != null && style.getColor().getName() != null) {
                    for (Formatting formatting : Formatting.values()) {
                        if (formatting.isColor() && formatting.getName().equalsIgnoreCase(style.getColor().getName())) {
                            string = formatting.toString() + string;
                            break;
                        }
                    }
                }
            }
            // Append string
            result.append(string);
            return Optional.empty();
        }, text.getStyle());
        return result.toString();
    }

    public static LiteralText getLiteralText(String chatMessage) {
        LiteralText text = new LiteralText("");
        if (chatMessage != null) {
            String[] messageParts = chatMessage.split(" ");
            int pathIndex = 0;
            for (String messagePart : messageParts) {
                LiteralText append = new LiteralText(messagePart);
                Style chatStyle = Style.EMPTY;
                if (ChatProcessor.URL_PATTERN.matcher(ChatColor.stripColor(messagePart)).matches()) {
                    chatStyle = chatStyle.withFormatting(Formatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ChatColor.stripColor(messagePart)));
                }
                String currentPath = chatMessage.substring(0, chatMessage.indexOf(messagePart, pathIndex));
                String lastColor = ChatColor.getLastColors(currentPath);
                if (lastColor.length() >= 2) {
                    char formattingChar = lastColor.charAt(1);
                    chatStyle = ChatProcessor.formatChatStyle(chatStyle, formattingChar);
                }
                append.setStyle(chatStyle);
                text.append(append);
                text.append(new LiteralText( " "));
                pathIndex += messagePart.length() - 1;
            }
        }
        return text;
    }

    public static void sendMessages() {
        historyLock.lock();
        ArrayList<String> listCopy;

        try {
            if (!history.isEmpty()) {
                listCopy = new ArrayList<>(history);
                for (String message : listCopy) {
                    printChatMessage(message, true);
                }
                history.clear();
            }
        } finally {
            historyLock.unlock();
        }
    }

    public static void printChatMessage(String chatMessage, boolean send) {
        if (!send) {
            history.add(chatMessage);
            return;
        }
        LiteralText LiteralText = getLiteralText(chatMessage);
        ((IMixinGuiNewChat) MinecraftClient.getInstance().inGameHud.getChatHud()).setTheChatLine(LiteralText, 0,
                MinecraftClient.getInstance().inGameHud.getTicks(), false);
    }

    public static void printClientMessage(String chatMessage) {
        ChatProcessor.printChatMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString()
                + "EMC " + ChatColor.RESET.toString()
                + ChatColor.GRAY.toString() + "> " + ChatColor.GRAY + chatMessage, false);
    }

    public static void printFrameworkMessage(String msg) {
        ChatProcessor.printChatMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString()
                + "EMC " + ChatColor.RESET.toString()
                + ChatColor.GRAY.toString() + "> " + ChatColor.GRAY + msg, false);
    }

    public static void printClientMessage(String chatMessage, boolean prefix) {
        if (!prefix) {
            ChatProcessor.printChatMessage(chatMessage, false);
        } else {
            ChatProcessor.printClientMessage(chatMessage);
        }
    }

    private static Formatting getTextFormattingByValue(char value) {
        int index = 0;
        for (ChatColor color : ChatColor.values()) {
            if (color.code == value) {
                index = color.intCode;
                break;
            }
        }
        for (Formatting textFormatting : Formatting.values()) {
            if (textFormatting.getColorIndex() == index) {
                return textFormatting;
            }
        }
        return null;
    }

    private static Style formatChatStyle(Style chatStyle, char formattingChar) {
        switch (formattingChar) {
            case 'k':
                chatStyle = chatStyle.withFormatting(Formatting.OBFUSCATED);
                break;
            case 'm':
                chatStyle = chatStyle.withFormatting(Formatting.STRIKETHROUGH);
                break;
            case 'l':
                chatStyle = chatStyle.withFormatting(Formatting.BOLD);
                break;
            case 'n':
                chatStyle = chatStyle.withFormatting(Formatting.UNDERLINE);
                break;
            case 'o':
                chatStyle = chatStyle.withFormatting(Formatting.ITALIC);
                break;
            case 'r':
                chatStyle = Style.EMPTY;
                break;
            case 'p':
            case 'q':
            default:
                chatStyle = chatStyle.withColor(ChatProcessor.getTextFormattingByValue(formattingChar));
        }
        return chatStyle;
    }

}
