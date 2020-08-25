package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventStructureLocation;
import me.deftware.client.framework.item.ThrowData;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnderEntity.class)
public class MixinEnderEyeEntity {

    @Unique
    private static ThrowData firstThrow = null;

    @Unique
    private static ThrowData secondThrow = null;

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void onInit(CallbackInfo ci) {
        if (firstThrow != null && secondThrow != null) {
            firstThrow = null;
            secondThrow = null;
        }
    }

    @Inject(method = "initTargetPos", at = @At("HEAD"))
    public void moveTowards(BlockPos pos, CallbackInfo ci) {
        EventStructureLocation event = new EventStructureLocation(DoubleBlockPosition.fromMinecraftBlockPos(pos), EventStructureLocation.StructureType.Stronghold);
        event.broadcast();
    }

    @Inject(method = "setVelocityClient", at = @At("TAIL"))
    public void setVelocityClient(double x, double y, double z, CallbackInfo info) {
        EyeOfEnderEntity entity = (EyeOfEnderEntity) (Object) this;

        if (firstThrow == null) {
            firstThrow = new ThrowData(entity, (entity).getX(), (entity).getZ(), x, z);
            return;
        }
        if (firstThrow.sameEntity(entity)) {
            firstThrow.addVec(x, z);
            return;
        }

        if (secondThrow == null) {
            secondThrow = new ThrowData(entity, (entity).getX(), (entity).getZ(), x, z);
            return;
        }
        if (secondThrow.sameEntity(entity)) {
            secondThrow.addVec(x, z);
            return;
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EyeOfEnderEntity;setPos(DDD)V"))
    public void setPos(CallbackInfo info) {
        Vec3d vel = ((EyeOfEnderEntity)(Object)this).getVelocity();
        System.out.println(vel.toString());

        if (firstThrow != null && secondThrow != null && Math.abs(vel.x * vel.z) <= .0000001 && Math.abs(vel.y) != 0.0D) {
            EventStructureLocation event = new EventStructureLocation(DoubleBlockPosition.fromMinecraftBlockPos(firstThrow.calculateIntersection(secondThrow)), EventStructureLocation.StructureType.Stronghold);
            event.broadcast();
        }
    }

}
