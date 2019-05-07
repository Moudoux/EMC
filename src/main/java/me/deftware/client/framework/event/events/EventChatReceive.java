package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.network.chat.Component;

/**
 * Triggered by Minecraft chat listener at the moment the message is drawn to screen
 */
public class EventChatReceive extends Event {

    private Component itc;

    public EventChatReceive(Component itc) {
        this.itc = itc;
    }

    public Component getItc() {
        return itc;
    }

    public void setItc(Component itc) {
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
