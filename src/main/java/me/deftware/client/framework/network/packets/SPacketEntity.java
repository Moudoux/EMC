package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.client.framework.entity.Entity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;

/**
 * @author Deftware
 */
public class SPacketEntity extends PacketWrapper {

	public SPacketEntity(Packet<?> packet) {
		super(packet);
	}

	public boolean isOnGround() {
		return ((EntityS2CPacket) packet).isOnGround();
	}

	public Entity getEntity() {
		return Entity.newInstance(((EntityS2CPacket) packet).getEntity(MinecraftClient.getInstance().world));
	}

}
