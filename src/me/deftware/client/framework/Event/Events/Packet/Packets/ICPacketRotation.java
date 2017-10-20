package me.deftware.client.framework.Event.Events.Packet.Packets;

import me.deftware.client.framework.Event.Events.Packet.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class ICPacketRotation extends IPacket {

	public ICPacketRotation(Packet<?> packet) {
		super(packet);
	}

	public ICPacketRotation(float yaw, float pitch, boolean onGround) {
		super(new CPacketPlayer.Rotation(yaw, pitch, onGround));
	}

}
