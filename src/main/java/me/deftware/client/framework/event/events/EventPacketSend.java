package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;

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
        return IPacket.translatePacket(packet);
    }

}
