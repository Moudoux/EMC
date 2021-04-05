package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class CPacketEntityAction extends PacketWrapper {
    
    public CPacketEntityAction(Packet<?> packet) {
        super(packet);
    }
    
    public Action getAction() {
        return Action.values()[((ClientCommandC2SPacket)packet).getMode().ordinal()];
    }
    
    public enum Action {
        START_SNEAK, STOP_SNEAK, STOP_SLEEP, START_SPRINT, STOP_SPRINT, START_HORSE_JUMP, STOP_HORSE_JUMP, OPEN_INVENTORY, START_GLIDING
    }
}
