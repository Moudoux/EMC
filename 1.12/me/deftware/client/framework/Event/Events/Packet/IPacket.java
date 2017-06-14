package me.deftware.client.framework.Event.Events.Packet;

import net.minecraft.network.Packet;

public class IPacket {

	private Packet<?> packet;

	public IPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}

}
