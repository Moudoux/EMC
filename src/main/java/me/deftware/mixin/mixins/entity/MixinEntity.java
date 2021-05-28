package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.event.events.*;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import me.deftware.mixin.imp.IMixinEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(Entity.class)
public abstract class MixinEntity implements IMixinEntity {

    @Shadow
    public boolean noClip;

    @Shadow
    protected boolean onGround;

    @Shadow
    protected boolean inNetherPortal;

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    protected abstract boolean getFlag(int id);

    @Shadow
    protected Vec3d movementMultiplier;

    @Shadow
    protected abstract void unsetRemoved();

    @Shadow public abstract float getYaw();

    @Shadow public abstract float getPitch();

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeLookDirection(double cursorX, double cursorY, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player && CameraEntityMan.isActive()) {
            CameraEntityMan.fakePlayer.changeLookDirection(cursorX, cursorY);
            CameraEntityMan.fakePlayer.setHeadYaw(CameraEntityMan.fakePlayer.getYaw());
            ci.cancel();
        }
    }

    @Redirect(method = "updateMovementInFluid(Lnet/minecraft/tag/Tag;D)Z", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
            opcode = 182))
    private void applyFluidVelocity(Entity entity, Vec3d velocity) {
        if (entity == MinecraftClient.getInstance().player) {
            EventFluidVelocity event = new EventFluidVelocity(new Vector3d(velocity)).broadcast();
            if (!event.isCanceled()) {
                entity.setVelocity(event.getVector3d().getMinecraftVector());
            }
        } else {
            entity.setVelocity(velocity);
        }
    }

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    public void pushAwayFrom(Entity entity, CallbackInfo info) {
        if (((Object) this) == MinecraftClient.getInstance().player) {
            if (new EventEntityPush(me.deftware.client.framework.entity.Entity.newInstance(entity)).broadcast().isCanceled()) {
                info.cancel();
            }
        }
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;noClip:Z", opcode = 180))
    private boolean noClipCheck(Entity self) {
        boolean noClipCheck = GameMap.INSTANCE.get(GameKeys.NOCLIP, false);
        return (self instanceof ClientPlayerEntity && self == MinecraftClient.getInstance().player) && (noClip || noClipCheck);
    }

    @Inject(method = "slowMovement", at = @At(value = "TAIL"), cancellable = true)
    private void onSlowMovement(BlockState state, Vec3d multiplier, CallbackInfo ci) {
        if (((Object) this) == MinecraftClient.getInstance().player) {
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
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.isSneaking()Z", opcode = 180, ordinal = 0))
    private boolean sneakingCheck(Entity self) {
        if (self == MinecraftClient.getInstance().player) {
            EventSneakingCheck event = new EventSneakingCheck(isSneaking());
            event.broadcast();
            if (event.isSneaking()) {
                return true;
            }
        }
        return getFlag(1);
    }

    @Inject(method = "setVelocityClient", at = @At("HEAD"), cancellable = true)
    private void onSetVelocityClient(double x, double y, double z, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player) {
            EventKnockback event = new EventKnockback(x, y, z);
            event.broadcast();
            if (event.isCanceled()) {
                ci.cancel();
            }
        }
    }

    @Override
    public boolean getAFlag(int id) {
        return getFlag(id);
    }

    @Override
    public void setInPortal(boolean inPortal) {
        this.inNetherPortal = inPortal;
    }

    @Override
    public void removeRemovedReason() {
        unsetRemoved();
    }

}
