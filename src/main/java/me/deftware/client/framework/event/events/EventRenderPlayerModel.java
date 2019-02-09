package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import net.minecraft.entity.Entity;

/**
 * Triggered when player model is being rendered.
 * It does not include the model drawn in players' inventory
 */
public class EventRenderPlayerModel extends Event {

	private boolean shouldRender = false;
	private String name;

	public EventRenderPlayerModel(Entity entity) {
		this.name = entity.getName().getFormattedText();
	}

	public String getName() {
		return name;
	}

	public boolean isShouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}

}
