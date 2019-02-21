package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.network.packets.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Triggered when packet is being sent to the server
 */

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
		} else if (packet instanceof CPacketCloseWindow) {
			return new ICPacketCloseWindow(packet);
		}
		return new IPacket(packet);
	}

}
