package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.client.framework.entity.Entity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;

import java.util.Objects;

/**
 * @author Deftware
 */
public class SPacketAnimation extends PacketWrapper {

	public SPacketAnimation(Packet<?> packet) {
		super(packet);
	}

	public int getEntityID() {
		return ((EntityAnimationS2CPacket) packet).getId();
	}

	public int getAnimationID() {
		return ((EntityAnimationS2CPacket) packet).getAnimationId();
	}

	public Entity getEntity() {
		return Entity.newInstance(Objects.requireNonNull(MinecraftClient.getInstance().world).getEntityById(getEntityID()));
	}

}