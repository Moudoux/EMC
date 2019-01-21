package me.deftware.client.framework.event.events.packet.packets;

import me.deftware.client.framework.event.events.packet.IPacket;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.network.play.client.CPacketUseEntity;

public class ICPacketUseEntity extends IPacket {

	public ICPacketUseEntity(IEntity entity) {
		super(new CPacketUseEntity(entity.getEntity()));
	}

}
