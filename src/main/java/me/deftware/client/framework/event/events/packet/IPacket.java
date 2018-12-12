package me.deftware.client.framework.event.events.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

/**
 * Describes the packet structure with all of it's data
 */
public class IPacket {

	private Packet<?> packet;

	public IPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}


	public void sendPacket() {
		Minecraft.getInstance().player.connection.sendPacket(packet);
	}

}
