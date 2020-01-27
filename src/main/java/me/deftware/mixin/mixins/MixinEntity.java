package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventKnockback;
import me.deftware.client.framework.event.events.EventSlowdown;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity implements IMixinEntity {

    @Shadow
    public boolean noClip;

    @Shadow
    public boolean onGround;

    @Shadow
    public float pitch;

    @Shadow
    public float yaw;

    @Shadow
    public boolean inNetherPortal;

    @Shadow
    @Final
    public DataTracker dataTracker;

    @Shadow
    @Final
    public static TrackedData<EntityPose> POSE;

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    public abstract boolean hasVehicle();

    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    public abstract boolean getFlag(int int_1);

    @Shadow
    public Vec3d movementMultiplier;

    /**
     * @author Deftware
     * @reason
     */
    @Inject(method = "getPose", at = @At(value = "TAIL"), cancellable = true)
    private void onGetPose(CallbackInfoReturnable<EntityPose> cir) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "SWIMMING_MODE_OVERRIDE", false)) {
            cir.setReturnValue(EntityPose.SWIMMING);
        }
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;noClip:Z", opcode = 180))
    private boolean noClipCheck(Entity self) {
        boolean noClipCheck = (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "NOCLIP", false);
        return noClip || noClipCheck && self instanceof ClientPlayerEntity;
    }

    /**
     * @author Deftware
     * @reason
     */
    @Inject(method = "slowMovement", at = @At(value = "TAIL"), cancellable = true)
    private void onSlowMovement(BlockState state, Vec3d multiplier, CallbackInfo ci) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Web);
        event.broadcast();
        if (event.isCanceled()) {
            Vec3d cobSlowness = new Vec3d(0.25D, 0.05000000074505806D, 0.25D);
            if (multiplier.x == cobSlowness.x && multiplier.y == cobSlowness.y && multiplier.z == cobSlowness.z) {
                this.movementMultiplier = Vec3d.ZERO;
                ci.cancel();
            }
        }
    }

    // TODO is this even still needed?
    /*@Redirect(method = "move", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.isSneaking()Z", opcode = 180, ordinal = 0))
    private boolean sneakingCheck(Entity self) {
        EventSneakingCheck event = new EventSneakingCheck(isSneaking());
        event.broadcast();
        if (event.isSneaking()) {
            return true;
        }
        return getFlag(1);
    }*/

    @Inject(method = "setVelocityClient", at = @At("HEAD"), cancellable = true)
    private void onSetVelocityClient(double double_1, double double_2, double double_3, CallbackInfo ci) {
        EventKnockback event = new EventKnockback(double_1, double_2, double_3);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Override
    public boolean getAFlag(int flag) {
        return getFlag(flag);
    }

    @Accessor("dataTracker")
    @Override
    public abstract DataTracker getTracker();

    @Override
    public void setInPortal(boolean inPortal) {
        this.inNetherPortal = inPortal;
    }

}
