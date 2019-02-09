package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import me.deftware.mixin.imp.IMixinCPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.PlayerMoveServerMessage;

public class ICPacketPlayer extends IPacket {

    public ICPacketPlayer(Packet<?> packet) {
        super(packet);
    }

    public ICPacketPlayer() {
        super(new PlayerMoveServerMessage());
    }

    public void setOnGround(boolean state) {
        ((IMixinCPacketPlayer) getPacket()).setOnGround(state);
    }

    public void setMoving(boolean state) {
        ((IMixinCPacketPlayer) getPacket()).setMoving(state);
    }

}
