package me.deftware.client.framework.utils;

import com.google.common.collect.EvictingQueue;
import me.deftware.mixin.imp.IMixinGuiNewChat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

@SuppressWarnings("all")
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

    public static LiteralText getLiteralText(String chatMessage) {
        LiteralText LiteralText = new LiteralText("");
        if (chatMessage != null) {
            String[] messageParts = chatMessage.split(" ");
            int pathIndex = 0;

            for (String messagePart : messageParts) {
                LiteralText append = new LiteralText(messagePart);
                Style chatStyle = new Style();

                if (ChatProcessor.URL_PATTERN.matcher(ChatColor.stripColor(messagePart)).matches()) {
                    chatStyle.setUnderline(true);
                    chatStyle.setClickEvent(new ClickEvent(
                            ClickEvent.Action.OPEN_URL, ChatColor.stripColor(messagePart)));
                }

                String currentPath = chatMessage.substring(0, chatMessage.indexOf(messagePart, pathIndex));
                String lastColor = ChatColor.getLastColors(currentPath);

                if (lastColor.length() >= 2) {
                    char formattingChar = lastColor.charAt(1);
                    ChatProcessor.formatChatStyle(chatStyle, formattingChar);
                }

                append.setStyle(chatStyle);
                LiteralText.append(append);
                LiteralText.append(" ");
                pathIndex += messagePart.length() - 1;
            }
        }

        return LiteralText;
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
                chatStyle.setObfuscated(true);
                break;
            case 'm':
                chatStyle.setStrikethrough(true);
                break;
            case 'l':
                chatStyle.setBold(true);
                break;
            case 'n':
                chatStyle.setUnderline(true);
                break;
            case 'o':
                chatStyle.setItalic(true);
                break;
            case 'r':
                chatStyle.setObfuscated(false);
                chatStyle.setStrikethrough(false);
                chatStyle.setBold(false);
                chatStyle.setUnderline(false);
                chatStyle.setItalic(false);
                chatStyle.setColor(ChatProcessor.getTextFormattingByValue('r'));
                break;
            case 'p':
            case 'q':
            default:
                chatStyle.setColor(ChatProcessor.getTextFormattingByValue(formattingChar));
        }

        return chatStyle;
    }

}
