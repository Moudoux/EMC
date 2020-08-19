package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;

/**
 * @author Deftware
 */
public class CPacketClientStatus extends PacketWrapper {

	public CPacketClientStatus(Packet<?> packet) {
		super(packet);
	}

}
