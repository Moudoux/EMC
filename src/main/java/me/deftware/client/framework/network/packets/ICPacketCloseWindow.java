package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;

public class ICPacketCloseWindow extends IPacket {

	public ICPacketCloseWindow(Packet<?> packet) {
		super(packet);
	}

}
