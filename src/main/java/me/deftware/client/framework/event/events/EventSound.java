package me.deftware.client.framework.event.events;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;
import net.minecraft.client.sound.SoundInstance;

public class EventSound extends Event {
    private SoundInstance instance;
    private String translationVal;

    public EventSound(SoundInstance instance, String translationVal) {
        this.instance = instance;
    }

    public String getSoundId() {
        return instance.getId().toString();
    }

    public String getSoundName() {
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
