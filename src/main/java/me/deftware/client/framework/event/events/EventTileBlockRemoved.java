package me.deftware.client.framework.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.deftware.client.framework.entity.block.TileEntity;
import me.deftware.client.framework.event.Event;

public @AllArgsConstructor class EventTileBlockRemoved extends Event {

	private final @Getter TileEntity blockEntity;

}
