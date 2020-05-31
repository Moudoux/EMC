package me.deftware.client.framework.event.events;

import net.minecraft.network.Packet;

/**
 * Triggered when packet is sent from the server to the client
 */
public class EventPacketReceive extends EventPacketSend {

	public EventPacketReceive(Packet<?> packet) {
		super(packet);
	}

}
