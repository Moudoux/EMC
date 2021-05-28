package me.deftware.mixin.mixins.network;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.network.packets.CPacketUseEntity;
import me.deftware.mixin.imp.IMixinPlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerInteractEntityC2SPacket.class)
public class MixinPlayerInteractEntityC2SPacket implements IMixinPlayerInteractEntityC2SPacket {

    @Setter
    @Getter
    @Unique
    private CPacketUseEntity.Type actionType;


}
