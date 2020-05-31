package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;

public class ICPacketClientStatus extends IPacket {

	public ICPacketClientStatus(Packet<?> packet) {
		super(packet);
	}

}
