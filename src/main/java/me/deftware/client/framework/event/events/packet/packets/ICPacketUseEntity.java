package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.server.network.packet.PlayerInteractEntityServerPacket;

public class ICPacketUseEntity extends IPacket {

    public ICPacketUseEntity(IEntity entity) {
        super(new PlayerInteractEntityServerPacket(entity.getEntity()));
    }

}
