package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EventNametagRender extends Event {

	private Entity entity;

	public EventNametagRender(Entity entity) {
		this.entity = entity;
	}

	public boolean isPlayer() {
		return entity instanceof EntityPlayer;
	}

	public String getName() {
		return entity.getName().getFormattedText();
	}

}
