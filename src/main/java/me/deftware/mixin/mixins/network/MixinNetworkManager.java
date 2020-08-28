package me.deftware.mixin.mixins.network;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.deftware.client.framework.event.events.EventPacketReceive;
import me.deftware.client.framework.event.events.EventPacketSend;
import me.deftware.client.framework.world.World;
import me.deftware.mixin.imp.IMixinNetworkManager;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientConnection.class)
public abstract class MixinNetworkManager implements IMixinNetworkManager {

    @Shadow
    protected abstract void sendImmediately(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> listener);

    @SuppressWarnings("unchecked")
    @Redirect(method = "channelRead0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;)V"))
    private void channelRead0(Packet<?> packet, PacketListener listener) {
        if (packet instanceof WorldTimeUpdateS2CPacket) {
            World.updateTime();
        }

        EventPacketReceive event = new EventPacketReceive(packet);
        event.broadcast();
        if (!event.isCanceled()) {
            ((Packet<PacketListener>) event.getIPacket().getPacket()).apply(listener);
        }
    }

    @Redirect(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At(value = "INVOKE", target = "net/minecraft/network/ClientConnection.sendImmediately(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void sendPacket$dispatchPacket(ClientConnection connection, Packet<?> packetIn, final GenericFutureListener<? extends Future<? super Void>> futureListeners) {
        EventPacketSend event = new EventPacketSend(packetIn);
        event.broadcast();
        if (event.isCanceled()) {
            return;
        }
        sendImmediately(event.getPacket(), futureListeners);
    }

    public void sendPacketImmediately(Packet<?> packet) {
        sendImmediately(packet, null);
    }

}