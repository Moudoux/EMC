package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.PlayerMoveServerMessage;

public class ICPacketRotation extends IPacket {

    public ICPacketRotation(Packet<?> packet) {
        super(packet);
    }

    public ICPacketRotation(float yaw, float pitch, boolean onGround) {
        super(new PlayerMoveServerMessage.LookOnly(yaw, pitch, onGround));
    }

}
