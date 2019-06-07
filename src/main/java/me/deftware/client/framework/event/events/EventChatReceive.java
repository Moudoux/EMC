package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.text.Text;

/**
 * Triggered by Minecraft chat listener at the moment the message is drawn to screen
 */
public class EventChatReceive extends Event {

    private Text itc;

    public EventChatReceive(Text itc) {
        this.itc = itc;
    }

    public Text getItc() {
        return itc;
    }

    public void setItc(Text itc) {
        this.itc = itc;
    }

    public void setColorCodes() {
        itc = ChatProcessor.getLiteralText(
                ChatProcessor.convertColorCodes(itc.asFormattedString()));
    }

    public String getMessage() {
        return itc.getString();
    }

    public void replace(String original, String _new) {
        String message = itc.asFormattedString();
        itc = ChatProcessor.getLiteralText(message.replace(original, _new));
    }

}
