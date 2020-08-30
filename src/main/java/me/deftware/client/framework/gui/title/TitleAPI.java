package me.deftware.client.framework.gui.title;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.LiteralText;

/**
 * A wrapper for sending titles using the minecraft title system
 * (the big middle of screen things added in 1.8)
 *
 * @author Wagyourtail
 */
public class TitleAPI {
    public static void sendTitle(ChatMessage title, ChatMessage subtitle, int ticksFadeIn, int ticksVisible, int ticksFadeOut) {
        InGameHud igh = MinecraftClient.getInstance().inGameHud;
        if (igh != null) {
            //have to be done seperate because, minecraft...
            igh.setTitles(title.build(), null, ticksFadeIn, ticksVisible, ticksFadeOut);
            igh.setTitles(null, subtitle.build(), ticksFadeIn, ticksVisible, ticksFadeOut);
        }
    }
}
