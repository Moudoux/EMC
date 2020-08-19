package me.deftware.client.framework.event.events;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;

public class EventAttackEntity extends Event {

	private final Entity player, target;

	public EventAttackEntity(net.minecraft.entity.Entity player, net.minecraft.entity.Entity target) {
		this.player = Entity.newInstance(player);
		this.target = Entity.newInstance(target);
	}

	public Entity getPlayer() {
		return player;
	}

	public Entity getTarget() {
		return target;
	}

}
