package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventAnimation;
import me.deftware.client.framework.event.events.EventKnockback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.world.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

	@Shadow
	private Minecraft client;

	@Inject(method = "handleEntityStatus", at = @At("HEAD"), cancellable = true)
	public void handleEntityStatus(SPacketEntityStatus packetIn, CallbackInfo ci) {
		if (packetIn.getOpCode() == 35) {
			EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Totem);
			event.broadcast();
			if (event.isCanceled()) {
				ci.cancel();
			}
		}
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void handleExplosion(SPacketExplosion packetIn) {
		PacketThreadUtil.checkThreadAndEnqueue(packetIn, (NetHandlerPlayClient) (Object) this, client);
		Explosion explosion = new Explosion(client.world, (Entity) null, packetIn.getX(), packetIn.getY(),
				packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
		explosion.doExplosionB(true);
		EventKnockback event = new EventKnockback(packetIn.getMotionX(), packetIn.getMotionY(), packetIn.getMotionZ());
		event.broadcast();
		if (event.isCanceled()) {
			return;
		}
		client.player.motionX += packetIn.getMotionX();
		client.player.motionY += packetIn.getMotionY();
		client.player.motionZ += packetIn.getMotionZ();
	}
}
