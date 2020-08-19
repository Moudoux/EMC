package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;

/**
 * @author Deftware
 */
public class CPacketKeepAlive extends PacketWrapper {

	public CPacketKeepAlive(Packet<?> packet) {
		super(packet);
	}

}
