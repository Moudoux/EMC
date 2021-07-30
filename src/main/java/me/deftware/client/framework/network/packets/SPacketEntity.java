package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.client.framework.world.World;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;

import javax.annotation.Nullable;

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

	@Nullable
	public Entity getEntity() {
		net.minecraft.entity.Entity entity = ((EntityS2CPacket) packet).getEntity(MinecraftClient.getInstance().world);
		if (entity == null)
			return null;
		return World.getEntityById(entity.getId());
	}

}
