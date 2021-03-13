package me.deftware.mixin.mixins.network;

import com.google.common.collect.Maps;
import me.deftware.client.framework.event.events.EventAnimation;
import me.deftware.client.framework.event.events.EventChunkDataReceive;
import me.deftware.client.framework.event.events.EventKnockback;
import me.deftware.client.framework.world.player.PlayerEntry;
import me.deftware.mixin.imp.IMixinNetworkHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinNetHandlerPlayClient implements IMixinNetworkHandler {

    @Unique
    private final Map<UUID, PlayerEntry> playerEntryMap = Maps.newHashMap();

    @Redirect(method = "onPlayerList", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object onPlayerListUpdate(Map<UUID, PlayerListEntry> map, Object key) {
        playerEntryMap.remove((UUID) key);
        return map.remove((UUID) key);
    }

    @Redirect(method = "onPlayerList", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object onPlayerListAdd(Map<UUID, PlayerListEntry> map, Object key, Object value) {
        playerEntryMap.put((UUID) key, new PlayerEntry((PlayerListEntry) value));
        return map.put((UUID) key, (PlayerListEntry) value);
    }

    @Override
    public Map<UUID, PlayerEntry> getPlayerEntryMap() {
        return playerEntryMap;
    }

    @Inject(method = "onEntityStatus", at = @At("HEAD"), cancellable = true)
    public void onEntityStatus(EntityStatusS2CPacket packet, CallbackInfo ci) {
        if (packet.getStatus() == 35) {
            EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Totem);
            event.broadcast();
            if (event.isCanceled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    private void onExplosion(ExplosionS2CPacket packet, CallbackInfo ci) {
        EventKnockback event = new EventKnockback(packet.getPlayerVelocityX(), packet.getPlayerVelocityY(), packet.getPlayerVelocityZ());
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "onVelocityUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocityClient(DDD)V"), cancellable = true)
    public void onVelocityUpdate(EntityVelocityUpdateS2CPacket packet, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getId() == packet.getId()) {
            EventKnockback event = new EventKnockback(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
            event.broadcast();
            if (event.isCanceled()) {
                ci.cancel();
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
