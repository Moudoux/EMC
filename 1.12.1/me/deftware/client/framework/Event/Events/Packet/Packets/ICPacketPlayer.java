package me.deftware.client.framework.Event.Events.Packet.Packets;

import me.deftware.client.framework.Event.Events.Packet.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class ICPacketPlayer extends IPacket {

	public ICPacketPlayer(Packet<?> packet) {
		super(packet);
	}

	public ICPacketPlayer() {
		super(new CPacketPlayer());
	}

	public void setOnGround(boolean state) {
		((CPacketPlayer) getPacket()).onGround = state;
	}

	public void setMoving(boolean state) {
		((CPacketPlayer) getPacket()).moving = state;
	}

}
