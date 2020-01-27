package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventDamage;
import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public class MixinSweetBerryBushBlock {
    /**
     * @author Deftware
     * @reason
     */
    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"))
    private void onSlowMovement(Entity entity, BlockState state, Vec3d multiplier) {
        EventSlowdown eventSlowdown = new EventSlowdown(EventSlowdown.SlowdownType.BerryBush);
        eventSlowdown.broadcast();
        if(!eventSlowdown.isCanceled()) {
            entity.slowMovement(state, multiplier);
        }
    }

    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void onDamage(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        EventDamage eventDamage = new EventDamage(EventDamage.DamageSource.BerryBush);
        eventDamage.broadcast();
        if(eventDamage.isCanceled()) {
            ci.cancel();
        }
    }
}
