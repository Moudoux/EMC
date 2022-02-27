package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;

public class SPacketOpenScreen extends PacketWrapper {

    public SPacketOpenScreen(Packet<?> packet) {
        super(packet);
    }

}
