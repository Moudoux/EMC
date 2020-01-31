package me.deftware.client.framework.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;

import java.io.IOException;

/**
 * Describes the packet structure with all of it's data
 */
public class IPacket {

    private Packet<?> packet;

    private IPacketBuffer packetBuffer;

    public IPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public IPacketBuffer getPacketBuffer() {
        return packetBuffer;
    }

    public void setPacketBuffer(IPacketBuffer buffer) throws IOException {
        packet.write(buffer.buffer);
    }

    public void sendPacket() {
        MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);
    }

}
