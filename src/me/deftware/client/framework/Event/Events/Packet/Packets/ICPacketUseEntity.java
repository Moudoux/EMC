package me.deftware.client.framework.Event.Events.Packet.Packets;

import me.deftware.client.framework.Event.Events.Packet.IPacket;
import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.network.play.client.CPacketUseEntity;

public class ICPacketUseEntity extends IPacket {

	public ICPacketUseEntity(IEntity entity) {
		super(new CPacketUseEntity(entity.getEntity()));
	}

}
