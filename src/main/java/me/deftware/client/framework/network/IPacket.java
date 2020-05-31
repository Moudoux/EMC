package me.deftware.client.framework.network;

import me.deftware.client.framework.network.packets.*;
import me.deftware.mixin.imp.IMixinNetworkManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.EntityAnimationS2CPacket;
import net.minecraft.client.network.packet.EntityS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.ClientStatusC2SPacket;
import net.minecraft.server.network.packet.GuiCloseC2SPacket;
import net.minecraft.server.network.packet.KeepAliveC2SPacket;
import net.minecraft.server.network.packet.PlayerMoveC2SPacket;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Describes the packet structure with all of it's data
 */
@SuppressWarnings("ConstantConditions")
public class IPacket {

    protected Packet<?> packet;

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

    /**
     * Bypasses this event, and can be used to prevent an infinite loop
     */
    public void sendImmediately() {
        ((IMixinNetworkManager) MinecraftClient.getInstance().player.networkHandler.getConnection()).sendPacketImmediately(packet);
    }

    /**
     * Converts a packet into the EMC IPacket wrapper
     */
    @Nullable
    public static IPacket translatePacket(Packet<?> packet) {
        // Client to server packets
        if (packet instanceof PlayerMoveC2SPacket) {
            return new ICPacketPlayer(packet);
        } else if (packet instanceof PlayerMoveC2SPacket.Both) {
            return new ICPacketPositionRotation(packet);
        } else if (packet instanceof PlayerMoveC2SPacket.LookOnly) {
            return new ICPacketRotation(packet);
        } else if (packet instanceof PlayerMoveC2SPacket.PositionOnly) {
            return new ICPacketPosition(packet);
        } else if (packet instanceof GuiCloseC2SPacket) {
            return new ICPacketCloseWindow(packet);
        } else if (packet instanceof KeepAliveC2SPacket) {
            return new ICPacketKeepAlive(packet);
        } else if (packet instanceof ClientStatusC2SPacket) {
            return new ICPacketClientStatus(packet);
        }
        // Server to client packets
        if (packet instanceof EntityS2CPacket) {
            return new ISPacketEntity(packet);
        } else if (packet instanceof EntityAnimationS2CPacket) {
            return new ISPacketAnimation(packet);
        }
        return new IPacket(packet);
    }


}
