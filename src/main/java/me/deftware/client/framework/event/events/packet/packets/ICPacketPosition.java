package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.PlayerMoveServerMessage;

public class ICPacketPosition extends IPacket {

    public ICPacketPosition(Packet<?> packet) {
        super(packet);
    }

    public ICPacketPosition(double xIn, double yIn, double zIn, boolean onGroundIn) {
        super(new PlayerMoveServerMessage.PositionOnly(xIn, yIn, zIn, onGroundIn));
    }

}
