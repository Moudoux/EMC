package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.World;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.Objects;

/**
 * Triggered when entity (including player) nametag is being rendered
 */
public class EventNametagRender extends Event {

    private me.deftware.client.framework.entity.Entity entity;

    public EventNametagRender create(Entity entity) {
        setCanceled(false);
        this.entity = World.getEntityById(entity.getId());
        return this;
    }

    public me.deftware.client.framework.entity.Entity getEntity() {
        return entity;
    }

}
