package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.mixin.imp.IMixinPlayerInteractEntityC2SPacket;
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
        super(PlayerInteractEntityC2SPacket.attack(entity.getMinecraftEntity(), entity.getMinecraftEntity().isSneaking()));
    }

    public Type getType() {
        Type type = ((IMixinPlayerInteractEntityC2SPacket) packet).getActionType();
        if (type == null)
            return Type.UNKNOWN;
        return switch (type) {
            case ATTACK -> Type.ATTACK;
            case INTERACT -> Type.INTERACT;
            case INTERACT_AT -> Type.INTERACT_AT;
            default -> Type.UNKNOWN;
        };
    }

    public enum Type {
        ATTACK, INTERACT, INTERACT_AT, UNKNOWN
    }

}
