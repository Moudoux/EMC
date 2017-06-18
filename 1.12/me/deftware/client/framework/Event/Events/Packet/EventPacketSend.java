package me.deftware.client.framework.Event.Events.Packet;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Event.Events.Packet.Packets.ICPacketPlayer;
import me.deftware.client.framework.Event.Events.Packet.Packets.ICPacketPosition;
import me.deftware.client.framework.Event.Events.Packet.Packets.ICPacketPositionRotation;
import me.deftware.client.framework.Event.Events.Packet.Packets.ICPacketRotation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class EventPacketSend extends Event {

	private Packet<?> packet;

	public EventPacketSend(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}

	public void setPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public void setPacket(IPacket packet) {
		this.packet = packet.getPacket();
	}

	public IPacket getIPacket() {
		if (packet instanceof CPacketPlayer) {
			return new ICPacketPlayer(packet);
		} else if (packet instanceof CPacketPlayer.PositionRotation) {
			return new ICPacketPositionRotation(packet);
		} else if (packet instanceof CPacketPlayer.Rotation) {
			return new ICPacketRotation(packet);
		} else if (packet instanceof CPacketPlayer.Position) {
			return new ICPacketPosition(packet);
		}
		return new IPacket(packet);
	}

}
