package me.deftware.mixin.mixins;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.deftware.client.framework.event.PacketCircuit;
import me.deftware.client.framework.event.events.EventPacketSend;
import me.deftware.client.framework.network.IPacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientConnection.class)
public abstract class MixinNetworkManager {

    @Shadow
    protected abstract void sendImmediately(Packet<?> packet_1, GenericFutureListener<? extends Future<? super Void>> genericFutureListener_1);

    @Redirect(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At(value = "INVOKE", target = "net/minecraft/network/ClientConnection.sendImmediately(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void sendPacket$dispatchPacket(ClientConnection connection, Packet<?> packetIn, final GenericFutureListener<? extends Future<? super Void>> futureListeners) {
        EventPacketSend event = new EventPacketSend(packetIn);
        event.broadcast();
        if (event.isCanceled()) {
            return;
        }

        //Run it through the circuits.
        IPacket ipacket = event.getIPacket();
        ipacket = PacketCircuit.handlePacket(ipacket);

        if(ipacket != null) sendImmediately(ipacket.getPacket(), futureListeners);
        //Packets can be null out of the circuits, so that they can be blocked from being sent.
    }

}