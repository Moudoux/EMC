package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.network.PacketWrapper;
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

    public void setPacket(PacketWrapper packet) {
        this.packet = packet.getPacket();
    }

    public PacketWrapper getIPacket() {
        return PacketWrapper.translatePacket(packet);
    }

}
