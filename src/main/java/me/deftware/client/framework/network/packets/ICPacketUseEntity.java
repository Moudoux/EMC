package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.network.play.client.CPacketUseEntity;

public class ICPacketUseEntity extends IPacket {

	public ICPacketUseEntity(IEntity entity) {
		super(new CPacketUseEntity(entity.getEntity()));
	}

}
