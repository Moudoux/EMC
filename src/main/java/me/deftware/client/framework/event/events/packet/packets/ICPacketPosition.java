package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class ICPacketPosition extends IPacket {

	public ICPacketPosition(Packet<?> packet) {
		super(packet);
	}

	public ICPacketPosition(double xIn, double yIn, double zIn, boolean onGroundIn) {
		super(new CPacketPlayer.Position(xIn, yIn, zIn, onGroundIn));
	}

}
