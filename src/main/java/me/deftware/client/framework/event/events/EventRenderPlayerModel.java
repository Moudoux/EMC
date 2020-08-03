package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;
import net.minecraft.entity.Entity;

/**
 * Triggered when player model is being rendered.
 * It does not include the model drawn in players' inventory
 */
public class EventRenderPlayerModel extends Event {

	private @Getter @Setter boolean shouldRender = false;
	private final @Getter ChatMessage name;

	public EventRenderPlayerModel(Entity entity) {
		this.name = new ChatMessage().fromText(entity.getName());
	}


}
