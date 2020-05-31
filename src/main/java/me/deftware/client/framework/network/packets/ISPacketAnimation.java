package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.EntityAnimationS2CPacket;
import net.minecraft.network.Packet;

public class ISPacketAnimation extends IPacket {

	public ISPacketAnimation(Packet<?> packet) {
		super(packet);
	}

	public int getEntityID() {
		return ((EntityAnimationS2CPacket) packet).getId();
	}

	public int getAnimationID() {
		return ((EntityAnimationS2CPacket) packet).getAnimationId();
	}

	public IEntity getEntity() {
		return new IEntity(MinecraftClient.getInstance().world.getEntityById(getEntityID()));
	}

}
