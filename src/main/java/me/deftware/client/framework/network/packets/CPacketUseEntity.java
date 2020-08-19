package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.client.framework.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

/**
 * @author Deftware
 */
public class CPacketUseEntity extends PacketWrapper {

    public CPacketUseEntity(Entity entity) {
        super(new PlayerInteractEntityC2SPacket(entity.getMinecraftEntity(), entity.getMinecraftEntity().isSneaking()));
    }

}
