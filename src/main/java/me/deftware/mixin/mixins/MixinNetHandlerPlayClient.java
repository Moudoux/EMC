package me.deftware.mixin.mixins;

import io.netty.buffer.Unpooled;
import me.deftware.client.framework.event.events.EventAnimation;
import me.deftware.client.framework.event.events.EventChunkDataReceive;
import me.deftware.client.framework.event.events.EventKnockback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.ChunkDataS2CPacket;
import net.minecraft.client.network.packet.EntityStatusS2CPacket;
import net.minecraft.client.network.packet.EntityVelocityUpdateS2CPacket;
import net.minecraft.client.network.packet.ExplosionS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinNetHandlerPlayClient {

    @Redirect(method = "onGameJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/Packet;)V"))
    private void handleGameJoin(ClientConnection connection, Packet<?> packet) {
        if (!(packet instanceof CustomPayloadC2SPacket)) {
            connection.send(packet);
            return;
        }
        // Overwrite the brand packet to send vanilla, because Fabric modifies it and some server do not like it
        connection.send(new CustomPayloadC2SPacket(CustomPayloadC2SPacket.BRAND, (new PacketByteBuf(Unpooled.buffer())).writeString("vanilla")));
    }

    @Inject(method = "onEntityStatus", at = @At("HEAD"), cancellable = true)
    private void onEntityStatus(EntityStatusS2CPacket packetIn, CallbackInfo ci) {
        if (packetIn.getStatus() == 35) {
            EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Totem);
            event.broadcast();
            if (event.isCanceled()) {
                ci.cancel();
            }
        }
    }

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public void onExplosion(ExplosionS2CPacket explosionClientPacket_1) {
        NetworkThreadUtils.forceMainThread(explosionClientPacket_1, (ClientPlayNetworkHandler) (Object) this, MinecraftClient.getInstance());
        Explosion explosion_1 = new Explosion(MinecraftClient.getInstance().world, (Entity) null, explosionClientPacket_1.getX(), explosionClientPacket_1.getY(), explosionClientPacket_1.getZ(), explosionClientPacket_1.getRadius(), explosionClientPacket_1.getAffectedBlocks());
        explosion_1.affectWorld(true);
        EventKnockback event = new EventKnockback(explosionClientPacket_1.getPlayerVelocityX(), explosionClientPacket_1.getPlayerVelocityY(), explosionClientPacket_1.getPlayerVelocityZ());
        event.broadcast();
        if (event.isCanceled()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(MinecraftClient.getInstance().player.getVelocity().add(event.getX(), event.getY(), event.getZ()));
    }

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public void onVelocityUpdate(EntityVelocityUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler) (Object) this, MinecraftClient.getInstance());

        Entity entity = MinecraftClient.getInstance().world.getEntityById(packet.getId());
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (entity != null) {
            if (player != null && player.getEntityId() == packet.getId()) {
                EventKnockback event = new EventKnockback(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
                event.broadcast();
                if (event.isCanceled()) {
                    return;
                }
                entity.setVelocityClient(event.getX() / 8000.0D, event.getY() / 8000.0D, event.getZ() / 8000.0D);
            } else {
                entity.setVelocityClient(packet.getVelocityX() / 8000.0D, packet.getVelocityY() / 8000.0D, packet.getVelocityZ() / 8000.0D);
            }
        }
    }

    @Inject(method = "onChunkData", at = @At("HEAD"), cancellable = true)
    public void onReceiveChunkData(ChunkDataS2CPacket packet, CallbackInfo ci) {
        EventChunkDataReceive event = new EventChunkDataReceive(packet);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

}
