package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventAttackEntity;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.render.camera.GameCamera;
import me.deftware.mixin.imp.IMixinPlayerControllerMP;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.*;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinPlayerControllerMP implements IMixinPlayerControllerMP {

    @Shadow
    private boolean breakingBlock;

    @Inject(method = "getReachDistance", at = @At(value = "RETURN"), cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BLOCK_REACH_DISTANCE", cir.getReturnValue()));
    }


    @Inject(method = "hasExtendedReach", at = @At(value = "TAIL"), cancellable = true)
    private void onHasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "EXTENDED_REACH", cir.getReturnValue()));
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    public void attackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (target == null || target == player || target == GameCamera.fakePlayer) {
            ci.cancel();
        } else {
            EventAttackEntity event = new EventAttackEntity(player, target);
            event.broadcast();
        }
    }

    @Inject(at = @At("HEAD"), method = "interactEntity", cancellable = true)
    private void interactEntity(PlayerEntity player, Entity target, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if (target == null || target == player) {
            info.setReturnValue(ActionResult.FAIL);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "interactEntityAtLocation", cancellable = true)
    public void interactEntityAtLocation(PlayerEntity player, Entity entity, EntityHitResult hitResult, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
        if (entity == null || entity == player) {
            ci.setReturnValue(ActionResult.FAIL);
            ci.cancel();
        }
    }

    @Override
    public void setPlayerHittingBlock(boolean state) {
        this.breakingBlock = state;
    }

}
