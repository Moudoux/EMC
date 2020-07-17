package me.deftware.client.framework.network.packets;


import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class ICPacketPositionRotation extends IPacket {

	public ICPacketPositionRotation(Packet<?> packet) {
		super(packet);
	}

	public ICPacketPositionRotation(double x, double y, double z, float yaw, float pitch, boolean isOnGround) {
		super(new PlayerMoveC2SPacket.Both(x,y,z,yaw,pitch,isOnGround));
	}

}
