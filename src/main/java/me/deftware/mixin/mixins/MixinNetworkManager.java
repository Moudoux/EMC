package me.deftware.mixin.mixins;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.deftware.client.framework.event.events.packet.EventPacketSend;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

	@Shadow
	protected abstract void dispatchPacket(Packet<?> p_dispatchPacket_1_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_dispatchPacket_2_);

	@Redirect(method = "sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At(value = "INVOKE", target = "net/minecraft/network/NetworkManager.dispatchPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V"))
	private void sendPacket$dispatchPacket(NetworkManager networkManager, Packet<?> packetIn, @Nullable final GenericFutureListener<? extends Future<?super Void>> futureListeners) {
		EventPacketSend event = new EventPacketSend(packetIn).send();
		if (event.isCanceled()) {
			return;
		}
		dispatchPacket(event.getPacket(), futureListeners);
	}

}