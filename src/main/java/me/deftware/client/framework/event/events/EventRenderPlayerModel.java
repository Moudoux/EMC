package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.ClientWorld;
import me.deftware.client.framework.world.World;
import net.minecraft.entity.Entity;

/**
 * Triggered when player model is being rendered.
 * It does not include the model drawn in players' inventory
 */
@Setter
@Getter
public class EventRenderPlayerModel extends Event {

	private me.deftware.client.framework.entity.Entity entity;
	private boolean shouldRender = false;

	public EventRenderPlayerModel create(Entity entity) {
		this.shouldRender = false;
		this.entity = ClientWorld.getClientWorld().getEntityByReference(entity);
		return this;
	}

}
