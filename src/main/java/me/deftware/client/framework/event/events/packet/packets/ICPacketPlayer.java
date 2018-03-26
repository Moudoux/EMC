package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import me.deftware.mixin.imp.IMixinCPacketPlayer;
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
		((IMixinCPacketPlayer) getPacket()).setOnGround(state);
	}

	public void setMoving(boolean state) {
		((IMixinCPacketPlayer) getPacket()).setMoving(state);
	}

}
