package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.network.packets.*;
import net.minecraft.client.network.packet.EntityS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.ClientStatusC2SPacket;
import net.minecraft.server.network.packet.GuiCloseC2SPacket;
import net.minecraft.server.network.packet.KeepAliveC2SPacket;
import net.minecraft.server.network.packet.PlayerMoveC2SPacket;

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
