package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventAnimation;
import me.deftware.client.framework.event.events.EventKnockback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.packet.EntityStatusS2CPacket;
import net.minecraft.client.network.packet.ExplosionS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinNetHandlerPlayClient {

    @Inject(method = "onEntityStatus", at = @At("HEAD"), cancellable = true)
    public void onEntityStatus(EntityStatusS2CPacket packetIn, CallbackInfo ci) {
        if (packetIn.getStatus() == 35) {
            EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Totem).send();
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
    public void onExplosion(ExplosionS2CPacket explosionClientPacket_1) {
        NetworkThreadUtils.forceMainThread(explosionClientPacket_1, (ClientPlayNetworkHandler) (Object) this, MinecraftClient.getInstance());
        Explosion explosion_1 = new Explosion(MinecraftClient.getInstance().world, (Entity) null, explosionClientPacket_1.getX(), explosionClientPacket_1.getY(), explosionClientPacket_1.getZ(), explosionClientPacket_1.getRadius(), explosionClientPacket_1.getAffectedBlocks());
        explosion_1.affectWorld(true);
        EventKnockback event = new EventKnockback(explosionClientPacket_1.getPlayerVelocityX(), explosionClientPacket_1.getPlayerVelocityY(), explosionClientPacket_1.getPlayerVelocityZ()).send();
        if (event.isCanceled()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(MinecraftClient.getInstance().player.getVelocity().add((double)explosionClientPacket_1.getPlayerVelocityX(), (double)explosionClientPacket_1.getPlayerVelocityY(), (double)explosionClientPacket_1.getPlayerVelocityZ()));
    }

}
