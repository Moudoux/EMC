package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.network.PacketWrapper;
import me.deftware.client.framework.world.EnumFacing;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;

/**
 * @author Deftware
 */
public class CPacketDig extends PacketWrapper {

    public CPacketDig(Packet<?> packet) {
        super(packet);
    }

    public CPacketDig(IDigAction action, BlockPosition pos, EnumFacing facing) {
        super(new PlayerActionC2SPacket(getAction(action), pos.getMinecraftBlockPos(), facing.getFacing()));
    }

    public static PlayerActionC2SPacket.Action getAction(IDigAction action) {
        if (action.equals(IDigAction.START_DESTROY_BLOCK)) {
            return PlayerActionC2SPacket.Action.START_DESTROY_BLOCK;
        } else if (action.equals(IDigAction.STOP_DESTROY_BLOCK)) {
            return PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK;
        }
        return null;
    }

    public enum IDigAction {
        START_DESTROY_BLOCK, STOP_DESTROY_BLOCK
    }

}