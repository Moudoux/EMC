package me.deftware.mixin.mixins.block;

import me.deftware.client.framework.event.events.EventDamage;
import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public class MixinSweetBerryBushBlock {

    @Unique
    private final EventSlowdown slowdown = new EventSlowdown();

    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"))
    private void onSlowMovement(Entity entity, BlockState state, Vec3d multiplier) {
        slowdown.create(EventSlowdown.SlowdownType.BerryBush, 1);
        slowdown.broadcast();
        if(!slowdown.isCanceled()) {
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
