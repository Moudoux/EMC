package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class ICPacketUseEntity extends IPacket {

    public ICPacketUseEntity(IEntity entity) {
        super(new PlayerInteractEntityC2SPacket(entity.getEntity()));
    }

}
