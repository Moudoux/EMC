package me.deftware.client.framework.event.events.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class IPacket {

	private Packet<?> packet;

	public IPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}


	public void sendPacket() {
		Minecraft.getMinecraft().player.connection.sendPacket(packet);
	}

}
