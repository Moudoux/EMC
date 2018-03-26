package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinCPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketPlayer.class)
public class MixinCPacketPlayer implements IMixinCPacketPlayer {

	@Shadow
	private boolean onGround;

	@Shadow
	private boolean moving;

	@Override
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public boolean isMoving() {
		return moving;
	}

	@Override
	public void setOnGround(boolean state) {
		onGround = state;
	}

	@Override
	public void setMoving(boolean state) {
		moving = state;
	}
}
