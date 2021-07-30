package me.deftware.client.framework.event.events;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.World;

public class EventAttackEntity extends Event {

	private final Entity player, target;

	public EventAttackEntity(net.minecraft.entity.Entity player, net.minecraft.entity.Entity target) {
		this.player = World.getEntityById(player.getId());
		this.target = World.getEntityById(target.getId());
	}

	public Entity getPlayer() {
		return player;
	}

	public Entity getTarget() {
		return target;
	}

}
