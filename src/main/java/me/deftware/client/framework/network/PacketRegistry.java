package me.deftware.client.framework.network;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.network.packets.*;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;

import java.util.HashMap;

/**
 * Responsible for translating Minecraft packets to EMC wrappers
 *
 * @author Deftware
 */
public class PacketRegistry {

    public static final PacketRegistry INSTANCE = new PacketRegistry();

    @Getter
    @Setter
    private SocksProxy proxy;

    private final HashMap<Class<? extends Packet<?>>, Class<? extends PacketWrapper>> packetMap = new HashMap<>();

    private PacketRegistry() {
        register(PlayerInteractEntityC2SPacket.class, CPacketUseEntity.class);
        register(CloseScreenS2CPacket.class, CPacketCloseWindow.class);
        register(KeepAliveC2SPacket.class, CPacketKeepAlive.class);
        register(ClientStatusC2SPacket.class, CPacketClientStatus.class);
        // Move packets
        register(PlayerMoveC2SPacket.class, CPacketPlayer.class);
        register(PlayerMoveC2SPacket.OnGroundOnly.class, CPacketPlayer.class);
        register(PlayerMoveC2SPacket.Full.class, CPacketPositionRotation.class);
        register(PlayerMoveC2SPacket.LookAndOnGround.class, CPacketRotation.class);
        register(PlayerMoveC2SPacket.PositionAndOnGround.class, CPacketPosition.class);
        // Player Actions
        register(ClientCommandC2SPacket.class, CPacketEntityAction.class);
        // Server bound
        register(EntityS2CPacket.class, SPacketEntity.class);
        register(EntityAnimationS2CPacket.class, SPacketAnimation.class);
    }

    public void register(Class<? extends Packet<?>> minecraft, Class<? extends PacketWrapper> translated) {
        packetMap.putIfAbsent(minecraft, translated);
    }

    public PacketWrapper translate(Packet<?> packet) {
        if (packetMap.containsKey(packet.getClass())) {
            Class<? extends PacketWrapper> wrapper = packetMap.get(packet.getClass());
            try {
                return wrapper.getDeclaredConstructor(Packet.class).newInstance(packet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return new PacketWrapper(packet);
    }

}
