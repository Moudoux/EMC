package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinRenderManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class MixinRenderManager implements IMixinRenderManager {

    @Shadow public Camera camera;

    public double renderPosX = 0, renderPosY = 0, renderPosZ = 0;

    public float rotationPitch = 0, rotationYaw = 0;

    public double getRenderPosX() {
        return renderPosX;
    }

    public double getRenderPosY() {
        return renderPosY;
    }

    public double getRenderPosZ() {
        return renderPosZ;
    }

    @Override
    public float getRotationYaw() {
        return rotationYaw;
    }

    @Override
    public float getRotationPitch() {
        return rotationPitch;
    }

    @Inject(method = "configure", at = @At("HEAD"))
    public void configure(World world, Camera camera, Entity target, CallbackInfo ci) {
        renderPosX = camera.getPos().x;
        renderPosY = camera.getPos().y;
        renderPosZ = camera.getPos().z;
        rotationPitch = camera.getPitch();
        rotationYaw = camera.getYaw();
    }

}
