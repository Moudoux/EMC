package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;

/**
 * @author Deftware
 */
public class CPacketCloseWindow extends PacketWrapper {

	public CPacketCloseWindow(Packet<?> packet) {
		super(packet);
	}

}
