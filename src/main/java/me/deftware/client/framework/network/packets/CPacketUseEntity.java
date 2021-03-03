package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.network.PacketWrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

/**
 * @author Deftware
 */
public class CPacketUseEntity extends PacketWrapper {

    public CPacketUseEntity(Packet<?> packet) {
        super(packet);
    }

    public CPacketUseEntity(Entity entity) {
        super(PlayerInteractEntityC2SPacket.method_34206(entity.getMinecraftEntity(), entity.getMinecraftEntity().isSneaking()));
    }

    public Type getType() {
        /*switch (((PlayerInteractEntityC2SPacket) packet).getClass()) {
            case ATTACK:
                return Type.ATTACK;
            case INTERACT:
                return Type.INTERACT;
            case INTERACT_AT:
                return Type.INTERACT_AT;
        }*/ // FIXME
        return Type.UNKNOWN;
    }

    public enum Type {
        ATTACK, INTERACT, INTERACT_AT, UNKNOWN
    }

}
