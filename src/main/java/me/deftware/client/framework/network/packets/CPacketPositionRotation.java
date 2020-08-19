package me.deftware.client.framework.network.packets;


import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/**
 * @author Deftware
 */
public class CPacketPositionRotation extends PacketWrapper {

	public CPacketPositionRotation(Packet<?> packet) {
		super(packet);
	}

	public CPacketPositionRotation(double x, double y, double z, float yaw, float pitch, boolean isOnGround) {
		super(new PlayerMoveC2SPacket.Both(x,y,z,yaw,pitch,isOnGround));
	}

}
