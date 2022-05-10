package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Triggered when entity (including player) nametag is being rendered
 */
public class EventNametagRender extends Event {

	private boolean isPlayer;
	private String name = "";

	public EventNametagRender(Entity entity) {
		isPlayer = entity instanceof EntityPlayer;
		if (isPlayer) {
			name = entity.getName().getFormattedText();
		}
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public String getName() {
		return name;
	}

}
