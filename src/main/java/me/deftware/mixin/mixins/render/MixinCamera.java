package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventAnimation;
import me.deftware.client.framework.event.events.EventCameraClip;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class MixinCamera {

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

    @Inject(at = @At("HEAD"), cancellable = true, method = "clipToSpace")
    public void clipToSpace(double camDistance, CallbackInfoReturnable<Double> info) {
        EventCameraClip event = new EventCameraClip(camDistance);
        event.broadcast();
        if (event.isCanceled()) {
            info.setReturnValue(event.getDistance());
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "getSubmersionType")
    public void getSubmergedFluidState(CallbackInfoReturnable<CameraSubmersionType> info) {
        EventAnimation event = new EventAnimation(EventAnimation.AnimationType.Underwater).broadcast();
        if (event.isCanceled()) {
            // Assuming that cancel is in this case completely ignoring fog and not to keep old fog rendered
            info.setReturnValue(CameraSubmersionType.NONE);
        }
    }

}
