package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Text;

/**
 * Triggered by Minecraft chat listener at the moment the message is drawn to screen
 */
public class EventChatReceive extends Event {

    private Text itc;

    public EventChatReceive(Text itc) {
        this.itc = itc;
    }

    public EventChatReceive(StringRenderable itc) {
        this.itc = (Text) itc;
    }

    public Text getItc() {
        return itc;
    }

    public void setItc(Text itc) {
        this.itc = itc;
    }

    public void setColorCodes() {
        itc = ChatProcessor.getLiteralText(
                ChatProcessor.convertColorCodes(ChatProcessor.getStringFromText(itc)));
    }

    public String getMessage() {
        return ChatProcessor.getStringFromText(itc);
    }

    public void replace(String original, String _new) {
        String message = ChatProcessor.getStringFromText(itc);
        itc = ChatProcessor.getLiteralText(message.replace(original, _new));
    }

}
