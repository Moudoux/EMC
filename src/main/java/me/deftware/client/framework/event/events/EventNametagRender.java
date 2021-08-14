package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.ClientWorld;
import net.minecraft.entity.Entity;

/**
 * Triggered when entity (including player) nametag is being rendered
 */
public class EventNametagRender extends Event {

    private me.deftware.client.framework.entity.Entity entity;

    public EventNametagRender create(Entity entity) {
        setCanceled(false);
        this.entity = ClientWorld.getClientWorld().getEntityByReference(entity);
        return this;
    }

    public me.deftware.client.framework.entity.Entity getEntity() {
        return entity;
    }

}
