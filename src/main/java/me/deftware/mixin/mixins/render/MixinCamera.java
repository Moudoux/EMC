package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventAnimation;
import me.deftware.client.framework.event.events.EventCameraClip;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.render.camera.GameCamera;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Deftware, wagyourtail
 */
@Mixin(Camera.class)
public class MixinCamera implements GameCamera {

    @Inject(at = @At("HEAD"), cancellable = true, method = "getFocusedEntity")
    public void getFocusedEntity(CallbackInfoReturnable<Entity> info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (CameraEntityMan.isActive()) {
            info.setReturnValue(mc.player);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "isThirdPerson")
    public void isThirdPerson(CallbackInfoReturnable<Boolean> info) {
        if (CameraEntityMan.isActive()) {
            info.setReturnValue(true);
            info.cancel();
        }
    }

    @Unique
    private final EventCameraClip eventCameraClip = new EventCameraClip();

    @Inject(at = @At("HEAD"), cancellable = true, method = "clipToSpace")
    public void clipToSpace(double camDistance, CallbackInfoReturnable<Double> info) {
        eventCameraClip.create(camDistance);
        eventCameraClip.broadcast();
        if (eventCameraClip.isCanceled()) {
            info.setReturnValue(eventCameraClip.getDistance());
            info.cancel();
        }
    }

    @Unique
    private final EventAnimation eventAnimation = new EventAnimation();

    @Inject(at = @At("HEAD"), cancellable = true, method = "getSubmersionType")
    public void getSubmergedFluidState(CallbackInfoReturnable<CameraSubmersionType> info) {
        eventAnimation.create(EventAnimation.AnimationType.Underwater).broadcast();
        if (eventAnimation.isCanceled()) {
            // Assuming that cancel is in this case completely ignoring fog and not to keep old fog rendered
            info.setReturnValue(CameraSubmersionType.NONE);
        }
    }

    @Override
    public Vector3d getCameraPosition() {
        return new Vector3d(((Camera) (Object) this).getPos());
    }

    @Override
    public float _getRotationPitch() {
        return ((Camera) (Object) this).getPitch();
    }

    @Override
    public float _getRotationYaw() {
        return ((Camera) (Object) this).getYaw();
    }

    @Override
    public double _getRenderPosX() {
        return ((Camera) (Object) this).getPos().x;
    }

    @Override
    public double _getRenderPosY() {
        return ((Camera) (Object) this).getPos().y;
    }

    @Override
    public double _getRenderPosZ() {
        return ((Camera) (Object) this).getPos().z;
    }

}
