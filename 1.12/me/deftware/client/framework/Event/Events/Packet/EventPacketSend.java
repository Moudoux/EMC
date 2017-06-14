package me.deftware.client.framework.Event.Events.Packet;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Event.Events.Packet.Packets.ICPacketPlayer;
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
		}
		return null;
	}

}
