package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.client.framework.util.minecraft.BlockSwingResult;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;

/**
 * @author Deftware
 */
public class CPacketPlayerUseBlock extends PacketWrapper {

	public CPacketPlayerUseBlock(Packet<?> packet) {
		super(packet);
	}

	public CPacketPlayerUseBlock(BlockSwingResult swingResult) {
		this(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, swingResult.getMinecraftHitResult()));
	}
}
