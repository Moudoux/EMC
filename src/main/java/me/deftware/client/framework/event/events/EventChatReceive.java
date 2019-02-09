package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.text.TextComponent;

/**
 * Triggered by Minecraft chat listener at the moment the message is drawn to screen
 */
public class EventChatReceive extends Event {

    private TextComponent itc;

    public EventChatReceive(TextComponent itc) {
        this.itc = itc;
    }

    public TextComponent getItc() {
        return itc;
    }

    public void setItc(TextComponent itc) {
        this.itc = itc;
    }

    public void setColorCodes() {
        itc = ChatProcessor.getTextComponent(
                ChatProcessor.convertColorCodes(itc.getFormattedText()));
    }

    public String getMessage() {
        return itc.getText();
    }

    public void replace(String original, String _new) {
        String message = itc.getFormattedText();
        itc = ChatProcessor.getTextComponent(message.replace(original, _new));
    }

}
