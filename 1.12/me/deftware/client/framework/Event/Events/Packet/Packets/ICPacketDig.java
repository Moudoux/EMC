package me.deftware.client.framework.Event.Events.Packet.Packets;

import me.deftware.client.framework.Event.Events.Packet.IPacket;
import me.deftware.client.framework.Wrappers.Objects.IBlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;

public class ICPacketDig extends IPacket {

	public ICPacketDig(Packet<?> packet) {
		super(packet);
	}

	public ICPacketDig(IDigAction action, IBlockPos pos, IFacing facing) {
		super(new CPacketPlayerDigging(getAction(action), pos.getPos(), getFacing(facing)));
	}

	public static EnumFacing getFacing(IFacing facing) {
		if (facing.equals(IFacing.DOWN)) {
			return EnumFacing.DOWN;
		}
		return null;
	}

	public static CPacketPlayerDigging.Action getAction(IDigAction action) {
		if (action.equals(IDigAction.START_DESTROY_BLOCK)) {
			return CPacketPlayerDigging.Action.START_DESTROY_BLOCK;
		} else if (action.equals(IDigAction.STOP_DESTROY_BLOCK)) {
			return CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK;
		}
		return null;
	}

	public static enum IFacing {
		DOWN
	}

	public static enum IDigAction {
		START_DESTROY_BLOCK, STOP_DESTROY_BLOCK
	}

}
