package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Triggered when entity (including player) nametag is being rendered
 */
public class EventNametagRender extends Event {

    private boolean isPlayer;
    private String name = "";

    public EventNametagRender(Entity entity) {
        isPlayer = entity instanceof PlayerEntity;
        if (isPlayer) {
            name = ChatProcessor.getStringFromText(entity.getName());
        }
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public String getName() {
        return name;
    }

}
