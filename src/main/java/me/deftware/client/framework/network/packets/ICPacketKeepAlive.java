package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;

public class ICPacketKeepAlive extends IPacket {

	public ICPacketKeepAlive(Packet<?> packet) {
		super(packet);
	}

}
