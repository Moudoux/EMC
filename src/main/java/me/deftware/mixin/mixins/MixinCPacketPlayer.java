package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinCPacketPlayer;
import net.minecraft.server.network.packet.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerMoveC2SPacket.class)
public class MixinCPacketPlayer implements IMixinCPacketPlayer {

    @Shadow
    private boolean onGround;

    @Shadow
    private boolean changePosition;

    @Override
    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void setOnGround(boolean state) {
        onGround = state;
    }

    @Override
    public boolean isMoving() {
        return changePosition;
    }

    @Override
    public void setMoving(boolean state) {
        changePosition = state;
    }

}
