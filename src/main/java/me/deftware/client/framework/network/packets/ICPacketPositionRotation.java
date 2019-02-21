package me.deftware.client.framework.network.packets;


import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;

public class ICPacketPositionRotation extends IPacket {

	public ICPacketPositionRotation(Packet<?> packet) {
		super(packet);
	}

}
