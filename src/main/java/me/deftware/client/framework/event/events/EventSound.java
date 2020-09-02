package me.deftware.client.framework.event.events;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;
import net.minecraft.client.sound.SoundInstance;

public class EventSound extends Event {
    private SoundInstance instance;
    private ChatMessage translationVal;

    public EventSound(SoundInstance instance, ChatMessage translationVal) {
        this.instance = instance;
    }

    public String getSoundId() {
        return instance.getId().toString();
    }

    public ChatMessage getSoundName() {
        return translationVal;
    }

    public double getX() {
        return instance.getX();
    }

    public double getY() {
        return instance.getY();
    }

    public double getZ() {
        return instance.getZ();
    }
}
