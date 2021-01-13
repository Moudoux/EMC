package me.deftware.client.framework.network;

import me.deftware.client.framework.network.packets.*;
import me.deftware.mixin.imp.IMixinNetworkManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;

import java.io.IOException;

/**
 * Describes the packet structure with all of it's data
 *
 * @author Deftware
 */
public class PacketWrapper {

    protected Packet<?> packet;

    private PacketBuffer packetBuffer;

    public PacketWrapper(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public PacketBuffer getPacketBuffer() {
        return packetBuffer;
    }

    public void setPacketBuffer(PacketBuffer buffer) throws IOException {
        packet.write(buffer.buffer);
    }

    public void sendPacket() {
        MinecraftClient.getInstance().player.networkHandler.sendPacket(packet);
    }

    /**
     * Bypasses this event, and can be used to prevent an infinite loop
     */
    public void sendImmediately() {
        ((IMixinNetworkManager) MinecraftClient.getInstance().player.networkHandler.getConnection()).sendPacketImmediately(packet);
    }

    /**
     * Converts a packet into the EMC Packet wrapper
     */
    public static PacketWrapper translatePacket(Packet<?> packet) {
        // Client to server packets
        if (packet instanceof PlayerInteractEntityC2SPacket) {
            return new CPacketUseEntity(packet);
        } else if (packet instanceof PlayerMoveC2SPacket) {
            return new CPacketPlayer(packet);
        } else if (packet instanceof CloseScreenS2CPacket) { // TODO: Verify this
            return new CPacketCloseWindow(packet);
        } else if (packet instanceof KeepAliveC2SPacket) {
            return new CPacketKeepAlive(packet);
        } else if (packet instanceof ClientStatusC2SPacket) {
            return new CPacketClientStatus(packet);
        }
        // Server to client packets
        if (packet instanceof EntityS2CPacket) {
            return new SPacketEntity(packet);
        } else if (packet instanceof EntityAnimationS2CPacket) {
            return new SPacketAnimation(packet);
        }
        return new PacketWrapper(packet);
    }

}
