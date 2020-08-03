package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Triggered when entity (including player) nametag is being rendered
 */
public class EventNametagRender extends Event {

    private @Getter final boolean isPlayer;
    private @Getter ChatMessage name;

    public EventNametagRender(Entity entity) {
        if (isPlayer = entity instanceof PlayerEntity) {
            name = new ChatMessage().fromText(entity.getName());
        }
    }

}
