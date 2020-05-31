package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.entity.Entity;

public class EventAttackEntity extends Event {

	private IEntity player, target;

	public EventAttackEntity(Entity player, Entity target) {
		this.player = new IEntity(player);
		this.target = new IEntity(target);
	}

	public IEntity getPlayer() {
		return player;
	}

	public IEntity getTarget() {
		return target;
	}

}
