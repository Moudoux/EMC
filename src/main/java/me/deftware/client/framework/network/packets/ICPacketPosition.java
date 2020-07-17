package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class ICPacketPosition extends IPacket {

    public ICPacketPosition(Packet<?> packet) {
        super(packet);
    }

    public ICPacketPosition(double xIn, double yIn, double zIn, boolean onGroundIn) {
        super(new PlayerMoveC2SPacket.PositionOnly(xIn, yIn, zIn, onGroundIn));
    }

}
