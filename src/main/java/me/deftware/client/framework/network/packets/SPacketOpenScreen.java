package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;

public class SPacketOpenScreen extends PacketWrapper {

    public SPacketOpenScreen(Packet<?> packet) {
        super(packet);
    }

    public int getSyncId() {
        return ((OpenScreenS2CPacket) this.packet).getSyncId();
    }

}
