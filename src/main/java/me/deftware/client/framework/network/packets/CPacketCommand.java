package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class CPacketCommand extends PacketWrapper {

    public CPacketCommand(Packet<?> packet) {
        super(packet);
    }

    public CPacketCommand(Entity entity, Modes mode) {
        super(new ClientCommandC2SPacket(entity.getMinecraftEntity(), mode.getMinecraftMode()));
    }

    public enum Modes {
        PRESS_SHIFT_KEY,
        RELEASE_SHIFT_KEY,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING;

        ClientCommandC2SPacket.Mode getMinecraftMode() {
            return ClientCommandC2SPacket.Mode.valueOf(name());
        }
    }

}
