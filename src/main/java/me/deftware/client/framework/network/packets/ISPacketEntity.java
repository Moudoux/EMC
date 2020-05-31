package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.EntityS2CPacket;
import net.minecraft.network.Packet;

public class ISPacketEntity extends IPacket {

	public ISPacketEntity(Packet<?> packet) {
		super(packet);
	}

	public boolean isOnGround() {
		return ((EntityS2CPacket) packet).isOnGround();
	}

	public IEntity getEntity() {
		return new IEntity(((EntityS2CPacket) packet).getEntity(MinecraftClient.getInstance().world));
	}

}
