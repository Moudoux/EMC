package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import net.minecraft.network.Packet;

public class ICPacketCloseWindow extends IPacket {

	public ICPacketCloseWindow(Packet<?> packet) {
		super(packet);
	}

}
